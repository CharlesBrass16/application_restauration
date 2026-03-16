/* (C)2024 */
package ca.ulaval.glo2003.domain.restaurant.schedule;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ca.ulaval.glo2003.domain.reservation.Reservation;
import ca.ulaval.glo2003.domain.reservation.ReservationConfiguration;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import ca.ulaval.glo2003.domain.shared.Availability;
import ca.ulaval.glo2003.domain.shared.Id;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.Test;

public class RestaurantScheduleTest {
    private final Restaurant restaurant =
            new Restaurant(
                    null,
                    null,
                    "",
                    1,
                    LocalTime.of(10, 0),
                    LocalTime.of(12, 0),
                    new ReservationConfiguration(60));
    private final Restaurant restaurant2 =
            new Restaurant(
                    null,
                    null,
                    "",
                    2,
                    LocalTime.of(10, 0),
                    LocalTime.of(12, 0),
                    new ReservationConfiguration(60));
    private final LocalDate date = LocalDate.of(2024, 3, 31);
    private final LocalTime startTime = LocalTime.of(10, 0);
    private final LocalTime endTime = LocalTime.of(11, 0);
    private final Reservation reservation =
            new Reservation(
                    Id.fromString("acbd37dd-904a-4c83-bc53-ee8fc96789e1"),
                    date,
                    startTime,
                    endTime,
                    1,
                    null,
                    null);
    private final LocalTime startTime2 = LocalTime.of(11, 0);
    private final LocalTime endTime2 = LocalTime.of(12, 0);
    private final Reservation reservation2 =
            new Reservation(
                    Id.fromString("acbd37dd-904a-4c83-bc53-ee8fc96789e2"),
                    date,
                    startTime2,
                    endTime2,
                    1,
                    null,
                    null);
    private final Reservation reservation3 =
            new Reservation(
                    Id.fromString("acbd37dd-904a-4c83-bc53-ee8fc96789e3"),
                    date,
                    startTime2,
                    endTime2,
                    2,
                    null,
                    null);

    @Test
    void givenNewReservationNotExceedingCapacity_whenCheckingIfCanFit_thenReturnsTrue() {
        RestaurantSchedule restaurantSchedule = new RestaurantSchedule(restaurant, List.of());

        assertTrue(restaurantSchedule.canFitReservation((reservation)));
    }

    @Test
    void givenNewReservationExceedingCapacity_whenCheckingIfCanFit_thenReturnsFalse() {
        RestaurantSchedule restaurantSchedule =
                new RestaurantSchedule(restaurant, List.of(reservation));

        assertFalse(restaurantSchedule.canFitReservation((reservation)));
    }

    @Test
    void
            givenNewReservationNotExceedingCapacityOverlappingMultipleReservations_whenCheckingIfCanFit_thenReturnsTrue() {
        RestaurantSchedule restaurantSchedule =
                new RestaurantSchedule(restaurant2, List.of(reservation, reservation2));

        assertTrue(
                restaurantSchedule.canFitReservation(
                        (new Reservation(
                                Id.fromString("acbd37dd-904a-4c83-bc53-ee8fc96789e4"),
                                date,
                                LocalTime.of(10, 30),
                                LocalTime.of(11, 30),
                                1,
                                null,
                                null))));
    }

    @Test
    void
            givenNewReservationExceedingCapacityOverlappingMultipleReservations_whenCheckingIfCanFit_thenReturnsTrue() {
        RestaurantSchedule restaurantSchedule =
                new RestaurantSchedule(restaurant2, List.of(reservation, reservation3));

        assertFalse(
                restaurantSchedule.canFitReservation(
                        (new Reservation(
                                Id.fromString("acbd37dd-904a-4c83-bc53-ee8fc96789e4"),
                                date,
                                LocalTime.of(10, 30),
                                LocalTime.of(11, 30),
                                1,
                                null,
                                null))));
    }

    @Test
    void givenNoReservation_whenGettingAvailabilities_thenReturnsFullCapacityAvailabilities() {
        RestaurantSchedule restaurantSchedule = new RestaurantSchedule(restaurant, List.of());

        assertEquals(
                List.of(
                        new Availability(LocalTime.of(10, 0), 1),
                        new Availability(LocalTime.of(10, 15), 1),
                        new Availability(LocalTime.of(10, 30), 1),
                        new Availability(LocalTime.of(10, 45), 1),
                        new Availability(LocalTime.of(11, 0), 1)),
                restaurantSchedule.getAvailabilities());
    }

    @Test
    void
            givenReservations_whenGettingAvailabilities_thenReturnsAvailabilitiesWithAdjustedCapacity() {
        RestaurantSchedule restaurantSchedule =
                new RestaurantSchedule(restaurant, List.of(reservation));

        assertEquals(
                List.of(
                        new Availability(LocalTime.of(10, 0), 0),
                        new Availability(LocalTime.of(10, 15), 0),
                        new Availability(LocalTime.of(10, 30), 0),
                        new Availability(LocalTime.of(10, 45), 0),
                        new Availability(LocalTime.of(11, 0), 1)),
                restaurantSchedule.getAvailabilities());
    }
}
