/* (C)2024 */
package ca.ulaval.glo2003.domain.restaurant.menu;

import ca.ulaval.glo2003.domain.shared.Id;
import java.util.List;

public record Menu(Id restaurantId, List<Dish> dishes) {}
