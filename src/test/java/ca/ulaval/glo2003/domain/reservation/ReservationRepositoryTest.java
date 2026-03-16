/* (C)2024 */
package ca.ulaval.glo2003.domain.reservation;

import static org.junit.jupiter.api.Assertions.*;

import ca.ulaval.glo2003.domain.customer.Customer;
import ca.ulaval.glo2003.domain.exceptions.RestaurantNotFoundException;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import ca.ulaval.glo2003.domain.shared.Id;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public abstract class ReservationRepositoryTest {

    protected abstract ReservationRepositoryTest createRepository();

    private ReservationRepository reservationRepository;

    public static final LocalDate VALID_DATE = LocalDate.now();
    public static final LocalTime VALID_START_TIME = LocalTime.of(10, 0);
    public static final LocalTime VALID_END_TIME = LocalTime.of(22, 0);
    public static final int VALID_GROUP_SIZE = 1;
    public static final Customer VALID_CUSTOMER =
            new Customer("John Deer", "john.deer@gmail.com", "1234567890");

    public static final ReservationConfiguration DEFAULT_CONFIGURATION =
            ReservationConfiguration.Default();
    private static final Id VALID_RESERVATION_ID = Id.fromString("123");
    private static final Id OWNER_ID = Id.fromString("456");
    private static final Id RESTAURANT_ID = new Id("456879654322");
    public static final Restaurant VALID_RESTAURANT =
            new Restaurant(
                    RESTAURANT_ID,
                    OWNER_ID,
                    "name",
                    100,
                    LocalTime.of(10, 0),
                    LocalTime.of(22, 0),
                    DEFAULT_CONFIGURATION);

    private Reservation reservationTest;

    @BeforeEach
    void setUpReservationRepository() {
        reservationRepository = createRepository().reservationRepository;
        reservationTest =
                new Reservation(
                        VALID_RESERVATION_ID,
                        VALID_DATE,
                        VALID_START_TIME,
                        VALID_END_TIME,
                        VALID_GROUP_SIZE,
                        VALID_CUSTOMER,
                        RESTAURANT_ID);
        reservationRepository.add(reservationTest);
    }

    @Test
    void givenValidIdReservation_whenGetReservation_thenReservationIsReturned() {
        assertEquals(reservationTest, reservationRepository.find(RESTAURANT_ID));
    }

    @Test
    void givenInvalidReservationId_whenGetReservation_thenThrowsReservationNotFoundException() {
        Id INVALID_RESERVATION_ID = new Id("1234");
        assertThrows(
                RestaurantNotFoundException.class,
                () -> {
                    reservationRepository.find(INVALID_RESERVATION_ID);
                });
    }

    @Test
    void givenExistingReservation_whenDeletingReservation_thenRestaurantIsDeleted() {
        reservationRepository.delete(reservationTest.getReservationNumber());

        assertThrows(
                RestaurantNotFoundException.class,
                () -> {
                    reservationRepository.find(RESTAURANT_ID);
                });
    }
}
