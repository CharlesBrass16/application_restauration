/* (C)2024 */
package ca.ulaval.glo2003.domain.exceptions;

public class RestaurantNotFoundException extends RestaloException {

    public final String error = "NOT_FOUND";

    public RestaurantNotFoundException(String message) {
        super(message);
    }
}
