package org.skypro.myskyshop.model.product.special;

import org.skypro.myskyshop.model.product.Product;

import java.util.UUID;

public class FixPriceProduct extends Product {
    private static final double FIX_PRICE = 100;

    public FixPriceProduct(UUID id, String name) {
        super(id, name);
    }

    @Override
    public double getPrice() {
        return FIX_PRICE;
    }

    @Override
    public String toString() {
        return "Товар по фиксированной цене: " + getName() + ", Цена: " + FIX_PRICE + " р.";
    }

    @Override
    public boolean isSpecial() {
        return true;
    }
}