/* (C)2024 */
package ca.ulaval.glo2003.domain.exceptions;

public class MissingParameterException extends RestaloException {
    public final String error = "MISSING_PARAMETER";

    public MissingParameterException(String message) {
        super(message);
    }
}
