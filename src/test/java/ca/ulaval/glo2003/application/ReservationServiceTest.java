/* (C)2024 */
package ca.ulaval.glo2003.application;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import ca.ulaval.glo2003.domain.customer.Customer;
import ca.ulaval.glo2003.domain.customer.CustomerFactory;
import ca.ulaval.glo2003.domain.exceptions.InvalidParameterException;
import ca.ulaval.glo2003.domain.exceptions.ReservationNotFoundException;
import ca.ulaval.glo2003.domain.exceptions.RestaurantNotFoundException;
import ca.ulaval.glo2003.domain.reservation.Reservation;
import ca.ulaval.glo2003.domain.reservation.ReservationConfiguration;
import ca.ulaval.glo2003.domain.reservation.ReservationFactory;
import ca.ulaval.glo2003.domain.reservation.ReservationRepository;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import ca.ulaval.glo2003.domain.restaurant.RestaurantRepository;
import ca.ulaval.glo2003.domain.shared.Id;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {

    private static final Id RESTAURANT_ID = new Id("456879654322");
    private static final Id NUMBER = new Id("123456789");
    private static final Id OWNER_ID = new Id("1234");
    private static final LocalDate DATE = LocalDate.of(2024, 3, 18);
    private static final LocalTime START_TIME = LocalTime.of(20, 0);
    private static final LocalTime END_TIME = LocalTime.of(21, 0);
    private static final int GROUP_SIZE = 1;
    private static final String CUSTOMER_NAME = "John Deer";
    private static final String CUSTOMER_EMAIL = "john.deer@gmail.com";
    private static final String CUSTOMER_PHONE_NUMBER = "1234567890";
    private static final Customer CUSTOMER =
            new Customer(CUSTOMER_NAME, CUSTOMER_EMAIL, CUSTOMER_PHONE_NUMBER);
    public static final ReservationConfiguration DEFAULT_RESERVATION_CONFIGURATION =
            ReservationConfiguration.Default();
    private static final Restaurant RESTAURANT =
            new Restaurant(
                    RESTAURANT_ID,
                    OWNER_ID,
                    "name",
                    100,
                    LocalTime.of(10, 0),
                    LocalTime.of(22, 0),
                    DEFAULT_RESERVATION_CONFIGURATION);
    private static final Reservation EXISTING_RESERVATION =
            new Reservation(
                    NUMBER,
                    DATE,
                    LocalTime.of(19, 30),
                    LocalTime.of(20, 30),
                    GROUP_SIZE,
                    CUSTOMER,
                    RESTAURANT_ID);
    private static final List<Reservation> SAME_DAY_RESERVATIONS = List.of(EXISTING_RESERVATION);
    private static final Reservation NEW_RESERVATION =
            new Reservation(
                    NUMBER, DATE, START_TIME, END_TIME, GROUP_SIZE, CUSTOMER, RESTAURANT_ID);

    @Mock private ReservationRepository reservationRepository;
    @Mock private ReservationFactory reservationFactory;
    @Mock private RestaurantRepository restaurantRepository;
    @Mock private CustomerFactory customerFactory;

    @InjectMocks private ReservationService reservationService;

    @Test
    void givenValidReservation_whenCreateReservation_thenReservationIsAdded() {
        when(restaurantRepository.find(RESTAURANT_ID)).thenReturn(RESTAURANT);
        when(reservationRepository.listSameDay(RESTAURANT_ID, DATE))
                .thenReturn(SAME_DAY_RESERVATIONS);
        when(reservationFactory.create(RESTAURANT, CUSTOMER, DATE, START_TIME, GROUP_SIZE))
                .thenReturn(NEW_RESERVATION);

        reservationService.createReservation(RESTAURANT_ID, CUSTOMER, DATE, START_TIME, GROUP_SIZE);
    }

    @Test
    void givenInexistingRestaurantId_whenCreateReservation_thenThrowsRestaurantNotFoundException() {
        when(restaurantRepository.find(RESTAURANT_ID)).thenThrow(RestaurantNotFoundException.class);

        assertThrows(
                RestaurantNotFoundException.class,
                () ->
                        reservationService.createReservation(
                                RESTAURANT_ID, CUSTOMER, DATE, START_TIME, GROUP_SIZE));
    }

    @Test
    void givenInsufficientCapacity_whenCreateReservation_thenThrowsInvalidParameterException() {
        Restaurant smallRestaurant =
                new Restaurant(
                        RESTAURANT_ID,
                        OWNER_ID,
                        "name",
                        1,
                        LocalTime.of(10, 0),
                        LocalTime.of(22, 0),
                        DEFAULT_RESERVATION_CONFIGURATION);
        when(restaurantRepository.find(RESTAURANT_ID)).thenReturn(smallRestaurant);
        when(reservationRepository.listSameDay(RESTAURANT_ID, DATE))
                .thenReturn(SAME_DAY_RESERVATIONS);
        when(reservationFactory.create(smallRestaurant, CUSTOMER, DATE, START_TIME, GROUP_SIZE))
                .thenReturn(NEW_RESERVATION);

        assertThrows(
                InvalidParameterException.class,
                () ->
                        reservationService.createReservation(
                                RESTAURANT_ID, CUSTOMER, DATE, START_TIME, GROUP_SIZE));
    }

    @Test
    void
            givenNonExistingReservation_whenDeletingReservation_thenThrowsReservationNotFoundException() {
        var wrongReservationId = new Id("1000");
        when(reservationRepository.find(wrongReservationId))
                .thenThrow(new ReservationNotFoundException("Le restaurant n'existe pas"));

        assertThrows(
                ReservationNotFoundException.class,
                () -> {
                    reservationService.deleteReservation(wrongReservationId);
                });
    }
}
