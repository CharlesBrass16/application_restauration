/* (C)2024 */
package ca.ulaval.glo2003.interfaces.rest.mappers.exceptions;

import ca.ulaval.glo2003.domain.exceptions.InvalidParameterException;
import ca.ulaval.glo2003.interfaces.rest.dto.responses.ErrorResponse;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class InvalidParameterExceptionMapper implements ExceptionMapper<InvalidParameterException> {
    @Override
    public Response toResponse(InvalidParameterException exception) {
        ErrorResponse errorResponse = new ErrorResponse(exception.error, exception.getMessage());
        return Response.status(400).entity(errorResponse).build();
    }
}
