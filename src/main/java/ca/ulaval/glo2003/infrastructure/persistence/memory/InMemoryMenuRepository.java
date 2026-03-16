/* (C)2024 */
package ca.ulaval.glo2003.infrastructure.persistence.memory;

import ca.ulaval.glo2003.domain.exceptions.MenuNotFoundException;
import ca.ulaval.glo2003.domain.restaurant.menu.Menu;
import ca.ulaval.glo2003.domain.restaurant.menu.MenuRepository;
import ca.ulaval.glo2003.domain.shared.Id;
import java.util.LinkedList;
import java.util.List;

public class InMemoryMenuRepository implements MenuRepository {

    private final List<Menu> menus;

    public InMemoryMenuRepository() {
        this.menus = new LinkedList<>();
    }

    @Override
    public Menu findMenuFromRestaurant(Id restaurantId) {
        return menus.stream()
                .filter(menu -> menu.restaurantId().equals(restaurantId))
                .findFirst()
                .orElseThrow(
                        () ->
                                new MenuNotFoundException(
                                        String.format(
                                                "Aucun menu associé au restaurant %s",
                                                restaurantId.value())));
    }

    @Override
    public void removeMenuFromRestaurant(Id restaurantId) {
        menus.removeIf(menu -> menu.restaurantId().equals(restaurantId));
    }

    @Override
    public void saveMenu(Menu menu) {
        try {
            removeMenuFromRestaurant(menu.restaurantId());
        } finally {
            menus.add(menu);
        }
    }
}
