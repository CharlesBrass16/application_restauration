/* (C)2024 */
package ca.ulaval.glo2003.domain.restaurant;

import java.time.LocalTime;
import java.util.List;

public class RestaurantFilter {
    public List<Restaurant> filter(
            List<Restaurant> restaurants, RestaurantSearchOptions restaurantSearchOptions) {

        if (restaurantSearchOptions == null) {
            return restaurants;
        }

        return restaurants.stream()
                .filter(r -> fuzzyCompare(r.getName(), restaurantSearchOptions.name()))
                .filter(restaurant -> compareFromTime(restaurantSearchOptions.from(), restaurant))
                .filter(restaurant -> compareToTime(restaurantSearchOptions.to(), restaurant))
                .toList();
    }

    private static boolean compareToTime(LocalTime to, Restaurant restaurant) {
        if (to != null) {
            return restaurant.getOpeningTime().isBefore(to)
                    && (restaurant.getClosingTime().isAfter(to)
                            || restaurant.getClosingTime().equals(to));
        }
        return true;
    }

    private static boolean compareFromTime(LocalTime from, Restaurant restaurant) {
        if (from != null) {
            return restaurant.getClosingTime().isAfter(from)
                    && (restaurant.getOpeningTime().isBefore(from)
                            || restaurant.getOpeningTime().equals(from));
        }
        return true;
    }

    private static boolean fuzzyCompare(String string, String comparator) {
        if (comparator == null) return true;

        String normalizedString = string.toLowerCase();
        var currentIndex = 0;

        for (char c : comparator.toLowerCase().toCharArray()) {
            boolean charFound = false;
            for (int i = currentIndex; i < normalizedString.length(); i++) {
                if (normalizedString.charAt(i) == c) {
                    currentIndex = i;
                    charFound = true;
                    break;
                }
                ;
            }
            if (!charFound) return false;
        }
        return true;
    }
}
