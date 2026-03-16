/* (C)2024 */
package ca.ulaval.glo2003.interfaces.rest.dto.responses;

public class ErrorResponse {
    public final String error;
    public final String description;

    public ErrorResponse(String error, String description) {
        this.error = error;
        this.description = description;
    }
}
