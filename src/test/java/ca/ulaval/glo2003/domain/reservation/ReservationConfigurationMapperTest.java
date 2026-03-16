/* (C)2024 */
package ca.ulaval.glo2003.domain.reservation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.ulaval.glo2003.interfaces.rest.dto.requests.RestaurantRequest;
import ca.ulaval.glo2003.interfaces.rest.mappers.responses.ReservationConfigurationMapper;
import org.junit.jupiter.api.Test;

public class ReservationConfigurationMapperTest {

    @Test
    void givenValidDuration_whenMapConfiguration_thenDurationIsAdded() {
        RestaurantRequest restaurantRequest = new RestaurantRequest();
        restaurantRequest.reservationsConfig = new RestaurantRequest.ReservationsConfig();
        restaurantRequest.reservationsConfig.duration = 30;

        ReservationConfiguration reservationConfiguration =
                ReservationConfigurationMapper.mapConfiguration(restaurantRequest);

        assertEquals(30, reservationConfiguration.getDuration());
    }

    @Test
    void givenInvalidDuration_whenMapConfiguration_thenDefaultDurationIsAdded() {
        RestaurantRequest restaurantRequest = new RestaurantRequest();
        restaurantRequest.reservationsConfig = new RestaurantRequest.ReservationsConfig();
        restaurantRequest.reservationsConfig.duration = -1;

        ReservationConfiguration reservationConfiguration =
                ReservationConfigurationMapper.mapConfiguration(restaurantRequest);

        assertEquals(60, reservationConfiguration.getDuration());
    }

    @Test
    void givenNullDuration_whenMapConfiguration_thenDefaultDurationIsAdded() {
        RestaurantRequest restaurantRequest = new RestaurantRequest();
        restaurantRequest.reservationsConfig = new RestaurantRequest.ReservationsConfig();

        ReservationConfiguration reservationConfiguration =
                ReservationConfigurationMapper.mapConfiguration(restaurantRequest);

        assertEquals(
                ReservationConfiguration.Default().getDuration(),
                reservationConfiguration.getDuration());
    }

    @Test
    void
            givenNullReservationConfiguration_whenMapConfiguration_thenCreateReservationConfigurationWithDefaultDuration() {

        RestaurantRequest restaurantRequest = new RestaurantRequest();

        ReservationConfiguration reservationConfiguration =
                ReservationConfigurationMapper.mapConfiguration(restaurantRequest);

        assertEquals(
                ReservationConfiguration.Default().getDuration(),
                reservationConfiguration.getDuration());
    }
}
