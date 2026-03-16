/* (C)2024 */
package ca.ulaval.glo2003.infrastructure.persistence.mongoDB.model;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;

@Entity("Restaurant")
public class RestaurantModel {
    @Id public String id;
    public String owner;
    public String name;
    public int capacity;
    public String openingTime;
    public String closingTime;
    public int config;
}
