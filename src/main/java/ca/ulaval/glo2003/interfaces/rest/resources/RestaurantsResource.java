/* (C)2024 */
package ca.ulaval.glo2003.interfaces.rest.resources;

import ca.ulaval.glo2003.application.RestaurantService;
import ca.ulaval.glo2003.domain.exceptions.InvalidParameterException;
import ca.ulaval.glo2003.domain.exceptions.MissingParameterException;
import ca.ulaval.glo2003.domain.restaurant.RestaurantSearchOptions;
import ca.ulaval.glo2003.domain.shared.Id;
import ca.ulaval.glo2003.interfaces.rest.dto.requests.AddMenuRequest;
import ca.ulaval.glo2003.interfaces.rest.dto.requests.RestaurantRequest;
import ca.ulaval.glo2003.interfaces.rest.dto.requests.RestaurantSearchRequest;
import ca.ulaval.glo2003.interfaces.rest.mappers.requests.MenuMapper;
import ca.ulaval.glo2003.interfaces.rest.mappers.responses.AvailabilityResponseMapper;
import ca.ulaval.glo2003.interfaces.rest.mappers.responses.MenuResponseMapper;
import ca.ulaval.glo2003.interfaces.rest.mappers.responses.ReservationConfigurationMapper;
import ca.ulaval.glo2003.interfaces.rest.mappers.responses.RestaurantResponseMapper;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import java.net.URI;
import java.time.LocalDate;

@Path("")
public class RestaurantsResource {

    private final RestaurantService restaurantService;

