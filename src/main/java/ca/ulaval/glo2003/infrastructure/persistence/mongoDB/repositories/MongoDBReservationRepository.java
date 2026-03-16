/* (C)2024 */
package ca.ulaval.glo2003.infrastructure.persistence.mongoDB.repositories;

import ca.ulaval.glo2003.domain.customer.Customer;
import ca.ulaval.glo2003.domain.exceptions.ReservationNotFoundException;
import ca.ulaval.glo2003.domain.reservation.Reservation;
import ca.ulaval.glo2003.domain.reservation.ReservationRepository;
import ca.ulaval.glo2003.domain.shared.Id;
import ca.ulaval.glo2003.infrastructure.persistence.mongoDB.MongoDatastore;
import ca.ulaval.glo2003.infrastructure.persistence.mongoDB.model.ReservationModel;
import dev.morphia.query.filters.Filters;
import jakarta.inject.Inject;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class MongoDBReservationRepository implements ReservationRepository {

    private final MongoDatastore datastore;

    @Inject
    public MongoDBReservationRepository(MongoDatastore datastore) {
        this.datastore = datastore;
    }

    private static ReservationModel fromReservation(Reservation reservation) {
        ReservationModel reservationModel = new ReservationModel();
        reservationModel.id = reservation.getReservationNumber().value();
        reservationModel.date = reservation.getDate().format(DateTimeFormatter.ISO_DATE);
        reservationModel.start = reservation.getStart().format(DateTimeFormatter.ISO_TIME);
        reservationModel.end = reservation.getEnd().format(DateTimeFormatter.ISO_TIME);
        reservationModel.groupSize = reservation.getGroupSize();
        reservationModel.name = reservation.getCustomer().getName();
        reservationModel.email = reservation.getCustomer().getEmail();
        reservationModel.phoneNumber = reservation.getCustomer().getPhoneNumber();
        reservationModel.restaurantId = reservation.getRestaurantId().value();
        return reservationModel;
    }

    @Override
    public void add(Reservation reservation) {
        datastore.getDatastore().save(fromReservation(reservation));
    }

    @Override
    public void delete(Id reservationNumber) {
        datastore
                .getDatastore()
                .find(ReservationModel.class)
                .filter(Filters.eq("_id", reservationNumber.value()))
                .delete();
    }

    @Override
    public Reservation find(Id reservationNumber) {
        ReservationModel reservationModel =
                datastore
                        .getDatastore()
                        .find(ReservationModel.class)
                        .filter(Filters.eq("_id", reservationNumber.value()))
                        .first();
        if (reservationModel == null) {
            throw new ReservationNotFoundException("Reservation Not found");
        }
        return new Reservation(
                new Id(reservationModel.id),
                LocalDate.parse(reservationModel.date),
                LocalTime.parse(reservationModel.start),
                LocalTime.parse(reservationModel.end),
                reservationModel.groupSize,
                new Customer(
                        reservationModel.name,
                        reservationModel.email,
                        reservationModel.phoneNumber),
                new Id(reservationModel.restaurantId));
    }

    @Override
    public List<Reservation> listSameDay(Id restaurantId, LocalDate date) {
        List<ReservationModel> reservationModels =
                datastore
                        .getDatastore()
                        .find(ReservationModel.class)
                        .filter(
                                Filters.and(
                                        Filters.eq("restaurantId", restaurantId.value()),
                                        Filters.eq(
                                                "date", date.format(DateTimeFormatter.ISO_DATE))))
                        .iterator()
                        .toList();
        return reservationModels.stream()
                .map(
                        reservationModel ->
                                new Reservation(
                                        new Id(reservationModel.id),
                                        LocalDate.parse(reservationModel.date),
                                        LocalTime.parse(reservationModel.start),
                                        LocalTime.parse(reservationModel.end),
                                        reservationModel.groupSize,
                                        new Customer(
                                                reservationModel.name,
                                                reservationModel.email,
                                                reservationModel.phoneNumber),
                                        new Id(reservationModel.restaurantId)))
                .collect(Collectors.toList());
    }

    @Override
    public List<Reservation> findByRestaurantID(Id restaurantId) {
        List<ReservationModel> reservationModels =
                datastore
                        .getDatastore()
                        .find(ReservationModel.class)
                        .filter(Filters.eq("restaurantId", restaurantId.value()))
                        .iterator()
                        .toList();

        return reservationModels.stream()
                .map(
                        reservationModel ->
                                new Reservation(
                                        new Id(reservationModel.id),
                                        LocalDate.parse(reservationModel.date),
                                        LocalTime.parse(reservationModel.start),
                                        LocalTime.parse(reservationModel.end),
                                        reservationModel.groupSize,
                                        new Customer(
                                                reservationModel.name,
                                                reservationModel.email,
                                                reservationModel.phoneNumber),
                                        new Id(reservationModel.restaurantId)))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAllFromRestaurant(Id restaurantId) {
        datastore
                .getDatastore()
                .find(ReservationModel.class)
                .filter(Filters.eq("restaurantId", restaurantId.value()))
                .delete();
    }
}
