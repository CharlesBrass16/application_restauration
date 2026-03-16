/* (C)2024 */
package ca.ulaval.glo2003.domain.restaurant;

import static org.junit.jupiter.api.Assertions.*;

import ca.ulaval.glo2003.domain.exceptions.InvalidParameterException;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RestaurantValidatorTest {

    public static final String VALID_NAME = "La Botega";
    public static final int VALID_CAPACITY = 100;
    public static final LocalTime VALID_OPENING_TIME = LocalTime.of(10, 0);
    public static final LocalTime VALID_CLOSING_TIME = LocalTime.of(14, 0);
    private RestaurantValidator restaurantValidator;

    @BeforeEach
    void setupRestaurantValidator() {
        restaurantValidator = new RestaurantValidator();
    }

    @Test
    void givenValidRestaurant_whenValidateRestaurant_thenNoExceptionIsThrown() {
        assertDoesNotThrow(
                () ->
                        restaurantValidator.validate(
                                VALID_NAME,
                                VALID_CAPACITY,
                                VALID_OPENING_TIME,
                                VALID_CLOSING_TIME));
    }

    @Test
    void givenEmptyName_whenValidateRestaurant_thenThrowsException() {
        String emptyName = "";

        assertThrows(
                InvalidParameterException.class,
                () ->
                        restaurantValidator.validate(
                                emptyName, VALID_CAPACITY, VALID_OPENING_TIME, VALID_CLOSING_TIME));
    }

    @Test
    void givenNullName_whenValidateRestaurant_thenThrowsException() {
        String nullName = null;

        assertThrows(
                InvalidParameterException.class,
                () ->
                        restaurantValidator.validate(
                                nullName, VALID_CAPACITY, VALID_OPENING_TIME, VALID_CLOSING_TIME));
    }

    @Test
    void givenNegativeCapacity_whenValidateRestaurant_thenThrowsException() {
        int negativeCapacity = -1;

        assertThrows(
                InvalidParameterException.class,
                () ->
                        restaurantValidator.validate(
                                VALID_NAME,
                                negativeCapacity,
                                VALID_OPENING_TIME,
                                VALID_CLOSING_TIME));
    }

    @Test
    void givenOpeningTimeAfterClosingTime_whenValidateRestaurant_thenThrowsException() {
        LocalTime invalidOpeningTime = LocalTime.of(14, 0);
        LocalTime invalidClosingTime = LocalTime.of(10, 0);

        assertThrows(
                InvalidParameterException.class,
                () ->
                        restaurantValidator.validate(
                                VALID_NAME,
                                VALID_CAPACITY,
                                invalidOpeningTime,
                                invalidClosingTime));
    }
}
