package org.skypro.myskyshop.controller;

import org.skypro.myskyshop.errors.ShopError;
import org.skypro.myskyshop.exceptions.NoSuchProductException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Глобальный обработчик исключений для контроллеров приложения "MySkyShop".
 *
 * <p>Класс перехватывает специфические исключения, возникающие в ходе обработки HTTP-запросов,
 * и преобразует их в единый формат ошибок {@link org.skypro.myskyshop.errors.ShopError},
 * возвращаемый в виде корректного HTTP-ответа с соответствующим статусом.</p>
 *
 * <p>Аннотация {@link RestControllerAdvice} делает этот класс глобальным по отношению ко всем
 * {@link org.springframework.web.bind.annotation.RestController}, позволяя централизованно
 * управлять обработкой исключений.</p>
 *
 * @see RestControllerAdvice
 * @see ExceptionHandler
 * @see NoSuchProductException
 * @see ShopError
 */
@RestControllerAdvice
public class ShopControllerAdvice {

    /**
     * Обрабатывает исключение {@link NoSuchProductException}, которое выбрасывается,
     * когда запрашиваемый продукт не найден в системе.
     *
     * <p>Метод формирует объект {@link ShopError} с кодом ошибки {@code "PRODUCT_NOT_FOUND"}
     * и сообщением из исключения, затем возвращает ответ со статусом HTTP 404 (Not Found).</p>
     *
     * <p>Пример тела ответа:</p>
     * <pre>
     * {
     *   "code": "PRODUCT_NOT_FOUND",
     *   "message": "Продукт с ID 12345 не найден"
     * }
     * </pre>
     *
     * @param e перехваченное исключение {@link NoSuchProductException}
     * @return {@link ResponseEntity} с телом {@link ShopError} и HTTP-статусом 404
     */
    @ExceptionHandler(NoSuchProductException.class)
    public ResponseEntity<ShopError> handleNoSuchProductException(NoSuchProductException e) {
        ShopError shopError = new ShopError("PRODUCT_NOT_FOUND", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(shopError);
    }
}
