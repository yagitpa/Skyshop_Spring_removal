package org.skypro.myskyshop.service;

import org.skypro.myskyshop.exceptions.NoSuchProductException;
import org.skypro.myskyshop.model.basket.ProductBasket;
import org.skypro.myskyshop.model.product.Product;
import org.skypro.myskyshop.model.user.BasketItem;
import org.skypro.myskyshop.model.user.UserBasket;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BasketService {
    private final ProductBasket productBasket;
    private final StorageService storageService;

    public BasketService(ProductBasket productBasket, StorageService storageService) {
        this.productBasket = productBasket;
        this.storageService = storageService;
    }

    public void addProduct(UUID productId) {
        storageService.getProductById(productId);
        productBasket.addProduct(productId);
        }

    public UserBasket getUserBasket() {
        Map<UUID, Integer> basketMap = productBasket.getProducts();
        List<BasketItem> items = basketMap.entrySet().stream()
                .map(entry -> {
                    UUID productId = entry.getKey();
                    int quantity = entry.getValue();
                    Product product = storageService.getProductById(productId).orElse(null);
                    if (product == null) {
                        return null;
                    }
                    return new BasketItem(product, quantity);
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        return new UserBasket(items);
    }
}