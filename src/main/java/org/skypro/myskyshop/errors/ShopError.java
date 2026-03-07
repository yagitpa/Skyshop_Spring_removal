package org.skypro.myskyshop.errors;

import org.springframework.lang.NonNull;

/**
 * Класс-сущность, представляющий стандартизированную структуру ошибки, возвращаемой API приложения.
 *
 * <p>Используется в качестве тела HTTP-ответа при возникновении ошибок, например, через
 * {@link org.springframework.web.bind.annotation.ExceptionHandler} в классе
 * {@link org.skypro.myskyshop.controller.ShopControllerAdvice}.</p>
 *
 * <p>{@code ShopError} содержит два основных поля:</p>
 * <ul>
 *   <li><b>code</b> — машинно-читаемый код ошибки (например, {@code "PRODUCT_NOT_FOUND"})</li>
 *   <li><b>message</b> — человеко-читаемое описание ошибки (например, "Продукт с ID 12345 не найден")</li>
 * </ul>
 *
 * <p>Класс реализован как {@code record}, что делает его неизменяемым, потокобезопасным
 * и автоматически генерирует конструктор, геттеры, методы {@code equals()}, {@code hashCode()}
 * и {@code toString()}.</p>
 *
 * <p>Пример JSON-представления объекта {@code ShopError}:</p>
 * <pre>
 * {
 *   "code": "PRODUCT_NOT_FOUND",
 *   "message": "Продукт с ID a1b2c3d4 не существует"
 * }
 * </pre>
 *
 * @param code    уникальный код ошибки (не должен быть {@code null})
 * @param message описание ошибки, понятное пользователю или разработчику (не должен быть {@code null})
 *
 * @see org.skypro.myskyshop.controller.ShopControllerAdvice
 * @see org.springframework.web.bind.annotation.ExceptionHandler
 */
public record ShopError(@NonNull String code, @NonNull String message) {
}
