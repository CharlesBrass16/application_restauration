/* (C)2024 */
package ca.ulaval.glo2003.interfaces.rest.mappers.exceptions;

import ca.ulaval.glo2003.domain.exceptions.NotRestaurantOwnerException;
import ca.ulaval.glo2003.interfaces.rest.dto.responses.ErrorResponse;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class NotRestaurantOwnerExceptionMapper
        implements ExceptionMapper<NotRestaurantOwnerException> {
    @Override
    public Response toResponse(NotRestaurantOwnerException exception) {
        ErrorResponse errorResponse = new ErrorResponse(exception.error, exception.getMessage());
        return Response.status(404).entity(errorResponse).build();
    }
}
