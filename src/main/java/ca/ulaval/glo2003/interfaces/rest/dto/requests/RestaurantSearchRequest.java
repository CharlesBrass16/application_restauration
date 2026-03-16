/* (C)2024 */
package ca.ulaval.glo2003.interfaces.rest.dto.requests;

import jakarta.json.bind.annotation.JsonbProperty;
import java.time.LocalTime;

public class RestaurantSearchRequest {
    @JsonbProperty("name")
    public String name;

    @JsonbProperty("opened")
    public RestaurantRequest.Hours hours;

    public static class Hours {
        @JsonbProperty("from")
        public LocalTime open;

        @JsonbProperty("to")
        public LocalTime close;
    }
}
