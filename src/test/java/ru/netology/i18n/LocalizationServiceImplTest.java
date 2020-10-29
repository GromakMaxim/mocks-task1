package ru.netology.i18n;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import ru.netology.entity.Country;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.EnumSource.Mode.EXCLUDE;

class LocalizationServiceImplTest {
    @ParameterizedTest
    @EnumSource(value = Country.class, names = {"RUSSIA"})
    void test_define_locale_with_Russia(Country argument) {
        LocalizationService localizationService = new LocalizationServiceImpl();
        assertEquals("Добро пожаловать",localizationService.locale(argument));
    }

    @ParameterizedTest
    @EnumSource(value = Country.class, mode = EXCLUDE, names = {"RUSSIA"})//перебираются все ENUM кроме россии
    void test_define_locale(Country argument) {
        LocalizationService localizationService = new LocalizationServiceImpl();
        assertEquals("Welcome",localizationService.locale(argument));
    }


}