/* (C)2024 */
package ca.ulaval.glo2003.interfaces.rest.dto.responses;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbPropertyOrder;

@JsonbPropertyOrder({"start", "remainingPlaces"})
public class AvailabilityResponse {
    @JsonbCreator
    public AvailabilityResponse(
            @JsonbProperty("start") String start,
            @JsonbProperty("remainingPlaces") int remainingPlaces) {
        this.start = start;
        this.remainingPlaces = remainingPlaces;
    }

    @JsonbProperty("start")
    public String start;

    @JsonbProperty("remainingPlaces")
    public int remainingPlaces;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AvailabilityResponse that)) {
            return false;
        }
        return start.equals(that.start) && remainingPlaces == that.remainingPlaces;
    }
}
