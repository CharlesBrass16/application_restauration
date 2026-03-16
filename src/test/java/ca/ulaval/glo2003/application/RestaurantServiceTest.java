/* (C)2024 */
package ca.ulaval.glo2003.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo2003.domain.customer.Customer;
import ca.ulaval.glo2003.domain.exceptions.NotRestaurantOwnerException;
import ca.ulaval.glo2003.domain.exceptions.RestaurantNotFoundException;
import ca.ulaval.glo2003.domain.reservation.Reservation;
import ca.ulaval.glo2003.domain.reservation.ReservationConfiguration;
import ca.ulaval.glo2003.domain.reservation.ReservationRepository;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import ca.ulaval.glo2003.domain.restaurant.RestaurantFactory;
import ca.ulaval.glo2003.domain.restaurant.RestaurantRepository;
import ca.ulaval.glo2003.domain.restaurant.menu.Dish;
import ca.ulaval.glo2003.domain.restaurant.menu.Menu;
import ca.ulaval.glo2003.domain.restaurant.menu.MenuRepository;
import ca.ulaval.glo2003.domain.restaurant.menu.MenuValidator;
import ca.ulaval.glo2003.domain.shared.Availability;
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
class RestaurantServiceTest {

    private static final String RESTAURANT_STRING_ID = "456879654321";
    private static final Id RESTAURANT_ID = new Id(RESTAURANT_STRING_ID);
    private static final String OWNER_STRING_ID = "123456789";
    private static final Id OWNER_ID = new Id(OWNER_STRING_ID);
    private static final String NAME = "name";
    private static final int CAPACITY = 100;
    private static final LocalTime OPENING_TIME = LocalTime.of(10, 0);
    private static final LocalTime CLOSING_TIME = LocalTime.of(13, 0);
    private static final ReservationConfiguration RESERVATION_DURATION =
            ReservationConfiguration.Default();
    private final Restaurant RESTAURANT =
            new Restaurant(
                    RESTAURANT_ID,
                    OWNER_ID,
                    NAME,
                    CAPACITY,
                    OPENING_TIME,
                    CLOSING_TIME,
                    RESERVATION_DURATION);
    private static final LocalDate DATE = LocalDate.of(2024, 4, 8);
    private static final Reservation RESERVATION =
            new Reservation(
                    new Id("123456789"),
                    DATE,
                    LocalTime.of(10, 0),
                    LocalTime.of(11, 0),
                    2,
                    new Customer(NAME, RESTAURANT_STRING_ID, OWNER_STRING_ID),
                    RESTAURANT_ID);
    @Mock private RestaurantRepository restaurantRepository;

    @Mock private ReservationRepository reservationRepository;
    @Mock private RestaurantFactory restaurantFactory;
    @Mock private MenuValidator menuValidator;
    @Mock private MenuRepository menuRepository;

    @InjectMocks private RestaurantService restaurantService;

    @Test
    void givenValidRestaurant_whenCreateRestaurant_thenRestaurantIsAdded() {
        when(restaurantFactory.create(
                        OWNER_ID, NAME, CAPACITY, OPENING_TIME, CLOSING_TIME, RESERVATION_DURATION))
                .thenReturn(RESTAURANT);

        restaurantService.createRestaurant(
                OWNER_ID, NAME, CAPACITY, OPENING_TIME, CLOSING_TIME, RESERVATION_DURATION);

        verify(restaurantRepository).add(RESTAURANT);
    }

    @Test
    void givenRestaurantId_whenGetRestaurant_thenRestaurantIsReturned() {
        when(restaurantRepository.find(RESTAURANT_ID)).thenReturn(RESTAURANT);

        var actual = restaurantService.getRestaurant(RESTAURANT_ID, OWNER_ID);

        assertEquals(RESTAURANT, actual);
    }

    @Test
    void givenOwnerId_whenGetRestaurantsFromOwnerId_thenRestaurantsAreReturned() {
        when(restaurantRepository.findByOwnerId(OWNER_ID)).thenReturn(List.of(RESTAURANT));

        var actual = restaurantService.getRestaurantsFromOwnerId(OWNER_ID);

        assertEquals(List.of(RESTAURANT), actual);
    }

    @Test
    void
            givenOwnerNotOwningRestaurant_whenGettingRestaurant_thenThrowsNotRestaurantOwnerException() {
        when(restaurantRepository.find(RESTAURANT_ID)).thenReturn(RESTAURANT);

        assertThrows(
                NotRestaurantOwnerException.class,
                () -> {
                    restaurantService.getRestaurant(RESTAURANT_ID, new Id("123"));
                });
    }

