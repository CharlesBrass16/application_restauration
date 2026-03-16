/* (C)2024 */
package ca.ulaval.glo2003.infrastructure.persistence.memory;

import ca.ulaval.glo2003.domain.exceptions.RestaurantNotFoundException;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import ca.ulaval.glo2003.domain.restaurant.RestaurantRepository;
import ca.ulaval.glo2003.domain.shared.Id;
import java.util.LinkedList;
import java.util.List;

public class InMemoryRestaurantRepository implements RestaurantRepository {

    private final List<Restaurant> restaurants;

    public InMemoryRestaurantRepository() {
        restaurants = new LinkedList<>();
    }

    @Override
    public void add(Restaurant restaurant) {
        restaurants.add(restaurant);
    }

    @Override
    public void delete(Id restaurantId) {
        Restaurant restaurant = find(restaurantId);
        restaurants.remove(restaurant);
    }

    @Override
    public Restaurant find(Id restaurantId) {
        return restaurants.stream()
                .filter(restaurant -> restaurant.getId().equals(restaurantId))
                .findFirst()
                .orElseThrow(() -> new RestaurantNotFoundException("Le restaurant n'existe pas"));
    }

    @Override
    public List<Restaurant> findByOwnerId(Id ownerId) {
        return restaurants.stream()
                .filter(restaurant -> restaurant.getOwnerId().equals(ownerId))
                .toList();
    }

    @Override
    public List<Restaurant> findAll() {
        return restaurants;
    }
}