    @Inject
    public RestaurantsResource(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @POST
    @Path("/restaurants")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createRestaurant(
            @Context UriInfo context,
            @HeaderParam("Owner") String stringOwnerId,
            RestaurantRequest restaurantRequest) {
        if (stringOwnerId == null
                || stringOwnerId.isBlank()
                || restaurantRequest.name == null
                || restaurantRequest.hours.open == null
                || restaurantRequest.hours.close == null) {
            throw new MissingParameterException("L'identifiant du propriétaire est vide");
        }

        var ownerId = Id.fromString(stringOwnerId);

        var duration = ReservationConfigurationMapper.mapConfiguration(restaurantRequest);

        var newId =
                restaurantService.createRestaurant(
                        ownerId,
                        restaurantRequest.name,
                        restaurantRequest.capacity,
                        restaurantRequest.hours.open,
                        restaurantRequest.hours.close,
                        duration);

        return buildOkResponse(newId, context);
    }

    @GET
    @Path("/restaurants/{restaurantId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRestaurant(
            @HeaderParam("Owner") String stringOwnerId,
            @PathParam("restaurantId") String stringRestaurantId) {

        if (stringOwnerId == null) {
            throw new MissingParameterException("L'identifiant du propriétaire est vide");
        }

        var restaurantId = Id.fromString(stringRestaurantId);
        var ownerId = Id.fromString(stringOwnerId);

        var restaurant = restaurantService.getRestaurant(restaurantId, ownerId);
        var response = RestaurantResponseMapper.toResponse(restaurant);
        return Response.ok(response).build();
    }

    @GET
    @Path("/restaurants")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRestaurants(@HeaderParam("Owner") String stringOwnerId) {
        if (stringOwnerId == null) {
            throw new MissingParameterException("L'identifiant du propriétaire est vide");
        }

        var ownerId = Id.fromString(stringOwnerId);

        var restaurants = restaurantService.getRestaurantsFromOwnerId(ownerId);
        var response = RestaurantResponseMapper.toResponse(restaurants);
        return Response.ok(response).build();
    }

    @POST
    @Path("/search/restaurants")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRestaurantsWithFilters(RestaurantSearchRequest restaurantSearchRequest) {
        if (restaurantSearchRequest == null) {
            var restaurants =
                    restaurantService.findRestaurants(
                            new RestaurantSearchOptions(null, null, null));
            var response = RestaurantResponseMapper.toResponse(restaurants);
            return Response.ok(response).build();
        }

        if (restaurantSearchRequest.hours == null) {
            var restaurants =
                    restaurantService.findRestaurants(
                            new RestaurantSearchOptions(restaurantSearchRequest.name, null, null));
            var response = RestaurantResponseMapper.toResponse(restaurants);
            return Response.ok(response).build();
        }

        RestaurantSearchOptions restaurantSearchOptions =
                new RestaurantSearchOptions(
                        restaurantSearchRequest.name,
                        restaurantSearchRequest.hours.open,
                        restaurantSearchRequest.hours.close);

        var restaurants = restaurantService.findRestaurants(restaurantSearchOptions);
        var response = RestaurantResponseMapper.toResponse(restaurants);
        return Response.ok(response).build();
    }

    @GET
    @Path("/restaurants/{restaurantId}/availabilities")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRestaurantAvailabilities(
            @PathParam("restaurantId") String stringRestaurantId, @QueryParam("date") String date) {
        if (date == null) {
            throw new MissingParameterException("Un des paramètres obligatoires est manquant");
        }
        var restaurantId = Id.fromString(stringRestaurantId);
        LocalDate localDate;
        try {
            localDate = LocalDate.parse(date);
        } catch (Exception e) {
            throw new InvalidParameterException("Un des paramètres n'est pas valide");
        }
        var availabilities = restaurantService.getAvailabilities(restaurantId, localDate);
        var response = AvailabilityResponseMapper.toResponse(availabilities, localDate);
        return Response.ok(response).build();
    }

    @DELETE
    @Path("/restaurants/{restaurantId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteRestaurant(
            @PathParam("restaurantId") String StringRestaurantId,
            @HeaderParam("Owner") String StringOwnerId) {
        if (StringOwnerId == null || StringOwnerId.isBlank()) {
            throw new MissingParameterException("L'identifiant du propriétaire est vide");
        }
        if (StringRestaurantId == null || StringRestaurantId.isEmpty()) {
            throw new MissingParameterException("L'identifiant du restaurant est vide");
        }

        var restaurantId = Id.fromString(StringRestaurantId);
        var ownerId = Id.fromString(StringOwnerId);

        restaurantService.deleteRestaurantById(restaurantId, ownerId);
        return Response.noContent().build();
    }

    @GET
    @Path("restaurants/{restaurantId}/menu")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMenu(@PathParam("restaurantId") String restaurantId) {
        var id = Id.fromString(restaurantId);
        var menu = restaurantService.findMenuForRestaurant(id);
        var response = MenuResponseMapper.toResponse(menu);
        return Response.ok(response).build();
    }

    @POST
    @Path("restaurants/{restaurantId}/menu")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addMenu(
            @Context UriInfo info,
            @HeaderParam("Owner") String stringOwnerId,
            @PathParam("restaurantId") String stringRestaurantId,
            AddMenuRequest menuRequest) {
        var restaurantId = Id.fromString(stringRestaurantId);
        var ownerId = Id.fromString(stringOwnerId);
        var menu = MenuMapper.mapMenu(restaurantId, menuRequest);

        restaurantService.addMenu(menu, ownerId);
        return Response.created(getUriResponse(restaurantId, info)).build();
    }

    @DELETE
    @Path("restaurants/{restaurantId}/menu")
    public Response deleteMenu(
            @HeaderParam("Owner") String stringOwnerId,
            @PathParam("restaurantId") String stringRestaurantId) {
        var restaurantId = Id.fromString(stringRestaurantId);
        var ownerId = Id.fromString(stringOwnerId);
        restaurantService.deleteMenuFromRestaurant(restaurantId, ownerId);
        return Response.noContent().build();
    }

    private Response buildOkResponse(Id restaurantId, UriInfo context) {
        URI location = getUriResponse(restaurantId, context);
        return Response.ok().status(201).location(location).build();
    }

    private URI getUriResponse(Id restauranId, UriInfo context) {
        String formattedLocation =
                String.format("%s/%s", context.getAbsolutePath(), restauranId.value());
        return URI.create(formattedLocation);
    }
}
