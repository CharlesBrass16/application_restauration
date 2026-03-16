/* (C)2024 */
package ca.ulaval.glo2003.domain.restaurant;

import static org.junit.jupiter.api.Assertions.*;

import ca.ulaval.glo2003.domain.exceptions.RestaurantNotFoundException;
import ca.ulaval.glo2003.domain.reservation.ReservationConfiguration;
import ca.ulaval.glo2003.domain.shared.Id;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public abstract class RestaurantRepositoryIntegrationTest {
    protected abstract RestaurantRepository createRepository();

    private RestaurantRepository restaurantRepository;

    private static final String FIRST_RESTAURANT_STRING_ID = "456879654321";
    private static final String SECOND_RESTAURANT_STRING_ID = "4568796543";
    private static final Id FIRST_RESTAURANT_ID = new Id(FIRST_RESTAURANT_STRING_ID);
    private static final Id SECOND_RESTAURANT_ID = new Id(SECOND_RESTAURANT_STRING_ID);
    private static final String OWNER_STRING_ID = "123456789";
    private static final String SECOND_OWNER_STRING_ID = "12345678";
    private static final Id OWNER_ID = new Id(OWNER_STRING_ID);
    private static final Id SECOND_OWNER_ID = new Id(SECOND_OWNER_STRING_ID);
    private static final String name = "name";
    private static final int capacity = 100;
    private static final LocalTime openingTime = LocalTime.of(10, 0);
    private static final LocalTime closingTime = LocalTime.of(14, 0);
    private Restaurant firstRestaurantTest;
    private Restaurant secondRestaurantTest;
    public static final ReservationConfiguration DEFAULT_RESERVATION_CONFIGURATION =
            ReservationConfiguration.Default();

    @BeforeEach
    void setUpRestaurantRepository() {
        restaurantRepository = createRepository();
        firstRestaurantTest =
                new Restaurant(
                        FIRST_RESTAURANT_ID,
                        OWNER_ID,
                        name,
                        capacity,
                        openingTime,
                        closingTime,
                        DEFAULT_RESERVATION_CONFIGURATION);
        secondRestaurantTest =
                new Restaurant(
                        SECOND_RESTAURANT_ID,
                        SECOND_OWNER_ID,
                        name,
                        capacity,
                        openingTime,
                        closingTime,
                        DEFAULT_RESERVATION_CONFIGURATION);
        restaurantRepository.add(firstRestaurantTest);
        restaurantRepository.add(secondRestaurantTest);
    }

    @Test
    void givenValidIdRestaurant_whenFindRestaurant_thenRestaurantIsReturned() {
        assertEquals(firstRestaurantTest, restaurantRepository.find(FIRST_RESTAURANT_ID));
    }

    @Test
    void givenInvalidIdRestaurant_whenGetRestaurant_thenThrowsRestaurantNotFoundException() {
        Id INVALID_RESTAURANT_ID = new Id("1234");
        assertThrows(
                RestaurantNotFoundException.class,
                () -> restaurantRepository.find(INVALID_RESTAURANT_ID));
    }

    @Test
    void givenTwoSavedRestaurant_whenFindAllRestaurant_thenRestaurantAreReturned() {
        assertEquals(
                List.of(firstRestaurantTest, secondRestaurantTest), restaurantRepository.findAll());
    }

    @Test
    void givenValidIdOwnerRestaurant_whenGetRestaurantsFromOwnerId_thenRestaurantsAreReturned() {
        assertEquals(List.of(firstRestaurantTest), restaurantRepository.findByOwnerId(OWNER_ID));
    }

    @Test
    void givenExistingRestaurant_whenDeletingRestaurant_thenRestaurantIsDeleted() {
        restaurantRepository.delete(firstRestaurantTest.getId());

        assertThrows(
                RestaurantNotFoundException.class,
                () -> restaurantRepository.find(FIRST_RESTAURANT_ID));
    }

    @Test
    void givenExistingRestaurant_whenFindingRestaurantById_thenRestaurantIsReturned() {
        Restaurant restaurant = restaurantRepository.find(FIRST_RESTAURANT_ID);

        assertEquals(firstRestaurantTest, restaurant);
    }
}
