/* (C)2024 */
package ca.ulaval.glo2003.infrastructure.persistence.mongoDB.model;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;

@Entity("Reservations")
public class ReservationModel {
    @Id public String id;
    public String date;
    public String start;
    public String end;
    public int groupSize;
    public String name;
    public String email;
    public String phoneNumber;
    public String restaurantId;
}
