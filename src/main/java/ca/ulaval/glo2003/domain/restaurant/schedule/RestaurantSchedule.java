/* (C)2024 */
package ca.ulaval.glo2003.domain.restaurant.schedule;

import ca.ulaval.glo2003.domain.reservation.Reservation;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import ca.ulaval.glo2003.domain.shared.Availability;
import ca.ulaval.glo2003.domain.shared.TimeManager;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RestaurantSchedule {
    private final Restaurant restaurant;
    private final LocalTime timeOfFirstReservation;
    private final LocalTime timeOfLastReservation;
    private final List<Reservation> reservations;
    private final HashMap<LocalTime, List<Reservation>> schedule;

    public RestaurantSchedule(Restaurant restaurant, List<Reservation> reservations) {
        this.restaurant = restaurant;
        timeOfFirstReservation = TimeManager.getFirstFixedTime(restaurant.getOpeningTime());
        timeOfLastReservation =
                TimeManager.getLastFixedTime(
                        restaurant
                                .getClosingTime()
                                .minusMinutes(restaurant.getReservationDuration()));
        this.reservations = reservations;
        this.schedule = new HashMap<>();
        initializeSchedule();
        addReservations();
    }

    public boolean canFitReservation(Reservation newReservation) {
        LocalTime time = newReservation.getStart();
        while (time.isBefore(newReservation.getEnd()) && !time.isAfter(timeOfLastReservation)) {
            int reservedPlaces = 0;
            for (Reservation reservation : schedule.get(time)) {
                reservedPlaces += reservation.getGroupSize();
                if (newReservation.getGroupSize() > restaurant.getCapacity() - reservedPlaces) {
                    return false;
                }
            }
            time = TimeManager.getNextFixedTime(time);
        }
        return true;
    }

    public List<Availability> getAvailabilities() {
        List<Availability> availabilities = new ArrayList<Availability>();
        LocalTime time = timeOfFirstReservation;
        while (!time.isAfter(timeOfLastReservation)) {
            int reservedPlaces = 0;
            for (Reservation reservation : schedule.get(time)) {
                reservedPlaces += reservation.getGroupSize();
            }
            availabilities.add(new Availability(time, restaurant.getCapacity() - reservedPlaces));
            time = TimeManager.getNextFixedTime(time);
        }
        return availabilities;
    }

    private void initializeSchedule() {
        LocalTime time = timeOfFirstReservation;
        while (!time.isAfter(timeOfLastReservation)) {
            schedule.put(time, new ArrayList<>());
            time = TimeManager.getNextFixedTime(time);
        }
    }

    private void addReservations() {
        for (Reservation reservation : reservations) {
            LocalTime time = reservation.getStart();
            while (time.isBefore(reservation.getEnd()) && !time.isAfter(timeOfLastReservation)) {
                schedule.get(time).add(reservation);
                time = TimeManager.getNextFixedTime(time);
            }
        }
    }
}
