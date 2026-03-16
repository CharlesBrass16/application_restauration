/* (C)2024 */
package ca.ulaval.glo2003.domain.reservation;

import ca.ulaval.glo2003.domain.customer.Customer;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import ca.ulaval.glo2003.domain.shared.IdFactory;
import jakarta.inject.Inject;
import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationFactory {

    private final ReservationValidator reservationValidator;
    private final IdFactory idFactory;

    @Inject
    public ReservationFactory(ReservationValidator reservationValidator, IdFactory idFactory) {
        this.reservationValidator = reservationValidator;
        this.idFactory = idFactory;
    }

    public Reservation create(
            Restaurant restaurant,
            Customer customer,
            LocalDate date,
            LocalTime startTime,
            int groupSize) {
        int reservationsDuration = restaurant.getReservationDuration();
        LocalTime endTime = startTime.plusMinutes(reservationsDuration);
        reservationValidator.validate(startTime, endTime, groupSize, restaurant);
        var id = idFactory.createId();

        return new Reservation(
                id, date, startTime, endTime, groupSize, customer, restaurant.getId());
    }
}
