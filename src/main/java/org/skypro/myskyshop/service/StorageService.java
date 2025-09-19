package org.skypro.myskyshop.service;

import org.skypro.myskyshop.exceptions.NoSuchProductException;
import org.skypro.myskyshop.model.article.Article;
import org.skypro.myskyshop.model.product.Product;
import org.skypro.myskyshop.model.product.special.DiscountedProduct;
import org.skypro.myskyshop.model.product.special.FixPriceProduct;
import org.skypro.myskyshop.model.product.special.SimpleProduct;
import org.skypro.myskyshop.model.search.Searchable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class StorageService {
    private final Map<UUID, Product> products = new HashMap<>();
    private final Map<UUID, Article> articles = new HashMap<>();

    public StorageService() {
        initializeData();
    }

    public Collection<Product> getAllProducts() {
        return products.values();
    }

    public Collection<Article> getAllArticles() {
        return articles.values();
    }

    public Collection<Searchable> getAllSearchables() {
        return Stream.concat(products.values().stream(), articles.values().stream())
                .collect(Collectors.toList());
    }

    public Optional<Product> getProductById(UUID id) {
        Product p = products.get(id);
        if (p == null) {
            throw new NoSuchProductException("Товар с идентификатором " + id + " не найден");
        }
        return Optional.of(p);
    }

    private void initializeData() {
        UUID uuid1 = UUID.randomUUID();
        products.put(uuid1, new SimpleProduct(uuid1, "Дырокол", 500));
        UUID uuid2 = UUID.randomUUID();
        products.put(uuid2, new DiscountedProduct(uuid2, "Степлер", 700, 30));
        UUID uuid3 = UUID.randomUUID();
        products.put(uuid3, new FixPriceProduct(uuid3, "Ручка шариковая, синяя"));
        UUID artUuid1 = UUID.randomUUID();
        articles.put(artUuid1, new Article(artUuid1, "Про степлер", "Разнообразие, виды, назначение"));
        UUID artUuid2 = UUID.randomUUID();
        articles.put(artUuid2, new Article(artUuid2, "История степлера", "В изобретении степлера Ученые нашли " +
                "внеземной след!"));
    }
}
