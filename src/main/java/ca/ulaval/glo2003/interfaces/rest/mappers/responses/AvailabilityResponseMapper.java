/* (C)2024 */
package ca.ulaval.glo2003.interfaces.rest.mappers.responses;

import ca.ulaval.glo2003.domain.shared.Availability;
import ca.ulaval.glo2003.interfaces.rest.dto.responses.AvailabilityResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AvailabilityResponseMapper {
    public static List<AvailabilityResponse> toResponse(
            List<Availability> availabilities, LocalDate date) {
        return availabilities.stream()
                .map(availability -> AvailabilityResponseMapper.format(availability, date))
                .toList();
    }

    private static AvailabilityResponse format(Availability availability, LocalDate date) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String start = date.toString() + "T" + availability.startTime().format(dateTimeFormatter);
        return new AvailabilityResponse(start, availability.remainingPlaces());
    }
}
