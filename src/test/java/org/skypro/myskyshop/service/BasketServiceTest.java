package org.skypro.myskyshop.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.myskyshop.exceptions.ProductNotFoundException;
import org.skypro.myskyshop.model.basket.ProductBasket;
import org.skypro.myskyshop.model.product.Product;
import org.skypro.myskyshop.model.product.special.SimpleProduct;
import org.skypro.myskyshop.model.user.UserBasket;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BasketServiceTest {

    @Mock
    private ProductBasket productBasket;
    @Mock
    private StorageService storageService;
    @InjectMocks
    private BasketService basketService;

    @Test
    @DisplayName("Добавление несуществующего товара приводит к выбросу исключения")
    public void testWhenAddNonExistingProduct_thenTrowsException() {

        // Arrange
        UUID nonExistingProductId = UUID.randomUUID();
        when(storageService.getProductById(nonExistingProductId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThatExceptionOfType(ProductNotFoundException.class).isThrownBy(() -> basketService.addProduct(nonExistingProductId));

        // Verify
        verify(productBasket, never()).addProduct(nonExistingProductId);
    }

    @Test
    @DisplayName("Добавление существующего товара вызывает метод addProduct у мока ProductBasket")
    public void testWhenAddExistingProduct_thenInvokesAddProductOnProductBasket() {

        // Arrange
        UUID existingProductId = UUID.randomUUID();
        Product product = new SimpleProduct(existingProductId, "Бананы", 100);
        when(storageService.getProductById(existingProductId)).thenReturn(Optional.of(product));

        // Act
        basketService.addProduct(existingProductId);

        // Verify
        verify(productBasket, times(1)).addProduct(existingProductId);
    }

    @Test
    @DisplayName("Метод getUserBasket возвращает пустую корзину, если ProductBasket пуст")
    public void testWhenProductBasketIsEmpty_thenGetUserBasketReturnsEmptyBasket() {

        // Arrange
        when(productBasket.getProducts()).thenReturn(Map.of());

        // Act
        UserBasket result = basketService.getUserBasket();

        // Assert
        assertThat(result.getItems()).isEmpty();
    }

    @Test
    @DisplayName("Метод getUserBasket возвращает подходящую корзину, если в ProductBasket есть товары")
    public void testWhenProductBasketHasItems_thenGetUserBasketReturnsCorrectBasket() {

        // Arrange
        UUID productId = UUID.randomUUID();
        Product product = new SimpleProduct(productId, "Бананы", 100);
        when(productBasket.getProducts()).thenReturn(Map.of(productId, 5));
        when(storageService.getProductById(productId)).thenReturn(Optional.of(product));

        // Act
        UserBasket result = basketService.getUserBasket();

        // Assert
        assertThat(result.getItems()).hasSize(1);
        assertThat(result.getItems().get(0).product().getName()).isEqualTo("Бананы");
        assertThat(result.getItems().get(0).quantity()).isEqualTo(5);
    }
}
