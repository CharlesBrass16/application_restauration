/* (C)2024 */
package ca.ulaval.glo2003.infrastructure.persistence.memory;

import ca.ulaval.glo2003.domain.exceptions.ReservationNotFoundException;
import ca.ulaval.glo2003.domain.reservation.Reservation;
import ca.ulaval.glo2003.domain.reservation.ReservationRepository;
import ca.ulaval.glo2003.domain.shared.Id;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class InMemoryReservationRepository implements ReservationRepository {

    private final List<Reservation> reservations;

    public InMemoryReservationRepository() {
        this.reservations = new LinkedList<>();
    }

    @Override
    public void add(Reservation reservation) {
        reservations.add(reservation);
    }

    @Override
    public void delete(Id reservationId) {
        Reservation reservation = find(reservationId);
        reservations.remove(reservation);
    }

    @Override
    public Reservation find(Id reservationNumber) {
        return reservations.stream()
                .filter(reservation -> reservation.getReservationNumber().equals(reservationNumber))
                .findFirst()
                .orElseThrow(() -> new ReservationNotFoundException("Reservation Not found"));
    }

    @Override
    public List<Reservation> listSameDay(Id restaurantId, LocalDate date) {
        return reservations.stream()
                .filter(
                        reservation ->
                                reservation.getRestaurantId().equals(restaurantId)
                                        && reservation.getDate().equals(date))
                .toList();
    }

    @Override
    public List<Reservation> findByRestaurantID(Id restaurantId) {
        return reservations.stream()
                .filter(reservation -> reservation.getRestaurantId().equals(restaurantId))
                .toList();
    }

    @Override
    public void deleteAllFromRestaurant(Id restaurantId) {
        reservations.removeIf(reservation -> reservation.getRestaurantId().equals(restaurantId));
    }
}
