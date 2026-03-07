package org.skypro.myskyshop;

import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;

import java.io.PrintWriter;

/**
 * Утилитарный класс для запуска тестов в пакете {@code org.skypro.myskyshop.service}
 * с использованием JUnit Platform Launcher API.
 *
 * <p>Данный класс предоставляет возможность программного запуска тестов,
 * а также вывода итоговой статистики выполнения (количество пройденных,
 * упавших и пропущенных тестов) в консоль. Используется как альтернатива
 * стандартным способам запуска тестов (например, через IDE или Maven),
 * обеспечивая более гибкий контроль над процессом.</p>
 *
 * <p>Тесты выбираются по пакету {@code org.skypro.myskyshop.service}
 * с помощью {@link DiscoverySelectors#selectPackage(String)}.
 * Результаты выполнения собираются с помощью {@link SummaryGeneratingListener},
 * который затем выводит отчёт в стандартный поток вывода.</p>
 *
 * <p>Источник реализации: адаптация подхода из обучающего видео по JUnit 5
 * — <a href="https://youtu.be/VYCqLMVML5o?si=YnQbFkXMmhMQyeK3">YouTube</a>.</p>
 *
 * @see org.junit.platform.launcher.Launcher
 * @see org.junit.platform.launcher.core.LauncherFactory
 * @see org.junit.platform.launcher.listeners.SummaryGeneratingListener
 * @see org.junit.platform.engine.discovery.DiscoverySelectors
 */
public class TestLauncher {
    /**
     * Точка входа в приложение. Запускает тесты из пакета {@code org.skypro.myskyshop.service},
     * собирает результаты и выводит сводную статистику в консоль.
     *
     * <p>Алгоритм работы:</p>
     * <ol>
     *   <li>Создаётся экземпляр {@link org.junit.platform.launcher.Launcher} с помощью {@link LauncherFactory#create()}.</li>
     *   <li>Настраивается слушатель {@link SummaryGeneratingListener} для сбора результатов.</li>
     *   <li>Формируется запрос на обнаружение тестов в указанном пакете.</li>
     *   <li>Запускаются тесты с передачей запроса и слушателя методу {@code execute}.</li>
     *   <li>После завершения выводится итоговая статистика в стандартный поток вывода.</li>
     * </ol>
     *
     * @param args аргументы командной строки (не используются)
     */
    public static void main(String[] args) {
        var launcher = LauncherFactory.create();
        var summaryGeneratingListener = new SummaryGeneratingListener();
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder
                .request()
                .selectors(DiscoverySelectors.selectPackage("org.skypro.myskyshop.service"))
                .build();
        launcher.execute(request, summaryGeneratingListener);

        try (var writer = new PrintWriter(System.out)) {
            summaryGeneratingListener.getSummary().printTo(writer);
        }
    }
}