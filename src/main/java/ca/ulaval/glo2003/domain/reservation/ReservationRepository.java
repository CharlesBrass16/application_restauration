/* (C)2024 */
package ca.ulaval.glo2003.domain.reservation;

import ca.ulaval.glo2003.domain.shared.Id;
import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository {

    void add(Reservation reservation);

    void delete(Id reservationNumber);

    Reservation find(Id reservationNumber);

    List<Reservation> listSameDay(Id restaurantId, LocalDate date);

    List<Reservation> findByRestaurantID(Id restaurantId);

    void deleteAllFromRestaurant(Id restaurantId);
}
