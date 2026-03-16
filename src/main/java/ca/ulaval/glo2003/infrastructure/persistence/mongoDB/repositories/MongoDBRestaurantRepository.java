/* (C)2024 */
package ca.ulaval.glo2003.infrastructure.persistence.mongoDB.repositories;

import ca.ulaval.glo2003.domain.exceptions.RestaurantNotFoundException;
import ca.ulaval.glo2003.domain.reservation.ReservationConfiguration;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import ca.ulaval.glo2003.domain.restaurant.RestaurantRepository;
import ca.ulaval.glo2003.domain.shared.Id;
import ca.ulaval.glo2003.infrastructure.persistence.mongoDB.MongoDatastore;
import ca.ulaval.glo2003.infrastructure.persistence.mongoDB.model.RestaurantModel;
import dev.morphia.query.filters.Filters;
import jakarta.inject.Inject;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class MongoDBRestaurantRepository implements RestaurantRepository {

    private final MongoDatastore datastore;

    @Inject
    public MongoDBRestaurantRepository(MongoDatastore datastore) {
        this.datastore = datastore;
    }

    private static RestaurantModel fromRestaurant(Restaurant restaurant) {
        RestaurantModel restaurantModel = new RestaurantModel();
        restaurantModel.id = restaurant.getId().value();
        restaurantModel.owner = restaurant.getOwnerId().value();
        restaurantModel.name = restaurant.getName();
        restaurantModel.capacity = restaurant.getCapacity();
        restaurantModel.openingTime =
                restaurant.getOpeningTime().format(DateTimeFormatter.ISO_TIME);
        restaurantModel.closingTime =
                restaurant.getClosingTime().format(DateTimeFormatter.ISO_TIME);
        restaurantModel.config = restaurant.getReservationDuration();
        return restaurantModel;
    }

    @Override
    public void add(Restaurant restaurant) {
        datastore.getDatastore().save(fromRestaurant(restaurant));
    }

    @Override
    public Restaurant find(Id restaurantId) {
        RestaurantModel restaurantModel =
                datastore
                        .getDatastore()
                        .find(RestaurantModel.class)
                        .filter(Filters.eq("_id", restaurantId.value()))
                        .first();
        if (restaurantModel == null) {
            throw new RestaurantNotFoundException("Restaurant not found");
        }

        return new Restaurant(
                restaurantId,
                new Id(restaurantModel.owner),
                restaurantModel.name,
                restaurantModel.capacity,
                LocalTime.parse(restaurantModel.openingTime),
                LocalTime.parse(restaurantModel.closingTime),
                new ReservationConfiguration(restaurantModel.config));
    }

    @Override
    public List<Restaurant> findByOwnerId(Id ownerId) {
        List<RestaurantModel> restaurantModels =
                datastore
                        .getDatastore()
                        .find(RestaurantModel.class)
                        .filter(Filters.eq("owner", ownerId.value()))
                        .iterator()
                        .toList();

        return restaurantModels.stream()
                .map(
                        restaurantModel ->
                                new Restaurant(
                                        new Id(restaurantModel.id),
                                        ownerId,
                                        restaurantModel.name,
                                        restaurantModel.capacity,
                                        LocalTime.parse(restaurantModel.openingTime),
                                        LocalTime.parse(restaurantModel.closingTime),
                                        new ReservationConfiguration(restaurantModel.config)))
                .collect(Collectors.toList());
    }

    @Override
    public List<Restaurant> findAll() {
        List<RestaurantModel> restaurantModels =
                datastore.getDatastore().find(RestaurantModel.class).iterator().toList();
        return restaurantModels.stream()
                .map(
                        restaurantModel ->
                                new Restaurant(
                                        new Id(restaurantModel.id),
                                        new Id(restaurantModel.owner),
                                        restaurantModel.name,
                                        restaurantModel.capacity,
                                        LocalTime.parse(restaurantModel.openingTime),
                                        LocalTime.parse(restaurantModel.closingTime),
                                        new ReservationConfiguration(restaurantModel.config)))
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Id restaurantId) {
        long deletedCount =
                datastore
                        .getDatastore()
                        .find(RestaurantModel.class)
                        .filter(Filters.eq("_id", restaurantId.value()))
                        .delete()
                        .getDeletedCount();
        if (deletedCount == 0) {
            throw new RestaurantNotFoundException("Le restaurant n'existe pas");
        }
    }
}
