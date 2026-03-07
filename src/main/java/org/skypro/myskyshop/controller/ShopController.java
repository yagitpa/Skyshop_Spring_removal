package org.skypro.myskyshop.controller;

import org.skypro.myskyshop.model.article.Article;
import org.skypro.myskyshop.model.product.Product;
import org.skypro.myskyshop.model.search.SearchResult;
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
import java.util.UUID;

/**
 * REST-контроллер для обработки HTTP-запросов, связанных с товарами, статьями, поиском и корзиной пользователей.
 *
 * <p>Класс предоставляет конечные точки (endpoints) для:
 * <ul>
 *   <li>Получения списка всех товаров и статей</li>
 *   <li>Поиска по заданному шаблону</li>
 *   <li>Добавления товара в корзину</li>
 *   <li>Получения содержимого корзины пользователя</li>
 * </ul>
 * </p>
 *
 * <p>Контроллер использует сервисы:
 * <ul>
 *   <li>{@link org.skypro.myskyshop.service.StorageService} — для доступа к данным о продуктах и статьях</li>
 *   <li>{@link org.skypro.myskyshop.service.SearchService} — для выполнения поисковых запросов</li>
 *   <li>{@link org.skypro.myskyshop.service.BasketService} — для управления корзиной пользователя</li>
 * </ul>
 * </p>
 *
 * <p>Все методы возвращают данные в формате JSON (кроме добавления в корзину — возвращается текстовое сообщение).</p>
 *
 * @see RestController
 * @see GetMapping
 * @see ResponseEntity
 */
public class ShopController {
    private final org.skypro.myskyshop.service.StorageService storageService;
    private final org.skypro.myskyshop.service.SearchService searchService;
    private final org.skypro.myskyshop.service.BasketService basketService;

    /**
     * Конструктор для инъекции зависимостей.
     *
     * @param storageService сервис для хранения и получения продуктов и статей
     * @param searchService  сервис для выполнения поиска по шаблону
     * @param basketService  сервис для управления корзиной пользователя
     */
    public ShopController(StorageService storageService, SearchService searchService, BasketService basketService) {
        this.storageService = storageService;
        this.searchService = searchService;
        this.basketService = basketService;
    }

    /**
     * Возвращает список всех доступных продуктов.
     *
     * <p>Endpoint: <b>GET /products</b></p>
     *
     * @return коллекция объектов {@link Product}
     */
    @GetMapping("/products")
    public Collection<Product> getAllProducts() {
        return storageService.getAllProducts();
    }

    /**
     * Возвращает список всех доступных статей.
     *
     * <p>Endpoint: <b>GET /articles</b></p>
     *
     * @return коллекция объектов {@link Article}
     */
    @GetMapping("/articles")
    public Collection<Article> getAllArticles() {
        return storageService.getAllArticles();
    }

    /**
     * Выполняет поиск по заданному шаблону.
     *
     * <p>Endpoint: <b>GET /search?pattern={текст}</b></p>
     *
     * @param pattern строка поиска (регистронезависимая)
     * @return список результатов поиска {@link SearchResult}
     */
    @GetMapping("/search")
    public List<SearchResult> search(@RequestParam String pattern) {
        return searchService.search(pattern);
    }

    /**
     * Добавляет продукт в корзину пользователя по его идентификатору.
     *
     * <p>Endpoint: <b>GET /basket/{id}</b></p>
     *
     * @param id уникальный идентификатор продукта ({@link UUID})
     * @return {@link ResponseEntity} с текстовым сообщением об успешном добавлении
     */
    @GetMapping("/basket/{id}")
    public ResponseEntity<String> addProduct(@PathVariable("id") UUID id) {
        basketService.addProduct(id);
        return ResponseEntity.ok("Продукт успешно добавлен");
    }

    /**
     * Возвращает текущее состояние корзины пользователя.
     *
     * <p>Endpoint: <b>GET /basket</b></p>
     *
     * <p>Корзина содержит список продуктов с количеством. Реализована с использованием
     * {@link org.springframework.context.annotation.Scope}-бин, уникальный для каждой сессии.</p>
     *
     * @return объект {@link UserBasket}, представляющий корзину пользователя
     */
    @GetMapping("/basket")
    public UserBasket getUserBasket() {
        return basketService.getUserBasket();
    }
}