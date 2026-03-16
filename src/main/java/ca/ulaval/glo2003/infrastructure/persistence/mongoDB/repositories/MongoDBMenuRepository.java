/* (C)2024 */
package ca.ulaval.glo2003.infrastructure.persistence.mongoDB.repositories;

import ca.ulaval.glo2003.domain.exceptions.MenuNotFoundException;
import ca.ulaval.glo2003.domain.restaurant.menu.Menu;
import ca.ulaval.glo2003.domain.restaurant.menu.MenuRepository;
import ca.ulaval.glo2003.domain.shared.Id;
import ca.ulaval.glo2003.infrastructure.persistence.mongoDB.MongoDatastore;
import ca.ulaval.glo2003.infrastructure.persistence.mongoDB.model.MenuModel;
import dev.morphia.query.filters.Filters;

public class MongoDBMenuRepository implements MenuRepository {

    private final MongoDatastore datastore;

    public MongoDBMenuRepository(MongoDatastore datastore) {
        this.datastore = datastore;
    }

    @Override
    public Menu findMenuFromRestaurant(Id restaurantId) {
        var found =
                datastore
                        .getDatastore()
                        .find(MenuModel.class)
                        .filter(Filters.eq("restaurantId", restaurantId.value()))
                        .first();
        if (found == null) {
            throw new MenuNotFoundException(
                    String.format("Aucun menu trouvé pour le restaurant %s", restaurantId.value()));
        }
        return found.toMenu();
    }

    @Override
    public void removeMenuFromRestaurant(Id restaurantId) {
        datastore
                .getDatastore()
                .find(MenuModel.class)
                .filter(Filters.eq("restaurantId", restaurantId.value()))
                .delete();
    }

    @Override
    public void saveMenu(Menu menu) {
        var found =
                datastore
                        .getDatastore()
                        .find(MenuModel.class)
                        .filter(Filters.eq("restaurantId", menu.restaurantId().value()))
                        .first();
        if (found != null) {
            datastore.getDatastore().delete(found);
        }
        datastore.getDatastore().save(MenuModel.fromMenu(menu));
    }
}
