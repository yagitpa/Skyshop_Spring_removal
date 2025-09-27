package org.skypro.myskyshop;

import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;

import java.io.PrintWriter;

// Используется лаунчер из урока по JUnit 5 с YouTube: https://youtu.be/VYCqLMVML5o?si=YnQbFkXMmhMQyeK3
// как альтернатива запуска тестов, с отражением текстовой статистики в консоли

public class TestLauncher {
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
