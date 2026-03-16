/* (C)2024 */
package ca.ulaval.glo2003.infrastructure.persistence.mongoDB.model;

import ca.ulaval.glo2003.domain.restaurant.menu.Dish;
import ca.ulaval.glo2003.domain.restaurant.menu.Menu;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import java.util.List;

@Entity("Menu")
public final class MenuModel {

    @Id public String restaurantId;
    public List<String> dishes;

    public static MenuModel fromMenu(Menu menu) {
        String idValue = menu.restaurantId().value();
        var dishes = menu.dishes().stream().map(Dish::name).toList();
        MenuModel menuModel = new MenuModel();
        menuModel.restaurantId = idValue;
        menuModel.dishes = dishes;
        return menuModel;
    }

    public Menu toMenu() {
        var id = new ca.ulaval.glo2003.domain.shared.Id(restaurantId);
        List<Dish> dishList = dishes.stream().map(Dish::new).toList();
        return new Menu(id, dishList);
    }
}
