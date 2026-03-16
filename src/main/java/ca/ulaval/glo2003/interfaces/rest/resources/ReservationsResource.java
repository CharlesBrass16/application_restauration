/* (C)2024 */
package ca.ulaval.glo2003.interfaces.rest.resources;

import ca.ulaval.glo2003.application.ReservationService;
import ca.ulaval.glo2003.application.RestaurantService;
import ca.ulaval.glo2003.domain.customer.Customer;
import ca.ulaval.glo2003.domain.exceptions.InvalidParameterException;
import ca.ulaval.glo2003.domain.exceptions.MissingParameterException;
import ca.ulaval.glo2003.domain.reservation.ReservationSearchOptions;
import ca.ulaval.glo2003.domain.shared.Id;
import ca.ulaval.glo2003.interfaces.rest.dto.requests.ReservationRequest;
import ca.ulaval.glo2003.interfaces.rest.mappers.responses.ReservationResponseMapper;
import ca.ulaval.glo2003.interfaces.rest.mappers.responses.ReservationsFilterResponseMapper;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Path("")
public class ReservationsResource {

    private final ReservationService reservationService;
    private final RestaurantService restaurantService;

    @Inject
    public ReservationsResource(
            ReservationService reservationService, RestaurantService restaurantService) {
        this.reservationService = reservationService;
        this.restaurantService = restaurantService;
    }

    @POST
    @Path("/restaurants/{restaurantId}/reservations")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createReservation(
            @Context UriInfo context,
            @PathParam("restaurantId") String stringRestaurantId,
            ReservationRequest reservationRequest)
            throws MissingParameterException, InvalidParameterException {
        validateDate(reservationRequest);
        validateStartTime(reservationRequest);
        validateCustomer(reservationRequest);

        var restaurantId = Id.fromString(stringRestaurantId);

        Customer customer =
                reservationService.createCustomer(
                        reservationRequest.customer.name,
                        reservationRequest.customer.email,
                        reservationRequest.customer.phoneNumber);

        var newId =
                reservationService.createReservation(
                        restaurantId,
                        customer,
                        reservationRequest.date,
                        reservationRequest.startTime,
                        reservationRequest.groupSize);

        return buildOkResponse(newId, context);
    }

    @DELETE
    @Path("/reservations/{reservationNumber}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteReservation(
            @PathParam("reservationNumber") String stringReservationNumber)
            throws MissingParameterException {
        if (stringReservationNumber == null) {
            throw new MissingParameterException("Le numéro de réservation est vide");
        }

        var reservationId = Id.fromString(stringReservationNumber);
        reservationService.deleteReservation(reservationId);
        return Response.noContent().build();
    }

    @GET
    @Path("/reservations/{reservationNumber}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReservation(@PathParam("reservationNumber") String stringReservationNumber) {
        var reservationNumber = Id.fromString(stringReservationNumber);
        var reservation = reservationService.getReservation(reservationNumber);
        var restaurant = restaurantService.getRestaurantFromId(reservation.getRestaurantId());
        var response = ReservationResponseMapper.toResponse(reservation, restaurant);
        return Response.ok(response).build();
    }

    @GET
    @Path("/restaurants/{restaurantId}/reservations")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReservationWithFilters(
            @PathParam("restaurantId") String stringRestaurantId,
            @HeaderParam("Owner") String stringOwnerId,
            @QueryParam("date") String stringDate,
            @QueryParam("customerName") String customerName) {

        if (stringOwnerId == null || stringOwnerId.isBlank()) {
            throw new MissingParameterException("L'identifiant du propriétaire est vide");
        }

        var restaurantId = Id.fromString(stringRestaurantId);
        var ownerId = Id.fromString(stringOwnerId);
        if (stringDate != null) {
            try {
                LocalDate.parse(stringDate);
            } catch (DateTimeParseException e) {
                throw new InvalidParameterException("format de date invalide");
            }
        }
        LocalDate date = stringDate != null ? LocalDate.parse(stringDate) : null;
        if (date == null) {
            var reservations =
                    reservationService.findReservations(
                            new ReservationSearchOptions(customerName, null),
                            restaurantId,
                            ownerId);
            var response = ReservationsFilterResponseMapper.toResponse(reservations);
            return Response.ok(response).build();
        }
        if (customerName == null) {
            var reservations =
                    reservationService.findReservations(
                            new ReservationSearchOptions(null, date), restaurantId, ownerId);
            var response = ReservationsFilterResponseMapper.toResponse(reservations);
            return Response.ok(response).build();
        }

        ReservationSearchOptions reservationSearchOptions =
                new ReservationSearchOptions(customerName, date);
        var reservations =
                reservationService.findReservations(
                        reservationSearchOptions, restaurantId, ownerId);
        var response = ReservationsFilterResponseMapper.toResponse(reservations);
        return Response.ok(response).build();
    }

    private void validateDate(ReservationRequest reservationRequest) {
        if (reservationRequest.date == null) {
            throw new MissingParameterException("La date est vide");
        }
    }

    private void validateStartTime(ReservationRequest reservationRequest) {
        if (reservationRequest.startTime == null) {
            throw new MissingParameterException("L'heure de début est vide'");
        }
    }

    private void validateCustomer(ReservationRequest reservationRequest) {
        if (reservationRequest.customer == null) {
            throw new MissingParameterException("Le client est vide");
        }
        validateCustomerName(reservationRequest);
        validateCustomerEmail(reservationRequest);
        validateCustomerPhoneNumber(reservationRequest);
    }

    private void validateCustomerName(ReservationRequest reservationRequest) {
        if (reservationRequest.customer.name == null) {
            throw new MissingParameterException("Le nom du client est vide");
        }
    }

    private void validateCustomerEmail(ReservationRequest reservationRequest) {
        if (reservationRequest.customer.email == null) {
            throw new MissingParameterException("L'adresse courriel du client est vide");
        }
    }

    private void validateCustomerPhoneNumber(ReservationRequest reservationRequest) {
        if (reservationRequest.customer.phoneNumber == null) {
            throw new MissingParameterException("Le numéro de téléphone du client est vide");
        }
    }

    private Response buildOkResponse(Id reservationNumber, UriInfo context) {
        URI location = getUriResponse(reservationNumber, context);
        return Response.ok().status(201).location(location).build();
    }

    private URI getUriResponse(Id reservationNumber, UriInfo context) {
        String formattedLocation =
                String.format(
                        "%sreservations/%s",
                        context.getBaseUriBuilder(), reservationNumber.value());
        return URI.create(formattedLocation);
    }
}
