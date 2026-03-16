/* (C)2024 */
package ca.ulaval.glo2003.interfaces.rest.mappers;

import static org.junit.Assert.assertEquals;

import ca.ulaval.glo2003.domain.shared.Availability;
import ca.ulaval.glo2003.interfaces.rest.dto.responses.AvailabilityResponse;
import ca.ulaval.glo2003.interfaces.rest.mappers.responses.AvailabilityResponseMapper;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.Test;

public class AvailabilityResponseMapperTest {
    @Test
    void givenNoAvailability_whenConvertingToResponse_thenReturnsEmptyList() {
        assertEquals(
                List.of(),
                AvailabilityResponseMapper.toResponse(List.of(), LocalDate.of(2024, 4, 8)));
    }

    @Test
    void givenAvailabilities_whenConvertingToResponse_thenReturnsWellFormattedAvailabilities() {
        Availability availability1 = new Availability(LocalTime.of(10, 0), 0);
        Availability availability2 = new Availability(LocalTime.of(10, 15), 100);
        List<Availability> availabilities = List.of(availability1, availability2);
        var actual =
                AvailabilityResponseMapper.toResponse(availabilities, LocalDate.of(2024, 4, 8));
        AvailabilityResponse formattedAvailability1 =
                new AvailabilityResponse("2024-04-08T10:00:00", 0);
        AvailabilityResponse formattedAvailability2 =
                new AvailabilityResponse("2024-04-08T10:15:00", 100);
        List<AvailabilityResponse> expected =
                List.of(formattedAvailability1, formattedAvailability2);

        assertEquals(expected, actual);
    }
}
