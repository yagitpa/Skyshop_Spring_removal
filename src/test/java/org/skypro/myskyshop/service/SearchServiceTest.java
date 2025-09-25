package org.skypro.myskyshop.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.myskyshop.exceptions.InvalidSearchPatternException;
import org.skypro.myskyshop.model.product.special.DiscountedProduct;
import org.skypro.myskyshop.model.product.special.FixPriceProduct;
import org.skypro.myskyshop.model.product.special.SimpleProduct;
import org.skypro.myskyshop.model.search.Searchable;
import org.skypro.myskyshop.model.search.SearchResult;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SearchServiceTest {

    @Mock
    private StorageService storageService;

    @InjectMocks
    private SearchService searchService;

    @Test
    @DisplayName("Поиск в случае отсутствия объектов в StorageService")
    public void testSearch_whenStorageIsEmpty_thenReturnEmptyArrayList() {

        // Arrange
        when(storageService.getAllSearchables()).thenReturn(new ArrayList<>());

        // Act
        List<SearchResult> result = searchService.search("Бананы");

        // Assert
        assertThat(result).isEmpty();
        verify(storageService, times(1)).getAllSearchables();
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForParametrizedTests")
    @DisplayName("Поиск в случае, если объекты в StorageService есть, но нет подходящего")
    public void testSearch_whenNoMatchingObjectExist (Searchable searchable) {

        // Arrange
        when(storageService.getAllSearchables()).thenReturn(List.of(searchable));

        // Act
        List<SearchResult> result = searchService.search("Арбузы");

        // Assert
        assertThat(result).isEmpty();
        verify(storageService, times(1)).getAllSearchables();
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForParametrizedTests")
    @DisplayName("Поиск когда есть подходящий объект в StorageService")
    public void testSearch_whenMatchingObjectExist(Searchable searchable) {

        // Arrange
        when(storageService.getAllSearchables()).thenReturn(List.of(searchable));

        // Act
        List<SearchResult> result = searchService.search(searchable.getName());

        // Assert
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo(searchable.getName());
        verify(storageService, times(1)).getAllSearchables();
    }

    @Test
    @DisplayName("Проверка на выброс исключения InvalidSearchPatternException")
    public void testSearch_whenQueryIsEmpty_thenDropInvalidSearchPatternException() {

        // Act and Assert
        assertThatExceptionOfType(InvalidSearchPatternException.class).isThrownBy(() ->
                searchService.search(""));

        // Verify
        verify(storageService, never()).getAllSearchables();
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForParametrizedTests")
    @DisplayName("Поиск по неполному соответствию критерию поиска")
    public void testSearch_whenPartialQuery(Searchable searchable) {

        // Arrange
        when(storageService.getAllSearchables()).thenReturn(List.of(searchable));

        // Act
        List<SearchResult> result = searchService.search(searchable.getName().substring(0, 5));

        // Assert
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo(searchable.getName());
        verify(storageService, times(1)).getAllSearchables();
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForParametrizedTests")
    @DisplayName("Проверка поиска на чувствительность к регистру")
    public void testSearch_whenQueryContainsUpperCase(Searchable searchable) {

        // Arrange
        when(storageService.getAllSearchables()).thenReturn(List.of(searchable));

        // Act
        List<SearchResult> result = searchService.search(searchable.getName().toUpperCase());

        // Assert
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo(searchable.getName());
        verify(storageService,times(1)).getAllSearchables();
    }

    private static Stream<Arguments> provideArgumentsForParametrizedTests() {
        return Stream.of(
                Arguments.of(new SimpleProduct(UUID.randomUUID(), "Бананы", 100)),
                Arguments.of(new DiscountedProduct(UUID.randomUUID(), "Виноград", 100, 25)),
                Arguments.of(new FixPriceProduct(UUID.randomUUID(), "Яблоки"))
        );
    }
}