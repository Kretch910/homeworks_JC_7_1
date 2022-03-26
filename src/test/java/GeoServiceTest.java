import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;

import java.util.stream.Stream;

public class GeoServiceTest {

    @ParameterizedTest
    @MethodSource("GeoServiceTestSource")
    void GeoServiceTest(String ipAddress, Location preferences) {
        GeoService geoService = new GeoServiceImpl();

        Location extend = geoService.byIp(ipAddress);

        Assertions.assertEquals(extend, preferences);
    }

    private static Stream<Arguments> GeoServiceTestSource() {
        return Stream.of(
                Arguments.of("172.0.32.11", new Location("Moscow", Country.RUSSIA, "Lenina", 15)),
                Arguments.of("172.0.115.5", new Location("Moscow", Country.RUSSIA, null, 0)),
                Arguments.of("96.44.183.149", new Location("New York", Country.USA, " 10th Avenue", 32))
        );
    }

    @Test
    void byCoordinatesTest() {
        GeoService geoService = new GeoServiceImpl();
        double latitude = 10.5;
        double longitude = 20.6;

        Class<RuntimeException> extend = RuntimeException.class;

        Assertions.assertThrows(extend, () -> geoService.byCoordinates(latitude, longitude));
    }
}
