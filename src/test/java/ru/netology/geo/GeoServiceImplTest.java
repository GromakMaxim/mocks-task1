package ru.netology.geo;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.netology.entity.Country;

import static org.junit.jupiter.api.Assertions.*;

class GeoServiceImplTest {

    @ParameterizedTest
    @ValueSource(strings = {"172.0.32.11"})
    void test_define_location_byRusMoscowIP(String argument) {
        GeoService geoService = new GeoServiceImpl();

        assertEquals("Moscow", geoService.byIp(argument).getCity());
        assertEquals(Country.RUSSIA, geoService.byIp(argument).getCountry());
        assertEquals("Lenina", geoService.byIp(argument).getStreet());
        assertEquals(15, geoService.byIp(argument).getBuiling());
    }

    @ParameterizedTest
    @ValueSource(strings = {"127.0.0.1"})
    void test_define_location_byLOCALHOSTIP(String argument) {
        GeoService geoService = new GeoServiceImpl();

        assertNull(geoService.byIp(argument).getCity());
        assertNull(geoService.byIp(argument).getCountry());
        assertNull(geoService.byIp(argument).getStreet());
        assertEquals(0, geoService.byIp(argument).getBuiling());
    }

    @ParameterizedTest
    @ValueSource(strings = {"172.0.0.0", "172.", "172.123.12"})
    void test_define_location_byRUSIP(String argument) {
        GeoService geoService = new GeoServiceImpl();

        assertEquals("Moscow", geoService.byIp(argument).getCity());
        assertEquals(Country.RUSSIA, geoService.byIp(argument).getCountry());
        assertNull(geoService.byIp(argument).getStreet());
        assertEquals(0, geoService.byIp(argument).getBuiling());
    }

    @ParameterizedTest
    @ValueSource(strings = {"96.0.0.0", "96.", "96.111.222.333"})
    void test_define_location_byENGIP(String argument) {
        GeoService geoService = new GeoServiceImpl();

        assertEquals("New York", geoService.byIp(argument).getCity());
        assertEquals(Country.USA, geoService.byIp(argument).getCountry());
        assertNull(geoService.byIp(argument).getStreet());
        assertEquals(0, geoService.byIp(argument).getBuiling());
    }

    @ParameterizedTest
    @ValueSource(strings = {"fjsdufhs", "     ", "11111111111111", "123.123.123.123"})
    void test_location_WrongIP_Success_if_NPE(String argument) {
        GeoService geoService = new GeoServiceImpl();
        assertNull(geoService.byIp(argument));
    }
}