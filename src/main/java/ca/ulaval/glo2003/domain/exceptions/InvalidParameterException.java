/* (C)2024 */
package ca.ulaval.glo2003.domain.exceptions;

public class InvalidParameterException extends RestaloException {
    public final String error = "INVALID_PARAMETER";

    public InvalidParameterException(String message) {
        super(message);
    }
}
