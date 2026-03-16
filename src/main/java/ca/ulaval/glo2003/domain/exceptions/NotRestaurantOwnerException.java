/* (C)2024 */
package ca.ulaval.glo2003.domain.exceptions;

public class NotRestaurantOwnerException extends RestaloException {

    public final String error = "NOT_OWNER";

    public NotRestaurantOwnerException(String message) {
        super(message);
    }
}
