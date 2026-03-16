/* (C)2024 */
package ca.ulaval.glo2003.domain.restaurant.menu;

import ca.ulaval.glo2003.domain.shared.Id;

public interface MenuRepository {
    Menu findMenuFromRestaurant(Id restaurantId);

    void removeMenuFromRestaurant(Id restaurantId);

    void saveMenu(Menu menu);
}
