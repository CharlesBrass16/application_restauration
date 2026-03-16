/* (C)2024 */
package ca.ulaval.glo2003.interfaces.rest.mappers.requests;

import ca.ulaval.glo2003.domain.restaurant.menu.Dish;
import ca.ulaval.glo2003.domain.restaurant.menu.Menu;
import ca.ulaval.glo2003.domain.shared.Id;
import ca.ulaval.glo2003.interfaces.rest.dto.requests.AddMenuRequest;
import java.util.stream.Collectors;

public class MenuMapper {
    public static Menu mapMenu(Id restaurantId, AddMenuRequest addMenuRequest) {
        return new Menu(
                restaurantId,
                addMenuRequest.dishes.stream().map(Dish::new).collect(Collectors.toList()));
    }
}
