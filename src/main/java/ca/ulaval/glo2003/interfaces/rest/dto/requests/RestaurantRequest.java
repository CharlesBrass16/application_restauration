/* (C)2024 */
package ca.ulaval.glo2003.interfaces.rest.dto.requests;

import ca.ulaval.glo2003.domain.reservation.ReservationConfiguration;
import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;
import java.time.LocalTime;

public class RestaurantRequest {
    @JsonbProperty("name")
    public String name;

    @JsonbProperty("capacity")
    public int capacity;

    @JsonbProperty("hours")
    public Hours hours;

    @JsonbProperty("reservations")
    public ReservationsConfig reservationsConfig;

    public static class Hours {
        @JsonbProperty("open")
        public LocalTime open;

        @JsonbProperty("close")
        public LocalTime close;
    }

    public static class ReservationsConfig {
        public int duration;

        @JsonbCreator()
        public void setDuration(Integer duration) {
            this.duration =
                    (duration == null)
                            ? ReservationConfiguration.Default().getDuration()
                            : duration;
        }
    }
}
