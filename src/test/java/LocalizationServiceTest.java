import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;

import java.util.stream.Stream;

public class LocalizationServiceTest {

    @ParameterizedTest
    @MethodSource("LocalizationServiceTestSource")
    void LocalizationServiceTest(Country country, String preferences) {
        LocalizationService localizationService = new LocalizationServiceImpl();

        String extend = localizationService.locale(country);

        Assertions.assertEquals(extend, preferences);
    }

    private static Stream<Arguments> LocalizationServiceTestSource() {
        return Stream.of(
                Arguments.of(Country.RUSSIA, "Добро пожаловать"),
                Arguments.of(Country.USA, "Welcome"),
                Arguments.of(Country.GERMANY, "Welcome")
        );
    }
}
