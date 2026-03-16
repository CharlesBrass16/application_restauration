/* (C)2024 */
package ca.ulaval.glo2003.domain.restaurant;

import static org.junit.jupiter.api.Assertions.*;

import ca.ulaval.glo2003.domain.reservation.ReservationConfiguration;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.Test;

class RestaurantFilterTest {

    private final RestaurantFilter restaurantFilter = new RestaurantFilter();
    public static final int CAPACITY = 10;
    public static final ReservationConfiguration DEFAULT_CONFIGURATION =
            ReservationConfiguration.Default();
    String nameWithCase = "Abc";
    String nameInLowerCase = "Cde";
    String nameWithMultipleWords = "Ab de";

    LocalTime earlyOpeningTime = LocalTime.of(8, 0);
    LocalTime LateOpeningTime = LocalTime.of(13, 0);
    LocalTime earlyClosingTime = LocalTime.of(12, 0);
    LocalTime LateClosingTime = LocalTime.of(22, 0);

    LocalTime fromOptionOpen = LocalTime.of(1, 0);
    LocalTime toOptionClose = LocalTime.of(3, 30);

    Restaurant restaurant1 =
            new Restaurant(
                    null,
                    null,
                    nameWithCase,
                    CAPACITY,
                    earlyOpeningTime,
                    LateClosingTime,
                    DEFAULT_CONFIGURATION);
    Restaurant restaurant2 =
            new Restaurant(
                    null,
                    null,
                    nameInLowerCase,
                    CAPACITY,
                    earlyOpeningTime,
                    earlyClosingTime,
                    DEFAULT_CONFIGURATION);
    Restaurant restaurant3 =
            new Restaurant(
                    null,
                    null,
                    nameWithMultipleWords,
                    CAPACITY,
                    LateOpeningTime,
                    LateClosingTime,
                    DEFAULT_CONFIGURATION);

    Restaurant nightRestaurant =
            new Restaurant(
                    null,
                    null,
                    nameWithMultipleWords,
                    CAPACITY,
                    fromOptionOpen,
                    toOptionClose,
                    DEFAULT_CONFIGURATION);

    List<Restaurant> restaurantList = List.of(restaurant1, restaurant2, restaurant3);

    @Test
    public void
            givenOneRestaurantMatching_whenFilteringWithStringEqualToAName_thenMatchingRestaurantsAreReturned() {
        final String nameOption = nameWithCase;
        var option = new RestaurantSearchOptions(nameOption, null, null);
        var expected = List.of(restaurant1);

        var actual = restaurantFilter.filter(restaurantList, option);

        assertEquals(expected, actual);
    }

    @Test
    public void
            givenTwoRestaurantsMatching_whenFilteringWithStringContainInAWord_thenMatchingRestaurantsAreReturned() {
        final String nameOption = "c";
        var option = new RestaurantSearchOptions(nameOption, null, null);
        var expected = List.of(restaurant1, restaurant2);

        var actual = restaurantFilter.filter(restaurantList, option);

        assertEquals(expected, actual);
    }

    @Test
    public void
            givenOneRestaurantMatching_whenFilteringWithStringContainedInTwoWords_thenMatchingRestaurantsAreReturned() {
        final String nameOption = "bd";
        var option = new RestaurantSearchOptions(nameOption, null, null);
        var expected = List.of(restaurant3);

        var actual = restaurantFilter.filter(restaurantList, option);

        assertEquals(expected, actual);
    }

    @Test
    public void
            givenOneRestaurantsMatching_whenFilteringWithLowerCaseStringContainInAWord_thenMatchingRestaurantsAreReturned() {
        final String nameOption = "abc";
        var option = new RestaurantSearchOptions(nameOption, null, null);
        var expected = List.of(restaurant1);

        var actual = restaurantFilter.filter(restaurantList, option);

        assertEquals(expected, actual);
    }

    @Test
    public void
            givenOneRestaurantMatching_whenFilteringWithStringSeparatedAndContainedInAWord_thenMatchingRestaurantsAreReturned() {
        final String nameOption = "ac";
        var option = new RestaurantSearchOptions(nameOption, null, null);
        var expected = List.of(restaurant1);

        var actual = restaurantFilter.filter(restaurantList, option);

        assertEquals(expected, actual);
    }

    @Test
    public void
            givenThreeRestaurantsInOpeningWindow_whenFilteringWithFromTimeOption_thenMatchingRestaurantsAreReturned() {
        final LocalTime fromOption = LocalTime.of(8, 0);
        var option = new RestaurantSearchOptions(null, fromOption, null);
        var expected = List.of(restaurant1, restaurant2);

        var actual = restaurantFilter.filter(restaurantList, option);

        assertEquals(expected, actual);
    }

    @Test
    public void
            givenThreeRestaurantsInOpeningWindow_whenFilteringWithToTimeOption_thenMatchingRestaurantsAreReturned() {
        final LocalTime toOption = LocalTime.of(22, 0);
        var option = new RestaurantSearchOptions(null, null, toOption);
        var expected = List.of(restaurant1, restaurant3);

        var actual = restaurantFilter.filter(restaurantList, option);

        assertEquals(expected, actual);
    }

    @Test
    public void
            givenThreeRestaurantsInOpeningWindow_whenFilteringWithLargeToAndFromTimeOption_thenMatchingRestaurantsAreReturned() {
        final LocalTime fromOption = LocalTime.of(8, 0);
        final LocalTime toOption = LocalTime.of(22, 0);
        var option = new RestaurantSearchOptions(null, fromOption, toOption);
        var expected = List.of(restaurant1);

        var actual = restaurantFilter.filter(restaurantList, option);

        assertEquals(expected, actual);
    }

    @Test
    public void
            givenTwoRestaurantsInOpeningWindow_whenFilteringWithEarlyCloseFromTimeOption_thenMatchingRestaurantsAreReturned() {
        final LocalTime fromOption = LocalTime.of(13, 0);
        var option = new RestaurantSearchOptions(null, fromOption, null);
        var expected = List.of(restaurant1, restaurant3);

        var actual = restaurantFilter.filter(restaurantList, option);

        assertEquals(expected, actual);
    }

    @Test
    public void
            givenTwoRestaurantsInOpeningWindow_whenFilteringWithEarlyCloseToTimeOption_thenMatchingRestaurantsAreReturned() {
        final LocalTime toOption = LocalTime.of(12, 0);
        var option = new RestaurantSearchOptions(null, null, toOption);
        var expected = List.of(restaurant1, restaurant2);

        var actual = restaurantFilter.filter(restaurantList, option);

        assertEquals(expected, actual);
    }

    @Test
    public void
            givenMultipleRestaurant_whenFilteringForNightRestaurants_thenMatchingRestaurantsAreReturned() {
        final LocalTime fromOption = LocalTime.of(2, 45);
        final LocalTime toOption = LocalTime.of(3, 30);
        var option = new RestaurantSearchOptions(null, fromOption, toOption);
        var expected = List.of(nightRestaurant);

        List<Restaurant> restaurantList =
                List.of(restaurant1, restaurant2, restaurant3, nightRestaurant);

        var actual = restaurantFilter.filter(restaurantList, option);

        assertEquals(expected, actual);
    }
}
