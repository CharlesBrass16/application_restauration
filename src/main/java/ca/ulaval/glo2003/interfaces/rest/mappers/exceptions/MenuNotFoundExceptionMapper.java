/* (C)2024 */
package ca.ulaval.glo2003.interfaces.rest.mappers.exceptions;

import ca.ulaval.glo2003.domain.exceptions.MenuNotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class MenuNotFoundExceptionMapper implements ExceptionMapper<MenuNotFoundException> {

    @Override
    public Response toResponse(MenuNotFoundException exception) {
        return Response.status(404).entity(exception.getMessage()).build();
    }
}
