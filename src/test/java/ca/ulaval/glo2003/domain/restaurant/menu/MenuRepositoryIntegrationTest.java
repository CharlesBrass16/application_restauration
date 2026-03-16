/* (C)2024 */
package ca.ulaval.glo2003.domain.restaurant.menu;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.ulaval.glo2003.domain.exceptions.MenuNotFoundException;
import ca.ulaval.glo2003.domain.shared.Id;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public abstract class MenuRepositoryIntegrationTest {

    private final Id RESTAURANT_ID = new Id("456879654321");
    private final List<Dish> dishes = List.of(new Dish("poutine"));
    private final Menu MENU = new Menu(RESTAURANT_ID, dishes);
    private MenuRepository menuRepository;

    protected abstract MenuRepository createRepository();

    @BeforeEach
    public void setUpMenuRepository() {
        menuRepository = createRepository();
    }

    @Test
    public void givenNoMenu_whenFindMenu_thenErrorIsThrown() {
        MenuRepository menuRepository = createRepository();

        assertThrows(
                MenuNotFoundException.class,
                () -> menuRepository.findMenuFromRestaurant(RESTAURANT_ID));
    }

    @Test
    public void givenMenu_whenFindMenu_thenMenuIsReturned() {
        menuRepository.saveMenu(MENU);

        Menu menu = menuRepository.findMenuFromRestaurant(RESTAURANT_ID);

        assertEquals(MENU, menu);
    }

    @Test
    public void givenMenu_whenRemoveMenu_thenMenuIsRemoved() {
        menuRepository.saveMenu(MENU);

        menuRepository.removeMenuFromRestaurant(RESTAURANT_ID);

        assertThrows(
                MenuNotFoundException.class,
                () -> menuRepository.findMenuFromRestaurant(RESTAURANT_ID));
    }

    @Test
    public void givenAlreadyOneMenu_whenSaveMenu_thenMenuIsUpdated() {
        menuRepository.saveMenu(MENU);
        List<Dish> newDishes = List.of(new Dish("pizza"));
        Menu newMenu = new Menu(RESTAURANT_ID, newDishes);

        menuRepository.saveMenu(newMenu);

        Menu menu = menuRepository.findMenuFromRestaurant(RESTAURANT_ID);
        assertEquals(newMenu, menu);
    }
}
