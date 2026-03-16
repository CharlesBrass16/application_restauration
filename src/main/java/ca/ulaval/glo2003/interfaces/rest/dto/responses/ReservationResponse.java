/* (C)2024 */
package ca.ulaval.glo2003.interfaces.rest.dto.responses;

import ca.ulaval.glo2003.domain.customer.Customer;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbPropertyOrder;

@JsonbPropertyOrder({"number", "date", "time", "groupSize", "customer", "restaurant"})
public class ReservationResponse {
    @JsonbProperty("number")
    public String number;

    @JsonbProperty("date")
    public String date;

    @JsonbProperty("time")
    public time time;

    @JsonbProperty("groupSize")
    public int groupSize;

    @JsonbProperty("customer")
    public Customer customer;

    @JsonbProperty("restaurant")
    public RestaurantResponse restaurant;

    public ReservationResponse(
            String number,
            String date,
            time time,
            int groupSize,
            Customer customer,
            RestaurantResponse restaurant) {
        this.number = number;
        this.date = date;
        this.time = time;
        this.groupSize = groupSize;
        this.customer = customer;
        this.restaurant = restaurant;
    }

    @JsonbPropertyOrder({"start", "end"})
    public static class time {
        @JsonbProperty("start")
        public String start;

        @JsonbProperty("end")
        public String end;

        public time(String start, String end) {
            this.start = start;
            this.end = end;
        }
    }
}
