/* (C)2024 */
package ca.ulaval.glo2003.interfaces.rest.dto.responses;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbPropertyOrder;
import java.util.Objects;

@JsonbPropertyOrder({"id", "name", "capacity", "hours", "reservations"})
public class RestaurantResponse {
    @JsonbCreator
    public RestaurantResponse(
            @JsonbProperty("id") String id,
            @JsonbProperty("name") String name,
            @JsonbProperty("capacity") int capacity,
            @JsonbProperty("hours") Hours hours,
            @JsonbProperty("reservations") ReservationsConfig reservationsConfig) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.hours = hours;
        this.reservationsConfig = reservationsConfig;
    }

    @JsonbProperty("id")
    public String id;

    @JsonbProperty("name")
    public String name;

    @JsonbProperty("capacity")
    public int capacity;

    @JsonbProperty("hours")
    public Hours hours;

    @JsonbProperty("reservations")
    public ReservationsConfig reservationsConfig;

    @JsonbPropertyOrder({"open", "close"})
    public static class Hours {

        public String open;
        public String close;

        @JsonbCreator
        public Hours(@JsonbProperty("open") String open, @JsonbProperty("close") String close) {
            this.open = open;
            this.close = close;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Hours hours)) {
                return false;
            }
            return Objects.equals(open, hours.open) && Objects.equals(close, hours.close);
        }

        @Override
        public int hashCode() {
            return Objects.hash(open, close);
        }
    }

    public static class ReservationsConfig {
        @JsonbProperty("duration")
        public int duration;

        @JsonbCreator
        public ReservationsConfig(@JsonbProperty("duration") int duration) {
            this.duration = duration;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof ReservationsConfig that)) {
                return false;
            }
            return duration == that.duration;
        }

        @Override
        public int hashCode() {
            return Objects.hash(duration);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RestaurantResponse that)) {
            return false;
        }
        return capacity == that.capacity
                && Objects.equals(id, that.id)
                && Objects.equals(name, that.name)
                && Objects.equals(hours, that.hours)
                && Objects.equals(reservationsConfig, that.reservationsConfig);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, capacity, hours, reservationsConfig);
    }
}
