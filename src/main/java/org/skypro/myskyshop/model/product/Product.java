package org.skypro.myskyshop.model.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.skypro.myskyshop.model.search.Searchable;

import java.util.Objects;
import java.util.UUID;

public abstract class Product implements Searchable {
    protected final UUID id;
    protected final String name;

    public Product(UUID id, String name) {
        if (id == null) {
            throw new IllegalArgumentException("Идентификатор продукта не может быть пустым!");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Наименование товара не может быть пустым или отсутствовать!");
        }
        this.id = id;
        this.name = name;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    @JsonIgnore
    public String getSearchTerm() {
        return name;
    }

    @Override
    @JsonIgnore
    public String getContentType() {
        return "PRODUCT";
    }

    public String getName() {
        return name;
    }

    public abstract double getPrice();

    public abstract boolean isSpecial();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Product that = (Product) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}