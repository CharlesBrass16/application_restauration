/* (C)2024 */
package ca.ulaval.glo2003.interfaces.rest.mappers.responses;

import ca.ulaval.glo2003.domain.reservation.Reservation;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import ca.ulaval.glo2003.interfaces.rest.dto.responses.ReservationResponse;
import ca.ulaval.glo2003.interfaces.rest.dto.responses.RestaurantResponse;
import java.time.format.DateTimeFormatter;

public class ReservationResponseMapper {
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static final DateTimeFormatter dateFormatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static ReservationResponse toResponse(Reservation reservation, Restaurant restaurant) {
        String start = reservation.getStart().format(timeFormatter);
        String end = reservation.getEnd().format(timeFormatter);
        String date = reservation.getDate().format(dateFormatter);

        ReservationResponse.time time = new ReservationResponse.time(start, end);
        RestaurantResponse restaurantResponse = RestaurantResponseMapper.toResponse(restaurant);

        return new ReservationResponse(
                reservation.getReservationNumber().value(),
                date,
                time,
                reservation.getGroupSize(),
                reservation.getCustomer(),
                restaurantResponse);
    }
}
