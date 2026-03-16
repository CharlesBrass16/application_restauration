/* (C)2024 */
package ca.ulaval.glo2003.domain.restaurant;

import ca.ulaval.glo2003.domain.reservation.ReservationConfiguration;
import ca.ulaval.glo2003.domain.shared.Id;
import ca.ulaval.glo2003.domain.shared.IdFactory;
import jakarta.inject.Inject;
import java.time.LocalTime;

public class RestaurantFactory {

    private final RestaurantValidator restaurantValidator;
    private final IdFactory idFactory;

    @Inject
    public RestaurantFactory(RestaurantValidator restaurantValidator, IdFactory idFactory) {
        this.restaurantValidator = restaurantValidator;
        this.idFactory = idFactory;
    }

    public Restaurant create(
            Id ownerId,
            String name,
            int capacity,
            LocalTime openingTime,
            LocalTime closingTime,
            ReservationConfiguration reservationDuration) {
        restaurantValidator.validate(name, capacity, openingTime, closingTime);
        var id = idFactory.createId();
        return new Restaurant(
                id, ownerId, name, capacity, openingTime, closingTime, reservationDuration);
    }
}
