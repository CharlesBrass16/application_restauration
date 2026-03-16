/* (C)2024 */
package ca.ulaval.glo2003.domain.reservation;

import java.util.List;

public class ReservationsFilter {
    public List<Reservation> filter(
            List<Reservation> reservations, ReservationSearchOptions reservationSearchOptions) {
        if (reservationSearchOptions.date() == null
                && reservationSearchOptions.customerName() == null) {
            return reservations;
        }
        return reservations.stream()
                .filter(
                        reservation ->
                                fuzzyCompare(
                                        reservation.getCustomer().getName(),
                                        reservationSearchOptions.customerName()))
                .filter(
                        reservation ->
                                (reservationSearchOptions.date() == null)
                                        || reservation
                                                .getDate()
                                                .equals(reservationSearchOptions.date()))
                .toList();
    }

    private static boolean fuzzyCompare(String string, String comparator) {
        if (comparator == null || comparator.trim().isEmpty()) return true;
        string = string.toLowerCase().replaceAll("\\s+", "");
        comparator = comparator.toLowerCase().replaceAll("\\s+", "");
        return string.contains(comparator);
    }
}
