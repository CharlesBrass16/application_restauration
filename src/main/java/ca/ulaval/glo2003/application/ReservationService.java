/* (C)2024 */
package ca.ulaval.glo2003.application;

import ca.ulaval.glo2003.domain.customer.Customer;
import ca.ulaval.glo2003.domain.customer.CustomerFactory;
import ca.ulaval.glo2003.domain.exceptions.InvalidParameterException;
import ca.ulaval.glo2003.domain.exceptions.NotRestaurantOwnerException;
import ca.ulaval.glo2003.domain.exceptions.ReservationNotFoundException;
import ca.ulaval.glo2003.domain.reservation.*;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import ca.ulaval.glo2003.domain.restaurant.RestaurantRepository;
import ca.ulaval.glo2003.domain.restaurant.schedule.RestaurantSchedule;
import ca.ulaval.glo2003.domain.shared.Id;
import ca.ulaval.glo2003.domain.shared.TimeManager;
import jakarta.inject.Inject;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationFactory reservationFactory;
    private final RestaurantRepository restaurantRepository;
    private final CustomerFactory customerFactory;
    private final ReservationsFilter reservationsFilter;

    @Inject
    public ReservationService(
            ReservationRepository reservationRepository,
            ReservationFactory reservationFactory,
            RestaurantRepository restaurantRepository,
            CustomerFactory customerFactory,
            ReservationsFilter reservationsFilter) {
        this.reservationRepository = reservationRepository;
        this.reservationFactory = reservationFactory;
        this.restaurantRepository = restaurantRepository;
        this.customerFactory = customerFactory;
        this.reservationsFilter = reservationsFilter;
    }

    public Id createReservation(
            Id restaurantId,
            Customer customer,
            LocalDate date,
            LocalTime startTime,
            int groupSize) {
        Restaurant restaurant = restaurantRepository.find(restaurantId);
        LocalTime fixedStartTime = TimeManager.getFirstFixedTime(startTime);
        Reservation reservation =
                reservationFactory.create(restaurant, customer, date, fixedStartTime, groupSize);
        List<Reservation> sameDayReservations =
                reservationRepository.listSameDay(restaurantId, date);
        RestaurantSchedule restaurantSchedule =
                new RestaurantSchedule(restaurant, sameDayReservations);
        if (!restaurantSchedule.canFitReservation(reservation)) {
            throw new InvalidParameterException(
                    "Le restaurant ne peut pas accueillir cette réservation");
        }
        reservationRepository.add(reservation);

        return reservation.getReservationNumber();
    }

    public void deleteReservation(Id stringRestaurantId) {
        var reservation = reservationRepository.find(stringRestaurantId);
        if (reservation == null) {
            throw new ReservationNotFoundException("La reservation n'existe pas");
        }
        reservationRepository.delete(reservation.getReservationNumber());
    }

    public Reservation getReservation(Id reservationNumber) {
        return reservationRepository.find(reservationNumber);
    }

    public Customer createCustomer(
            String customerName, String customerEmail, String customerPhoneNumber) {
        return customerFactory.create(customerName, customerEmail, customerPhoneNumber);
    }

    public List<Reservation> findReservations(
            ReservationSearchOptions option, Id restaurantId, Id ownerId) {
        var restaurant = restaurantRepository.find(restaurantId);

        if (!restaurant.getOwnerId().equals(ownerId)) {
            throw new NotRestaurantOwnerException("Not Restaurant Owner");
        }
        var reservations = reservationRepository.findByRestaurantID(restaurantId);
        return reservationsFilter.filter(reservations, option);
    }
}
