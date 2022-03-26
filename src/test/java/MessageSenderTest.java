import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.i18n.LocalizationService;
import ru.netology.sender.MessageSenderImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;


public class MessageSenderTest {

    @ParameterizedTest
    @MethodSource("languageTestSource")
    void languageTest(String message, String ipAddress, Location location) {
        LocalizationService localizationService = Mockito.mock(LocalizationService.class);
        Mockito.when(localizationService.locale(location.getCountry()))
                .thenReturn(message);

        GeoService geoService = Mockito.mock(GeoService.class);
        Mockito.when(geoService.byIp(ipAddress))
                .thenReturn(location);

        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, ipAddress);

        MessageSenderImpl messageSender = new MessageSenderImpl(geoService, localizationService);
        String preferences = messageSender.send(headers);

        Assertions.assertEquals(message, preferences);
    }

    private static Stream<Arguments> languageTestSource() {
        return Stream.of(
                Arguments.of("Добро пожаловать", "172.0.32.11", new Location("Moscow", Country.RUSSIA, "Lenina", 15)),
                Arguments.of("Добро пожаловать", "172.0.115.5", new Location("Moscow", Country.RUSSIA, null, 0)),
                Arguments.of("Welcome", "96.44.183.149", new Location("New York", Country.USA, " 10th Avenue", 32)),
                Arguments.of("Welcome", "132.15.45.109", new Location("Berlin", Country.GERMANY, null, 0))
        );
    }
}
