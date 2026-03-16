/* (C)2024 */
package ca.ulaval.glo2003.domain.factories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import ca.ulaval.glo2003.domain.customer.Customer;
import ca.ulaval.glo2003.domain.exceptions.InvalidParameterException;
import ca.ulaval.glo2003.domain.reservation.Reservation;
import ca.ulaval.glo2003.domain.reservation.ReservationConfiguration;
import ca.ulaval.glo2003.domain.reservation.ReservationFactory;
import ca.ulaval.glo2003.domain.reservation.ReservationValidator;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import ca.ulaval.glo2003.domain.shared.Id;
import ca.ulaval.glo2003.domain.shared.IdFactory;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ReservationFactoryTest {

    public static final ReservationValidator RESERVATION_VALIDATOR = new ReservationValidator();
    @Mock private IdFactory ID_FACTORY_MOCK;
    private ReservationFactory RESERVATION_FACTORY;
    private static final Id NUMBER = Id.fromString("a5ca21a0-3bd6-46d1-8532-2f73f9da29d6");
    private static final LocalDate DATE = LocalDate.of(2024, 3, 18);
    private static final LocalTime START_TIME = LocalTime.of(20, 0);
    private static final LocalTime END_TIME = LocalTime.of(21, 0);
    private static final int GROUP_SIZE = 1;
    private static final Customer CUSTOMER =
            new Customer("John Deer", "john.deer@gmail.com", "1234567890");
    public static final ReservationConfiguration DEFAULT_CONFIGURATION =
            ReservationConfiguration.Default();
    private static final Id RESTAURANT_ID = Id.fromString("acbd37dd-904a-4c83-bc53-ee8fc96789e1");
    private static final Id OWNER_ID = Id.fromString("8a5b006a-a946-4434-b309-f1c1b24ad2fd");
    private static final Restaurant RESTAURANT =
            new Restaurant(
                    RESTAURANT_ID,
                    OWNER_ID,
                    "name",
                    100,
                    LocalTime.of(10, 0),
                    LocalTime.of(22, 0),
                    DEFAULT_CONFIGURATION);

    @BeforeEach
    public void SetUpFactory() {
        RESERVATION_FACTORY = new ReservationFactory(RESERVATION_VALIDATOR, ID_FACTORY_MOCK);
    }

    @Test
    void givenValidParameters_whenCreate_thenReservationIsCreated() {
        when(ID_FACTORY_MOCK.createId()).thenReturn(NUMBER);
        var expected =
                new Reservation(
                        NUMBER, DATE, START_TIME, END_TIME, GROUP_SIZE, CUSTOMER, RESTAURANT_ID);

        var actual = RESERVATION_FACTORY.create(RESTAURANT, CUSTOMER, DATE, START_TIME, GROUP_SIZE);

        assertEquals(expected, actual);
    }

    @Test
    void givenInvalidStartTime_whenCreate_thenExceptionIsThrown() {
        assertThrows(
                InvalidParameterException.class,
                () ->
                        RESERVATION_FACTORY.create(
                                RESTAURANT, CUSTOMER, DATE, LocalTime.of(21, 46), GROUP_SIZE));
    }

    @Test
    void
            givenValidExactlyAReservationDurationBeforeClosingTime_whenCreate_startTimeIsNotModified() {
        when(ID_FACTORY_MOCK.createId()).thenReturn(NUMBER);
        var startTime =
                RESTAURANT.getClosingTime().minusMinutes(RESTAURANT.getReservationDuration());
        var endTime = RESTAURANT.getClosingTime();

        var expected =
                new Reservation(
                        NUMBER, DATE, startTime, endTime, GROUP_SIZE, CUSTOMER, RESTAURANT_ID);

        var actual = RESERVATION_FACTORY.create(RESTAURANT, CUSTOMER, DATE, startTime, GROUP_SIZE);

        assertEquals(expected, actual);
    }
}
