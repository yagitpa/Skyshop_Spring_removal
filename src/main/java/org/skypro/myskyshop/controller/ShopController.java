package org.skypro.myskyshop.controller;

import org.skypro.myskyshop.model.article.Article;
import org.skypro.myskyshop.model.product.Product;
import org.skypro.myskyshop.model.search.SearchResult;
import org.skypro.myskyshop.model.user.BasketItem;
import org.skypro.myskyshop.model.user.UserBasket;
import org.skypro.myskyshop.service.BasketService;
import org.skypro.myskyshop.service.SearchService;
import org.skypro.myskyshop.service.StorageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class ShopController {
    private final StorageService storageService;
    private final SearchService searchService;
    private final BasketService basketService;

    public ShopController(StorageService storageService, SearchService searchService, BasketService basketService) {
        this.storageService = storageService;
        this.searchService = searchService;
        this.basketService = basketService;
    }

    @GetMapping("/products")
    public Collection<Product> getAllProducts() {
        return storageService.getAllProducts();
    }

    @GetMapping("/articles")
    public Collection<Article> getAllArticles() {
        return storageService.getAllArticles();
    }

    @GetMapping("/search")
    public List<SearchResult> search(@RequestParam String pattern) {
        return searchService.search(pattern);
    }

    @GetMapping("/basket/{id}")
    public ResponseEntity<String> addProduct(@PathVariable("id")UUID id) {
        basketService.addProduct(id);
        return ResponseEntity.ok("Продукт успешно добавлен");
    }

    @GetMapping("/basket")
    public UserBasket getUserBasket() {
        Map<UUID, Integer> basketMap = basketService.getBasketContents();
        List<BasketItem> items = basketMap.entrySet().stream()
                .map(entry -> {
                    UUID productId = entry.getKey();
                    int quantity = entry.getValue();
                    Product product = storageService.getProductById(productId)
                            .orElseThrow(() -> new IllegalStateException("Товар не найден"));
                    return new BasketItem(product, quantity);
                })
                .collect(Collectors.toList());
        double total = items.stream()
                .mapToDouble(item -> item.quantity() * item.product().getPrice())
                .sum();
        return new UserBasket(items, total);
    }
}
