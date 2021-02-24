package com.example.backend.utils;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class URLValidatorTest {

    @ParameterizedTest
    @ValueSource(strings = {"", "/{id}"})
    public void invalidUrlTest(String uriWithPathVariablePlaceholders){
        assertThrows(IllegalArgumentException.class, () -> {
            URLValidator.isUrlValid(
                    "////",
                    uriWithPathVariablePlaceholders,
                    new HashMap<>());
        });
    }
}
