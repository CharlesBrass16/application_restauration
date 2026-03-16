/* (C)2024 */
package ca.ulaval.glo2003.interfaces.rest.mappers.exceptions;

import ca.ulaval.glo2003.domain.exceptions.ReservationNotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ReservationNotFoundExceptionMapper
        implements ExceptionMapper<ReservationNotFoundException> {
    @Override
    public Response toResponse(ReservationNotFoundException exception) {
        return Response.status(404).entity(exception.getMessage()).build();
    }
}
