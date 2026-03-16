/* (C)2024 */
package ca.ulaval.glo2003.interfaces.rest.mappers.responses;

import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import ca.ulaval.glo2003.interfaces.rest.dto.responses.RestaurantResponse;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class RestaurantResponseMapper {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    public static RestaurantResponse toResponse(Restaurant restaurant) {
        String openingTime = restaurant.getOpeningTime().format(TIME_FORMATTER);
        String closingTime = restaurant.getClosingTime().format(TIME_FORMATTER);
        int reservationDuration = restaurant.getReservationDuration();

        RestaurantResponse.Hours hours = new RestaurantResponse.Hours(openingTime, closingTime);

        RestaurantResponse.ReservationsConfig reservationsConfig =
                new RestaurantResponse.ReservationsConfig(reservationDuration);

        return new RestaurantResponse(
                restaurant.getId().value(),
                restaurant.getName(),
                restaurant.getCapacity(),
                hours,
                reservationsConfig);
    }

    public static List<RestaurantResponse> toResponse(List<Restaurant> restaurants) {
        return restaurants.stream().map(RestaurantResponseMapper::toResponse).toList();
    }
}
