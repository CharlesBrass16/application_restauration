/* (C)2024 */
package ca.ulaval.glo2003.domain.reservation;

import static org.junit.jupiter.api.Assertions.*;

import ca.ulaval.glo2003.domain.customer.Customer;
import ca.ulaval.glo2003.domain.exceptions.ReservationNotFoundException;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import ca.ulaval.glo2003.domain.shared.Id;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public abstract class ReservationRepositoryIntegrationTest {

    protected abstract ReservationRepository createRepository();

    private ReservationRepository reservationRepository;

    private static final Id NUMBER = new Id("1234567890");
    private static final Id FIRST_NUMBER = new Id("1234567");
    private static final Id SECOND_NUMBER = new Id("123456789");
    private static final Id INEXISTING_ID = new Id("123456");
    private static final LocalDate DATE = LocalDate.now();
    private static final LocalTime START_TIME = LocalTime.of(20, 0);
    private static final LocalTime END_TIME = LocalTime.of(22, 0);
    private static final int GROUP_SIZE = 1;
    private static final String CUSTOMER_NAME = "John Deer";
    private static final String CUSTOMER_EMAIL = "john.deer@gmail.com";
    private static final String CUSTOMER_PHONE_NUMBER = "1234567890";
    private static final Customer CUSTOMER =
            new Customer(CUSTOMER_NAME, CUSTOMER_EMAIL, CUSTOMER_PHONE_NUMBER);
    private static final Id RESTAURANT_ID = Id.fromString("acbd37dd-904a-4c83-bc53-ee8fc96789e1");
    private static final Id FIRST_RESTAURANT_ID =
            Id.fromString("acbd37dd-904a-4c83-bc53-ee8fc96789lp");
    private static final Id SECOND_RESTAURANT_ID =
            Id.fromString("acbd37dd-904a-4c83-bc53-ee8fc96789yu");
    private static final Id OWNER_ID = Id.fromString("8a5b006a-a946-4434-b309-f1c1b24ad2fd");
    private static final Restaurant RESTAURANT =
            new Restaurant(
                    RESTAURANT_ID,
                    OWNER_ID,
                    "name",
                    100,
                    LocalTime.of(10, 0),
                    LocalTime.of(22, 0),
                    null);
    private Reservation reservationTest;
    private Reservation firstReservation;
    private Reservation secondReservation;

    @BeforeEach
    void setupInMemoryReservationRepository() {
        reservationRepository = createRepository();
        reservationTest =
                new Reservation(
                        NUMBER, DATE, START_TIME, END_TIME, GROUP_SIZE, CUSTOMER, RESTAURANT_ID);
        firstReservation =
                new Reservation(
                        FIRST_NUMBER,
                        DATE,
                        START_TIME,
                        END_TIME,
                        GROUP_SIZE,
                        CUSTOMER,
                        FIRST_RESTAURANT_ID);
        secondReservation =
                new Reservation(
                        SECOND_NUMBER,
                        DATE,
                        START_TIME,
                        END_TIME,
                        GROUP_SIZE,
                        CUSTOMER,
                        SECOND_RESTAURANT_ID);
        reservationRepository.add(reservationTest);
        reservationRepository.add(firstReservation);
        reservationRepository.add(secondReservation);
    }

    @Test
    void givenValidReservationNumber_whenFind_thenReservationIsReturned() {
        assertEquals(
                reservationTest,
                reservationRepository.find(reservationTest.getReservationNumber()));
    }

    @Test
    void givenInvalidReservationNumber_whenFind_thenThrowsReservationNotFoundException() {
        assertThrows(
                ReservationNotFoundException.class,
                () -> reservationRepository.find(INEXISTING_ID));
    }

    @Test
    void givenRestaurantIdAndDate_whenListSameDay_thenReservationsAreReturned() {
        assertEquals(
                List.of(reservationTest),
                reservationRepository.listSameDay(RESTAURANT.getId(), DATE));
    }

    @Test
    void givenRestaurantId_whenDeletingOneRestaurant_thenReservationsForThisRestaurantAreDeleted() {
        reservationRepository.deleteAllFromRestaurant(FIRST_RESTAURANT_ID);
        assertThrows(
                ReservationNotFoundException.class,
                () -> reservationRepository.find(firstReservation.getReservationNumber()));
    }

    @Test
    void givenRestaurantId_whenDeletingOneRestaurant_thenOtherReservationsAreNotDeleted() {
        reservationRepository.deleteAllFromRestaurant(RESTAURANT_ID);
        assertEquals(
                secondReservation,
                reservationRepository.find(secondReservation.getReservationNumber()));
    }

    @Test
    void givenReservations_whenFindingByRestaurantId_thenReservationsAreReturned() {
        assertEquals(
                List.of(firstReservation),
                reservationRepository.findByRestaurantID(FIRST_RESTAURANT_ID));
    }

    @Test
    void givenReservation_whenDeleting_thenReservationIsDeleted() {
        reservationRepository.delete(reservationTest.getReservationNumber());
        assertThrows(
                ReservationNotFoundException.class,
                () -> reservationRepository.find(reservationTest.getReservationNumber()));
    }
}
