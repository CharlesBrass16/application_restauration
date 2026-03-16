/* (C)2024 */
package ca.ulaval.glo2003.domain.reservation;

import static org.junit.jupiter.api.Assertions.*;

import ca.ulaval.glo2003.domain.customer.Customer;
import ca.ulaval.glo2003.domain.shared.Id;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class ReservationsFilterTest {

    private final ReservationsFilter reservationsFilter = new ReservationsFilter();
    private static final LocalDate VALIDE_DATE = LocalDate.now();
    private static final int GROUPE_SIZE = 10;
    private static final LocalTime START_TIME = LocalTime.of(20, 0);
    private static final LocalTime END_TIME = LocalTime.of(22, 0);
    private static final String CUSTOMER_NAME = "John Deer";
    private static final String CUSTOMER_EMAIL = "john.deer@gmail.com";
    private static final String CUSTOMER_PHONE_NUMBER = "1234567890";
    private static final Customer CUSTOMER =
            new Customer(CUSTOMER_NAME, CUSTOMER_EMAIL, CUSTOMER_PHONE_NUMBER);
    private static final Id RESTAURANT_ID = Id.fromString("acbd37dd-904a-4c83-bc53-ee8fc96789e1");
    private static final List<Reservation> RESERVATION =
            Arrays.asList(
                    new Reservation(
                            null,
                            VALIDE_DATE,
                            START_TIME,
                            END_TIME,
                            GROUPE_SIZE,
                            CUSTOMER,
                            RESTAURANT_ID));

    @Test
    void givenValidName_WhenFilterWithName_ThenMatchingReservationsAreReturned() {
        ReservationSearchOptions reservationSearchOptions =
                new ReservationSearchOptions(CUSTOMER_NAME, null);
        List<Reservation> filteredReservation =
                reservationsFilter.filter(RESERVATION, reservationSearchOptions);
        assertTrue(
                filteredReservation.stream()
                        .allMatch(
                                reservation ->
                                        reservation.getCustomer().getName().equals(CUSTOMER_NAME)));
    }

    @Test
    void givenValidDate_whenFilterWithDate_thenMatchingReservationsAreReturned() {
        ReservationSearchOptions reservationSearchOptions =
                new ReservationSearchOptions(null, LocalDate.now());
        List<Reservation> filteredReservation =
                reservationsFilter.filter(RESERVATION, reservationSearchOptions);
        assertTrue(
                filteredReservation.stream()
                        .allMatch(reservation -> reservation.getDate().equals(LocalDate.now())));
    }

    @Test
    void givenValidDateAndName_whenFilterWithDateAndName_thenMatchingReservationsAreReturned() {
        ReservationSearchOptions reservationSearchOptions =
                new ReservationSearchOptions(CUSTOMER_NAME, LocalDate.now());
        List<Reservation> filteredReservation =
                reservationsFilter.filter(RESERVATION, reservationSearchOptions);
        assertTrue(
                filteredReservation.stream()
                        .allMatch(
                                reservation ->
                                        reservation.getDate().equals(LocalDate.now())
                                                && reservation
                                                        .getCustomer()
                                                        .getName()
                                                        .equals(CUSTOMER_NAME)));
    }
}
