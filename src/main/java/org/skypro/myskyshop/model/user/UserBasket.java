package org.skypro.myskyshop.model.user;

import org.skypro.myskyshop.exceptions.BasketFormationException;
import org.skypro.myskyshop.model.product.Product;
import org.skypro.myskyshop.service.StorageService;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public record UserBasket(List<BasketItem> items, double total) {

    public static UserBasket fromBasket(Map<UUID, Integer> basketMap, StorageService storageService) {
        List<BasketItem> items = basketMap.entrySet().stream()
                .map(entry -> {
                    UUID productId = entry.getKey();
                    int quantity = entry.getValue();
                    Product product = storageService.getProductById(productId)
                            .orElseThrow(() -> new BasketFormationException("Товар с идентификатором " + productId +
                                    " не найден"));
                    return new BasketItem(product, quantity);
                })
                .collect(Collectors.toList());

        double total = items.stream()
                .mapToDouble(item -> item.quantity() * item.product().getPrice())
                .sum();
        return new UserBasket(items, total);
    }
}
