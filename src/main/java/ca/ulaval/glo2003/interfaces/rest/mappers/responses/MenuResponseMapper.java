/* (C)2024 */
package ca.ulaval.glo2003.interfaces.rest.mappers.responses;

import ca.ulaval.glo2003.domain.restaurant.menu.Dish;
import ca.ulaval.glo2003.domain.restaurant.menu.Menu;
import ca.ulaval.glo2003.interfaces.rest.dto.responses.MenuResponse;

public class MenuResponseMapper {

    public static MenuResponse toResponse(Menu menu) {
        MenuResponse response = new MenuResponse();
        response.dishes = menu.dishes().stream().map(Dish::name).toList();
        return response;
    }
}
