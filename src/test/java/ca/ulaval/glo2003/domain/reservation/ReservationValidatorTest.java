/* (C)2024 */
package ca.ulaval.glo2003.domain.reservation;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ca.ulaval.glo2003.domain.customer.Customer;
import ca.ulaval.glo2003.domain.exceptions.InvalidParameterException;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import ca.ulaval.glo2003.domain.shared.Id;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReservationValidatorTest {

    public static final LocalDate VALID_DATE = LocalDate.now();
    public static final LocalTime VALID_START_TIME = LocalTime.of(10, 0);
    public static final LocalTime VALID_END_TIME = LocalTime.of(22, 0);
    public static final int VALID_GROUP_SIZE = 1;
    public static final Customer VALID_CUSTOMER =
            new Customer("John Deer", "john.deer@gmail.com", "1234567890");

    public static final ReservationConfiguration DEFAULT_CONFIGURATION =
            ReservationConfiguration.Default();
    private static final Id RESTAURANT_ID = Id.fromString("123");
    private static final Id OWNER_ID = Id.fromString("456");
    public static final Restaurant VALID_RESTAURANT =
            new Restaurant(
                    RESTAURANT_ID,
                    OWNER_ID,
                    "name",
                    100,
                    LocalTime.of(10, 0),
                    LocalTime.of(22, 0),
                    DEFAULT_CONFIGURATION);
    private ReservationValidator reservationValidator;

    @BeforeEach
    void setupReservationValidator() {
        reservationValidator = new ReservationValidator();
    }

    @Test
    void givenValidReservation_whenValidateReservation_thenNoExceptionIsThrown() {
        assertDoesNotThrow(
                () ->
                        reservationValidator.validate(
                                VALID_START_TIME,
                                VALID_END_TIME,
                                VALID_GROUP_SIZE,
                                VALID_RESTAURANT));
    }

    @Test
    void givenGroupSizeOf0_whenValidateReservation_thenThrowsException() {
        assertThrows(
                InvalidParameterException.class,
                () ->
                        reservationValidator.validate(
                                VALID_START_TIME, VALID_END_TIME, 0, VALID_RESTAURANT));
    }

    @Test
    void givenStartTimeBeforeOpeningTime_whenValidateReservation_thenThrowsException() {
        assertThrows(
                InvalidParameterException.class,
                () ->
                        reservationValidator.validate(
                                LocalTime.of(9, 44),
                                VALID_END_TIME,
                                VALID_GROUP_SIZE,
                                VALID_RESTAURANT));
    }

    @Test
    void givenEndTimeAfterClosingTime_whenValidateReservation_thenThrowsException() {
        assertThrows(
                InvalidParameterException.class,
                () ->
                        reservationValidator.validate(
                                VALID_START_TIME,
                                LocalTime.of(22, 15),
                                VALID_GROUP_SIZE,
                                VALID_RESTAURANT));
    }
}
