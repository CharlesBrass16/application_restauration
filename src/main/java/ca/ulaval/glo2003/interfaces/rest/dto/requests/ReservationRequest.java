/* (C)2024 */
package ca.ulaval.glo2003.interfaces.rest.dto.requests;

import jakarta.json.bind.annotation.JsonbProperty;
import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationRequest {
    @JsonbProperty("date")
    public LocalDate date;

    @JsonbProperty("startTime")
    public LocalTime startTime;

    @JsonbProperty("groupSize")
    public int groupSize;

    @JsonbProperty("customer")
    public Customer customer;

    public static class Customer {
        @JsonbProperty("name")
        public String name;

        @JsonbProperty("email")
        public String email;

        @JsonbProperty("phoneNumber")
        public String phoneNumber;
    }
}