    @Test
    void givenIdNotExisting_whenGettingRestaurant_thenThrowsRestaurantNotFoundException() {
        when(restaurantRepository.find(RESTAURANT_ID)).thenThrow(RestaurantNotFoundException.class);

        assertThrows(
                RestaurantNotFoundException.class,
                () -> {
                    restaurantService.getRestaurant(RESTAURANT_ID, OWNER_ID);
                });
    }

    @Test
    void givenValidRestaurantIdAndDate_whenGettingAvailabilities_thenAvailabilitiesAreReturned() {
        when(restaurantRepository.find(RESTAURANT_ID)).thenReturn(RESTAURANT);
        when(reservationRepository.listSameDay(RESTAURANT_ID, DATE))
                .thenReturn(List.of(RESERVATION));

        var actual = restaurantService.getAvailabilities(RESTAURANT_ID, DATE);
        var expected =
                List.of(
                        new Availability(LocalTime.of(10, 0), 98),
                        new Availability(LocalTime.of(10, 15), 98),
                        new Availability(LocalTime.of(10, 30), 98),
                        new Availability(LocalTime.of(10, 45), 98),
                        new Availability(LocalTime.of(11, 0), 100),
                        new Availability(LocalTime.of(11, 15), 100),
                        new Availability(LocalTime.of(11, 30), 100),
                        new Availability(LocalTime.of(11, 45), 100),
                        new Availability(LocalTime.of(12, 0), 100));

        assertEquals(expected, actual);
    }

    @Test
    void givenIdNotExisting_whenGettingAvailabilities_thenThrowsRestaurantNotFoundException() {
        when(restaurantRepository.find(RESTAURANT_ID)).thenThrow(RestaurantNotFoundException.class);

        assertThrows(
                RestaurantNotFoundException.class,
                () -> {
                    restaurantService.getAvailabilities(RESTAURANT_ID, DATE);
                });
    }

    @Test
    void givenRightfulOwner_whenDeletingRestaurant_thenRestaurantIsDeleted() {
        when(restaurantRepository.find(RESTAURANT_ID)).thenReturn(RESTAURANT);

        restaurantService.deleteRestaurantById(RESTAURANT_ID, OWNER_ID);

        verify(restaurantRepository).delete(RESTAURANT.getId());
    }

    @Test
    void givenRightfulOwner_whenDeletingRestaurant_thenReservationsAreDeleted() {
        when(restaurantRepository.find(RESTAURANT_ID)).thenReturn(RESTAURANT);

        restaurantService.deleteRestaurantById(RESTAURANT_ID, OWNER_ID);

        verify(reservationRepository).deleteAllFromRestaurant(RESTAURANT_ID);
    }

    @Test
    void givenRightfulOwner_whenDeletingRestaurant_thenMenusAreDeleted() {
        when(restaurantRepository.find(RESTAURANT_ID)).thenReturn(RESTAURANT);

        restaurantService.deleteRestaurantById(RESTAURANT_ID, OWNER_ID);

        verify(menuRepository).removeMenuFromRestaurant(RESTAURANT_ID);
    }

    @Test
    void givenNotRightfulOwner_whenDeletingRestaurant_thenThrowsNotRestaurantOwnerException() {
        var wrongOwnerId = new Id("1000");
        when(restaurantRepository.find(RESTAURANT_ID)).thenReturn(RESTAURANT);

        assertThrows(
                NotRestaurantOwnerException.class,
                () -> {
                    restaurantService.deleteRestaurantById(RESTAURANT_ID, wrongOwnerId);
                });
    }

    @Test
    void givenNonExistingRestaurant_whenDeletingRestaurant_thenThrowsRestaurantNotFoundException() {
        var wrongRestaurantId = new Id("1000");
        when(restaurantRepository.find(wrongRestaurantId))
                .thenThrow(new RestaurantNotFoundException("Le restaurant n'existe pas"));

        assertThrows(
                RestaurantNotFoundException.class,
                () -> {
                    restaurantService.deleteRestaurantById(wrongRestaurantId, OWNER_ID);
                });
    }

    @Test
    void givenValidMenu_whenAddingMenu_thenMenuIsAdded() {
        var menu = new Menu(RESTAURANT_ID, List.of(new Dish("name")));
        when(restaurantRepository.find(menu.restaurantId())).thenReturn(RESTAURANT);

        restaurantService.addMenu(menu, OWNER_ID);

        verify(menuRepository).saveMenu(menu);
    }

    @Test
    void givenValidMenu_whenAddingMenu_thenMenuIsValidated() {
        var menu = new Menu(RESTAURANT_ID, List.of(new Dish("name")));
        when(restaurantRepository.find(menu.restaurantId())).thenReturn(RESTAURANT);

        restaurantService.addMenu(menu, OWNER_ID);

        verify(menuValidator).validateMenu(menu);
    }
}
