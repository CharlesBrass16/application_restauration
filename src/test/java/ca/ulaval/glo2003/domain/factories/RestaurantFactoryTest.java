/* (C)2024 */
package ca.ulaval.glo2003.domain.factories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import ca.ulaval.glo2003.domain.exceptions.InvalidParameterException;
import ca.ulaval.glo2003.domain.reservation.ReservationConfiguration;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import ca.ulaval.glo2003.domain.restaurant.RestaurantFactory;
import ca.ulaval.glo2003.domain.restaurant.RestaurantValidator;
import ca.ulaval.glo2003.domain.shared.Id;
import ca.ulaval.glo2003.domain.shared.IdFactory;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RestaurantFactoryTest {

    public static final RestaurantValidator RESTAURANT_VALIDATOR = new RestaurantValidator();
    public RestaurantFactory RESTAURANT_FACTORY;
    private static final String RESTAURANT_STRING_ID = "456879654321";
    private static final Id RESTAURANT_ID = new Id(RESTAURANT_STRING_ID);
    private static final String OWNER_STRING_ID = "123456789";
    private static final Id OWNER_ID = new Id(OWNER_STRING_ID);
    private static final String NAME = "name";
    private static final int CAPACITY = 100;

    private static final LocalTime OPENING_TIME = LocalTime.of(10, 0);
    private static final LocalTime CLOSING_TIME = LocalTime.of(14, 0);
    public static final ReservationConfiguration DEFAULT_CONFIGURATION =
            ReservationConfiguration.Default();
    @Mock private IdFactory ID_FACTORY_MOCK;

    @BeforeEach
    public void SetUpFactory() {
        RESTAURANT_FACTORY = new RestaurantFactory(RESTAURANT_VALIDATOR, ID_FACTORY_MOCK);
    }

    @Test
    void givenValidParameters_whenCreate_thenRestaurantIsCreated() {
        when(ID_FACTORY_MOCK.createId()).thenReturn(RESTAURANT_ID);
        var expected =
                new Restaurant(
                        RESTAURANT_ID,
                        OWNER_ID,
                        NAME,
                        CAPACITY,
                        OPENING_TIME,
                        CLOSING_TIME,
                        DEFAULT_CONFIGURATION);

        var actual =
                RESTAURANT_FACTORY.create(
                        OWNER_ID,
                        NAME,
                        CAPACITY,
                        OPENING_TIME,
                        CLOSING_TIME,
                        DEFAULT_CONFIGURATION);

        assertEquals(expected, actual);
    }

    @Test
    void givenInvalidOpeningTime_whenCreate_thenThrowsException() {
        LocalTime invalidOpeningTime = LocalTime.of(14, 0);
        LocalTime invalidClosingTime = LocalTime.of(10, 0);

        assertThrows(
                InvalidParameterException.class,
                () -> {
                    RESTAURANT_FACTORY.create(
                            OWNER_ID,
                            NAME,
                            CAPACITY,
                            invalidOpeningTime,
                            invalidClosingTime,
                            DEFAULT_CONFIGURATION);
                });
    }
}
