/* (C)2024 */
package ca.ulaval.glo2003.interfaces.rest.mappers.responses;

import ca.ulaval.glo2003.domain.reservation.ReservationConfiguration;
import ca.ulaval.glo2003.interfaces.rest.dto.requests.RestaurantRequest;

public class ReservationConfigurationMapper {

    public static ReservationConfiguration mapConfiguration(RestaurantRequest restaurantRequest) {
        if (restaurantRequest.reservationsConfig != null
                && restaurantRequest.reservationsConfig.duration > 0) {
            return new ReservationConfiguration(restaurantRequest.reservationsConfig.duration);
        } else {
            return ReservationConfiguration.Default();
        }
    }
}
