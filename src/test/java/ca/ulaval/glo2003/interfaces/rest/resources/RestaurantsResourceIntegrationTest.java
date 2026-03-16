/* (C)2024 */
package ca.ulaval.glo2003.interfaces.rest.resources;

import static org.junit.jupiter.api.Assertions.*;

import ca.ulaval.glo2003.interfaces.configuration.PersistenceConfig;
import ca.ulaval.glo2003.interfaces.configuration.RestaloResourceConfig;
import ca.ulaval.glo2003.interfaces.rest.dto.requests.ReservationRequest;
import ca.ulaval.glo2003.interfaces.rest.dto.requests.RestaurantRequest;
import ca.ulaval.glo2003.interfaces.rest.dto.responses.AvailabilityResponse;
import ca.ulaval.glo2003.interfaces.rest.dto.responses.RestaurantResponse;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.GenericType;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RestaurantsResourceIntegrationTest extends JerseyTest {
    private String ownerId = "ownerId";
    private RestaurantRequest restaurantRequest = new RestaurantRequest();
    private RestaurantResponse restaurantResponse;

    @BeforeEach
    void setupCustomerValidator() {
        restaurantRequest.name = "name";
        restaurantRequest.capacity = 1;
        restaurantRequest.hours = new RestaurantRequest.Hours();
        restaurantRequest.hours.open = LocalTime.of(10, 0);
        restaurantRequest.hours.close = LocalTime.of(12, 0);

        restaurantResponse =
                new RestaurantResponse(
                        "id",
                        "name",
                        1,
                        new RestaurantResponse.Hours("10:00:00", "12:00:00"),
                        new RestaurantResponse.ReservationsConfig(60));
    }

    @Override
    protected Application configure() {
        return new RestaloResourceConfig(PersistenceConfig.IN_MEMORY);
    }

    /**
     * Unfortunately since we are not mocking the application, we cannot verify the
     * returned ID
     */
    @Test
    public void givenValidRestaurantRequest_whenCreatingRestaurant_thenReturnsCreatedStatus() {
        var response =
                target("/restaurants")
                        .request()
                        .header("Owner", ownerId)
                        .post(Entity.json(restaurantRequest));

        assertEquals(201, response.getStatus());
    }

    @Test
    public void givenRequestWithInvalidCapacity_whenCreatingRestaurant_thenReturnsBadRequest() {
        restaurantRequest.capacity = 0;
        var response =
                target("/restaurants")
                        .request()
                        .header("Owner", ownerId)
                        .post(Entity.json(restaurantRequest));

        assertEquals(400, response.getStatus());
    }

    @Test
    public void givenRequestWithInvalidName_whenCreatingRestaurant_thenReturnsBadRequest() {
        restaurantRequest.name = "";
        var response =
                target("/restaurants")
                        .request()
                        .header("Owner", ownerId)
                        .post(Entity.json(restaurantRequest));

        assertEquals(400, response.getStatus());
    }

    @Test
    public void givenRequestWithInvalidOpeningTime_whenCreatingRestaurant_thenReturnsBadRequest() {
        restaurantRequest.hours.open = LocalTime.of(22, 0);
        var response =
                target("/restaurants")
                        .request()
                        .header("Owner", ownerId)
                        .post(Entity.json(restaurantRequest));

        assertEquals(400, response.getStatus());
    }

    @Test
    public void
            givenRestaurantsPresent_whenGettingRestaurants_thenReturnsOkStatusWithRestaurants() {
        target("/restaurants")
                .request()
                .header("Owner", ownerId)
                .post(Entity.json(restaurantRequest));

        var response = target("/restaurants").request().header("Owner", ownerId).get();

        assertEquals(200, response.getStatus());
        assertEquals("application/json", response.getHeaderString("Content-Type"));
        RestaurantResponse actual = response.readEntity(RestaurantResponse.class);
        assertEquals(restaurantResponse.name, actual.name);
        assertEquals(restaurantResponse.capacity, actual.capacity);
        assertEquals(restaurantResponse.hours.open, actual.hours.open);
        assertEquals(restaurantResponse.hours.close, actual.hours.close);
        assertEquals(
                restaurantResponse.reservationsConfig.duration, actual.reservationsConfig.duration);
    }

    @Test
    public void
            givenRestaurantsPresent_whenGettingRestaurants_thenReturnsCorrectAmountOfRestaurant() {
        target("/restaurants")
                .request()
                .header("Owner", ownerId)
                .post(Entity.json(restaurantRequest));

        target("/restaurants")
                .request()
                .header("Owner", ownerId)
                .post(Entity.json(restaurantRequest));

        var response = target("/restaurants").request().header("Owner", ownerId).get();

        assertEquals(200, response.getStatus());
        assertEquals("application/json", response.getHeaderString("Content-Type"));
        List<RestaurantResponse> actual = response.readEntity(new GenericType<>() {});
        assertEquals(2, actual.size());
    }

    @Test
    public void givenRestaurantsPresent_whenGettingRestaurant_thenReturnsOkStatusWithRestaurant() {
        var createResponse =
                target("/restaurants")
                        .request()
                        .header("Owner", ownerId)
                        .post(Entity.json(restaurantRequest));

        String id =
                createResponse
                        .getHeaderString("Location")
                        .substring(createResponse.getHeaderString("Location").lastIndexOf("/") + 1);

        var response = target("/restaurants/" + id).request().header("Owner", ownerId).get();

        assertEquals(200, response.getStatus());
        assertEquals("application/json", response.getHeaderString("Content-Type"));
        RestaurantResponse actual = response.readEntity(RestaurantResponse.class);
        assertEquals(restaurantResponse.name, actual.name);
        assertEquals(restaurantResponse.capacity, actual.capacity);
        assertEquals(restaurantResponse.hours.open, actual.hours.open);
        assertEquals(restaurantResponse.hours.close, actual.hours.close);
        assertEquals(
                restaurantResponse.reservationsConfig.duration, actual.reservationsConfig.duration);
    }

    @Test
    public void
            givenNoRestaurantsRestaurantsPresent_whenGettingRestaurant_thenReturnsNotFoundStatusWithRestaurant() {
        String id = "some inexisting value";
        ownerId = "Bob";
        var response = target("/restaurants/" + id).request().header("Owner", ownerId).get();

        assertEquals(404, response.getStatus());
    }

    @Test
    public void
            givenValidAvailabilitiesRequest_whenGettingAvailabilities_thenReturnsOkStatusWithAvailabilities() {
        var restaurantResponse =
                target("/restaurants")
                        .request()
                        .header("Owner", ownerId)
                        .post(Entity.json(restaurantRequest));

        String id =
                restaurantResponse
                        .getHeaderString("Location")
                        .substring(
                                restaurantResponse.getHeaderString("Location").lastIndexOf("/")
                                        + 1);

        ReservationRequest reservationRequest = new ReservationRequest();
        reservationRequest.customer = new ReservationRequest.Customer();
        reservationRequest.customer.email = "john.deer@gmail.com";
        reservationRequest.customer.name = "John Deer";
        reservationRequest.customer.phoneNumber = "1234567890";
        reservationRequest.date = LocalDate.of(2024, 4, 8);
        reservationRequest.groupSize = 1;
        reservationRequest.startTime = LocalTime.of(10, 30);

        target("/restaurants/" + id + "/reservations")
                .request()
                .post(Entity.json(reservationRequest));

        var response =
                target("/restaurants/" + id + "/availabilities")
                        .queryParam("date", "2024-04-08")
                        .request()
                        .get();

        assertEquals(200, response.getStatus());
        assertEquals("application/json", response.getHeaderString("Content-Type"));
        List<AvailabilityResponse> actual =
                response.readEntity(new GenericType<List<AvailabilityResponse>>() {});
        assertEquals(5, actual.size());
        assertEquals("2024-04-08T10:00:00", actual.get(0).start);
        assertEquals(1, actual.get(0).remainingPlaces);
        assertEquals("2024-04-08T10:15:00", actual.get(1).start);
        assertEquals(1, actual.get(1).remainingPlaces);
        assertEquals("2024-04-08T10:30:00", actual.get(2).start);
        assertEquals(0, actual.get(2).remainingPlaces);
        assertEquals("2024-04-08T10:45:00", actual.get(3).start);
        assertEquals(0, actual.get(3).remainingPlaces);
        assertEquals("2024-04-08T11:00:00", actual.get(4).start);
        assertEquals(0, actual.get(4).remainingPlaces);
    }

    @Test
    public void
            givenAvailabilitiesRequestWithoutDate_whenGettingAvailabilities_thenReturnsBadRequest() {
        var restaurantResponse =
                target("/restaurants")
                        .request()
                        .header("Owner", ownerId)
                        .post(Entity.json(restaurantRequest));

        String id =
                restaurantResponse
                        .getHeaderString("Location")
                        .substring(
                                restaurantResponse.getHeaderString("Location").lastIndexOf("/")
                                        + 1);

        var response = target("/restaurants/" + id + "/availabilities").request().get();

        assertEquals(400, response.getStatus());
    }

    @Test
    public void
            givenAvailabilitiesRequestWithInvalidDate_whenGettingAvailabilities_thenReturnsBadRequest() {
        var restaurantResponse =
                target("/restaurants")
                        .request()
                        .header("Owner", ownerId)
                        .post(Entity.json(restaurantRequest));

        String id =
                restaurantResponse
                        .getHeaderString("Location")
                        .substring(
                                restaurantResponse.getHeaderString("Location").lastIndexOf("/")
                                        + 1);

        var response =
                target("/restaurants/" + id + "/availabilities")
                        .queryParam("date", "asdf")
                        .request()
                        .get();

        assertEquals(400, response.getStatus());
    }

    @Test
    public void
            givenAvailabilitiesRequestWithInvalidRestaurantId_whenGettingAvailabilities_thenReturnsNotFound() {
        var response =
                target("/restaurants/asdf/availabilities")
                        .queryParam("date", "2024-04-08")
                        .request()
                        .get();

        assertEquals(404, response.getStatus());
    }

    @Test
    public void givenExistingRestaurant_whenDeletingRestaurant_thenReturnNoContentStatus() {
        var createResponse =
                target("/restaurants")
                        .request()
                        .header("Owner", ownerId)
                        .post(Entity.json(restaurantRequest));

        assertEquals(201, createResponse.getStatus());

        String locationHeader = createResponse.getHeaderString("Location");
        String[] locationParts = locationHeader.split("/");
        String restaurantId = locationParts[4];

        var deleteResponse =
                target("/restaurants/" + restaurantId).request().header("Owner", ownerId).delete();

        assertEquals(204, deleteResponse.getStatus());
    }

    @Test
    void givenNonExistingRestaurant_whenDeleting_thenReturnNotFoundStatus() {
        String id = "some inexisting value";
        ownerId = "Bob";
        var response = target("/restaurants/" + id).request().header("Owner", ownerId).delete();

        assertEquals(404, response.getStatus());
    }

    @Test
    void givenWrongOwnerId_whenDeleting_thenReturnNotFoundStatus() {
        var createResponse =
                target("/restaurants")
                        .request()
                        .header("Owner", ownerId)
                        .post(Entity.json(restaurantRequest));

        assertEquals(201, createResponse.getStatus());
        String locationHeader = createResponse.getHeaderString("Location");
        String[] locationParts = locationHeader.split("/");
        String restaurantId = locationParts[4];

        var deleteResponse =
                target("/restaurants/" + restaurantId)
                        .request()
                        .header("Owner", "NonValidOwnerId")
                        .delete();
        assertEquals(404, deleteResponse.getStatus());
    }
}
