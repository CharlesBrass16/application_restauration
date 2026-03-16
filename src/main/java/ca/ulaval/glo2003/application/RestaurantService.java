/* (C)2024 */
package ca.ulaval.glo2003.application;

import ca.ulaval.glo2003.domain.exceptions.NotRestaurantOwnerException;
import ca.ulaval.glo2003.domain.exceptions.RestaurantNotFoundException;
import ca.ulaval.glo2003.domain.reservation.Reservation;
import ca.ulaval.glo2003.domain.reservation.ReservationConfiguration;
import ca.ulaval.glo2003.domain.reservation.ReservationRepository;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import ca.ulaval.glo2003.domain.restaurant.RestaurantFactory;
import ca.ulaval.glo2003.domain.restaurant.RestaurantFilter;
import ca.ulaval.glo2003.domain.restaurant.RestaurantRepository;
import ca.ulaval.glo2003.domain.restaurant.RestaurantSearchOptions;
import ca.ulaval.glo2003.domain.restaurant.menu.Menu;
import ca.ulaval.glo2003.domain.restaurant.menu.MenuRepository;
import ca.ulaval.glo2003.domain.restaurant.menu.MenuValidator;
import ca.ulaval.glo2003.domain.restaurant.schedule.RestaurantSchedule;
import ca.ulaval.glo2003.domain.shared.Availability;
import ca.ulaval.glo2003.domain.shared.Id;
import jakarta.inject.Inject;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final ReservationRepository reservationRepository;
    private final RestaurantFactory restaurantFactory;
    private final RestaurantFilter restaurantFilter;
    private final MenuRepository menuRepository;
    private final MenuValidator menuValidator;

    @Inject
    public RestaurantService(
            RestaurantRepository restaurantRepository,
            ReservationRepository reservationRepository,
            RestaurantFactory restaurantFactory,
            RestaurantFilter restaurantFilter,
            MenuRepository menuRepository,
            MenuValidator menuValidator) {
        this.restaurantRepository = restaurantRepository;
        this.reservationRepository = reservationRepository;
        this.restaurantFactory = restaurantFactory;
        this.restaurantFilter = restaurantFilter;
        this.menuRepository = menuRepository;
        this.menuValidator = menuValidator;
    }

    public Id createRestaurant(
            Id ownerId,
            String name,
            int capacity,
            LocalTime openingTime,
            LocalTime closingTime,
            ReservationConfiguration reservationDuration) {
        Restaurant restaurant =
                restaurantFactory.create(
                        ownerId, name, capacity, openingTime, closingTime, reservationDuration);
        restaurantRepository.add(restaurant);
        return restaurant.getId();
    }

    public Restaurant getRestaurant(Id restaurantId, Id ownerId) {
        var restaurant = getRestaurantFromId(restaurantId);
        if (!restaurant.getOwnerId().equals(ownerId)) {
            throw new NotRestaurantOwnerException(
                    "L'owner n'est pas le propriétaire du restaurant");
        }
        return restaurant;
    }

    public Restaurant getRestaurantFromId(Id restaurantId) {
        return restaurantRepository.find(restaurantId);
    }

    public List<Restaurant> findRestaurants(RestaurantSearchOptions option) {
        var restaurants = restaurantRepository.findAll();
        return restaurantFilter.filter(restaurants, option);
    }

    public List<Restaurant> getRestaurantsFromOwnerId(Id ownerId) {
        return restaurantRepository.findByOwnerId(ownerId);
    }

    public List<Availability> getAvailabilities(Id restaurantId, LocalDate date) {
        Restaurant restaurant = getRestaurantFromId(restaurantId);
        List<Reservation> sameDayReservations =
                reservationRepository.listSameDay(restaurantId, date);
        RestaurantSchedule restaurantSchedule =
                new RestaurantSchedule(restaurant, sameDayReservations);
        return restaurantSchedule.getAvailabilities();
    }

    public void deleteRestaurantById(Id restaurantId, Id ownerId) {
        var restaurant = restaurantRepository.find(restaurantId);
        if (restaurant == null) {
            throw new RestaurantNotFoundException("Le restaurant n'existe pas");
        }
        if (!restaurant.getOwnerId().equals(ownerId)) {
            throw new NotRestaurantOwnerException("Vous n'êtes pas le propriétaire du restaurant");
        }
        reservationRepository.deleteAllFromRestaurant(restaurantId);
        menuRepository.removeMenuFromRestaurant(restaurantId);

        restaurantRepository.delete(restaurant.getId());
    }

    public void addMenu(Menu menu, Id ownerId) {
        getRestaurant(menu.restaurantId(), ownerId);
        menuValidator.validateMenu(menu);
        menuRepository.saveMenu(menu);
    }

    public void deleteMenuFromRestaurant(Id restaurantId, Id ownerId) {
        getRestaurant(restaurantId, ownerId);
        menuRepository.removeMenuFromRestaurant(restaurantId);
    }

    public Menu findMenuForRestaurant(Id restaurantId) {
        return menuRepository.findMenuFromRestaurant(restaurantId);
    }
}
