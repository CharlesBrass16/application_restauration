/* (C)2024 */
package ca.ulaval.glo2003.domain.restaurant.menu;

import ca.ulaval.glo2003.domain.exceptions.InvalidParameterException;

public class MenuValidator {
    public void validateMenu(Menu menu) {
        if (menu.dishes().isEmpty()) {
            throw new InvalidParameterException("Le menu doit contenir au moins un item.");
        }
        if (menu.dishes().stream().anyMatch(dish -> dish.name().isEmpty())) {
            throw new InvalidParameterException("Le nom de l'item ne peut pas être vide.");
        }
    }
}
