/* (C)2024 */
package ca.ulaval.glo2003.domain.restaurant;

import ca.ulaval.glo2003.domain.shared.Id;
import java.util.List;

public interface RestaurantRepository {
    void add(Restaurant restaurant);

    void delete(Id restaurantId);

    Restaurant find(Id restaurantId);

    List<Restaurant> findByOwnerId(Id ownerId);

    List<Restaurant> findAll();
}
