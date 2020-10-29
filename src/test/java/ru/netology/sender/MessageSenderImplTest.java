package ru.netology.sender;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;

import java.util.HashMap;
import java.util.Map;

class MessageSenderImplTest {

    @ParameterizedTest
    @ValueSource(strings = {"172.123.12.19", "172.0.0.0", "172.", "172.123.12.19.24234", "172.,,,,,,,"})
    public void test_RusIP(String argument) {
        GeoService geoServiceMock = Mockito.mock(GeoService.class);//создаём мок геосервиса
        Mockito.when(geoServiceMock.byIp(argument)).thenReturn(new Location("Moscow", Country.RUSSIA, null, 0));

        LocalizationService localizationServiceMock = Mockito.mock(LocalizationService.class);//создаём мок определения локализации
        Mockito.when(localizationServiceMock.locale(geoServiceMock.byIp(argument).getCountry())).thenReturn("Добро пожаловать");

        MessageSender messageSender = new MessageSenderImpl(geoServiceMock, localizationServiceMock);
        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, argument);

        Assertions.assertEquals("Добро пожаловать", messageSender.send(headers));
    }

    @ParameterizedTest
    @ValueSource(strings = {"96.111.22.33", "96.0.0.0", "96.", "96.123.12.19.24234", "96.,,,,,,,"})
    public void test_EngIP(String argument) {
        GeoService geoServiceMock = Mockito.mock(GeoService.class);//создаём мок геосервиса
        Mockito.when(geoServiceMock.byIp(argument)).thenReturn(new Location("New York", Country.USA, null,  0));

        LocalizationService localizationServiceMock = Mockito.mock(LocalizationService.class);//создаём мок определения локализации
        Mockito.when(localizationServiceMock.locale(geoServiceMock.byIp(argument).getCountry())).thenReturn("Welcome");

        MessageSender messageSender = new MessageSenderImpl(geoServiceMock, localizationServiceMock);
        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, argument);

        Assertions.assertEquals("Welcome", messageSender.send(headers));
    }


    @ParameterizedTest
    @ValueSource(strings = {"aaaaaaa", "AAAAAAA", "      ", "AS12d2G39K",})
    public void test_WrongIP_success_if_NPE(String argument) {
        GeoService geoService = new GeoServiceImpl();
        LocalizationService localizationService = new LocalizationServiceImpl();
        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);
        Map<String, String> headers = new HashMap<>();

        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, argument);

        Assertions.assertThrows(NullPointerException.class, () -> {
            messageSender.send(headers);
        });
    }

    @Test
    void test_NullIP_success_if_NPE() {
        GeoService geoService = new GeoServiceImpl();
        LocalizationService localizationService = new LocalizationServiceImpl();
        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);

        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, null);

        Assertions.assertThrows(NullPointerException.class, () -> {
            messageSender.send(headers);
        });
    }


}