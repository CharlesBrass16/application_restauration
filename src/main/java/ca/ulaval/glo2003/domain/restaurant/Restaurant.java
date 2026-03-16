/* (C)2024 */
package ca.ulaval.glo2003.domain.restaurant;

import ca.ulaval.glo2003.domain.reservation.ReservationConfiguration;
import ca.ulaval.glo2003.domain.shared.Id;
import java.time.LocalTime;
import java.util.Objects;

public class Restaurant {
    private final Id id;
    private final Id ownerId;
    private final String name;
    private final int capacity;
    private final LocalTime openingTime;
    private final LocalTime closingTime;
    private final ReservationConfiguration configuration;

    public Restaurant(
            Id id,
            Id ownerId,
            String name,
            int capacity,
            LocalTime openingTime,
            LocalTime closingTime,
            ReservationConfiguration configuration) {
        this.id = id;
        this.ownerId = ownerId;
        this.name = name;
        this.capacity = capacity;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.configuration = configuration;
    }

    public Id getId() {
        return id;
    }

    public Id getOwnerId() {
        return ownerId;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public LocalTime getOpeningTime() {
        return openingTime;
    }

    public LocalTime getClosingTime() {
        return closingTime;
    }

    public int getReservationDuration() {
        return configuration.getDuration();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Restaurant that = (Restaurant) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
