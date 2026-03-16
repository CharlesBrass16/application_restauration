/* (C)2024 */
package ca.ulaval.glo2003.interfaces.rest.dto.responses;

import ca.ulaval.glo2003.domain.customer.Customer;
import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbPropertyOrder;

@JsonbPropertyOrder({"number", "date", "time", "groupSize", "customer"})
public class ReservationsFilterResponse {
    @JsonbProperty("number")
    public String number;

    @JsonbProperty("date")
    public String date;

    @JsonbProperty("groupSize")
    public int groupSize;

    @JsonbProperty("customer")
    public Customer customer;

    @JsonbProperty("time")
    public time time;

    @JsonbCreator
    public ReservationsFilterResponse(
            @JsonbProperty("number") String number,
            @JsonbProperty("date") String date,
            @JsonbProperty("time") time time,
            @JsonbProperty("groupSize") int groupSize,
            @JsonbProperty("customer") Customer customer) {
        this.number = number;
        this.date = date;
        this.groupSize = groupSize;
        this.customer = customer;
        this.time = time;
    }

    @JsonbPropertyOrder({"start", "end"})
    public static class time {
        @JsonbProperty("start")
        public String start;

        @JsonbProperty("end")
        public String end;

        @JsonbCreator
        public time(@JsonbProperty("start") String start, @JsonbProperty("end") String end) {
            this.start = start;
            this.end = end;
        }
    }
}
