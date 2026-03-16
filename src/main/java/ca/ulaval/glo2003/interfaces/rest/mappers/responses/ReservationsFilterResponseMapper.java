/* (C)2024 */
package ca.ulaval.glo2003.interfaces.rest.mappers.responses;

import ca.ulaval.glo2003.domain.reservation.Reservation;
import ca.ulaval.glo2003.interfaces.rest.dto.responses.ReservationsFilterResponse;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ReservationsFilterResponseMapper {
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static final DateTimeFormatter dateFormatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static ReservationsFilterResponse toResponse(Reservation reservation) {
        String start = reservation.getStart().format(timeFormatter);
        String end = reservation.getEnd().format(timeFormatter);
        String date = reservation.getDate().format(dateFormatter);

        ReservationsFilterResponse.time time = new ReservationsFilterResponse.time(start, end);

        return new ReservationsFilterResponse(
                reservation.getReservationNumber().value(),
                date,
                time,
                reservation.getGroupSize(),
                reservation.getCustomer());
    }

    public static List<ReservationsFilterResponse> toResponse(List<Reservation> reservations) {
        return reservations.stream().map(ReservationsFilterResponseMapper::toResponse).toList();
    }
}
