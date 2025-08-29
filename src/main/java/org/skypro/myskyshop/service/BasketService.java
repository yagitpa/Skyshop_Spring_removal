package org.skypro.myskyshop.service;

import org.skypro.myskyshop.exceptions.ProductNotFoundException;
import org.skypro.myskyshop.model.basket.ProductBasket;
import org.skypro.myskyshop.model.product.Product;
import org.skypro.myskyshop.model.user.UserBasket;
import org.springframework.stereotype.Service;

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

    public void addProduct(UUID productId, int quantity) {
        Optional<Product> productOptional = storageService.getProductById(productId);
        if (productOptional.isPresent()) {
            productBasket.addProduct(productId, quantity);
        } else {
            throw new ProductNotFoundException("Товар с указанным идентификатором не найден");
        }
    }

    public UserBasket getUserBasket() {
        return UserBasket.fromBasket(productBasket.getProducts(), storageService);
    }
}