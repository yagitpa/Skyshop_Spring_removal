package org.skypro.myskyshop.service;

import org.skypro.myskyshop.model.basket.ProductBasket;
import org.skypro.myskyshop.model.product.Product;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class BasketService {
    private final ProductBasket productBasket;
    private final StorageService storageService;

    public BasketService(ProductBasket productBasket, StorageService storageService) {
        this.productBasket = productBasket;
        this.storageService = storageService;
    }

    public void addProduct(UUID productId) {
        Optional<Product> productOptional = storageService.getProductById(productId);
        if (productOptional.isPresent()) {
            productBasket.addProduct(productId);
        } else {
            throw new IllegalArgumentException("Товар с указанным идентификатором не найден");
        }
    }

    public Map<UUID, Integer> getBasketContents() {
        return productBasket.getProducts();
    }
}