/* (C)2024 */
package ca.ulaval.glo2003.domain.restaurant.menu;

import static org.junit.jupiter.api.Assertions.*;

import ca.ulaval.glo2003.domain.exceptions.InvalidParameterException;
import ca.ulaval.glo2003.domain.shared.Id;
import java.util.List;
import org.junit.jupiter.api.Test;

class MenuValidatorTest {
    private static final Id VALID_RESTAURANT_ID = new Id("1234");

    @Test
    public void givenValidMenu_whenValidatingMenu_thenShouldNotThrowException() {
        String VALID_DISH_NAME = "dishName";
        Dish VALID_DISH = new Dish(VALID_DISH_NAME);
        Menu menu = new Menu(VALID_RESTAURANT_ID, List.of(VALID_DISH));

        MenuValidator menuValidator = new MenuValidator();

        assertDoesNotThrow(() -> menuValidator.validateMenu(menu));
    }

    @Test
    public void givenMenuWithNoDishes_whenValidatingMenu_thenShouldThrowException() {
        Menu menu = new Menu(VALID_RESTAURANT_ID, List.of());

        MenuValidator menuValidator = new MenuValidator();

        assertThrows(InvalidParameterException.class, () -> menuValidator.validateMenu(menu));
    }

    @Test
    public void givenMenuWithDishWithEmptyName_whenValidatingMenu_thenShouldThrowException() {
        Dish dish = new Dish("");
        Menu menu = new Menu(VALID_RESTAURANT_ID, List.of(dish));

        MenuValidator menuValidator = new MenuValidator();

        assertThrows(InvalidParameterException.class, () -> menuValidator.validateMenu(menu));
    }
}
