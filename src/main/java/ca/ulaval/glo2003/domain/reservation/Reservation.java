/* (C)2024 */
package ca.ulaval.glo2003.domain.reservation;

import ca.ulaval.glo2003.domain.customer.Customer;
import ca.ulaval.glo2003.domain.shared.Id;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Reservation {
    private final Id number;
    private final LocalDate date;
    private final int groupSize;
    private final LocalTime start;
    private final LocalTime end;
    private final Customer customer;
    private final Id restaurantId;

    public Reservation(
            Id number,
            LocalDate date,
            LocalTime start,
            LocalTime end,
            int groupSize,
            Customer customer,
            Id restaurantId) {
        this.number = number;
        this.date = date;
        this.groupSize = groupSize;
        this.start = start;
        this.end = end;
        this.customer = customer;
        this.restaurantId = restaurantId;
    }

    public Id getReservationNumber() {
        return number;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getGroupSize() {
        return groupSize;
    }

    public LocalTime getStart() {
        return start;
    }

    public LocalTime getEnd() {
        return end;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Id getRestaurantId() {
        return restaurantId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Reservation that)) {
            return false;
        }
        return groupSize == that.groupSize
                && Objects.equals(number, that.number)
                && Objects.equals(date, that.date)
                && Objects.equals(start, that.start)
                && Objects.equals(end, that.end)
                && Objects.equals(customer, that.customer)
                && Objects.equals(restaurantId, that.restaurantId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, date, groupSize, start, end, customer, restaurantId);
    }
}
