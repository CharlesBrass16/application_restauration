/* (C)2024 */
package ca.ulaval.glo2003.interfaces.rest.configurations;

import ca.ulaval.glo2003.domain.reservation.ReservationRepository;
import ca.ulaval.glo2003.domain.restaurant.RestaurantRepository;
import ca.ulaval.glo2003.domain.restaurant.menu.MenuRepository;
import ca.ulaval.glo2003.infrastructure.persistence.mongoDB.MongoDatastore;
import ca.ulaval.glo2003.infrastructure.persistence.mongoDB.repositories.MongoDBMenuRepository;
import ca.ulaval.glo2003.infrastructure.persistence.mongoDB.repositories.MongoDBReservationRepository;
import ca.ulaval.glo2003.infrastructure.persistence.mongoDB.repositories.MongoDBRestaurantRepository;
import jakarta.inject.Singleton;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class ConfigurationMongoDB extends AbstractBinder {

    @Override
    protected void configure() {
        bind(MongoDatastore.class).to(MongoDatastore.class).in(Singleton.class);
        bind(MongoDBReservationRepository.class)
                .to(ReservationRepository.class)
                .in(Singleton.class);
        bind(MongoDBRestaurantRepository.class).to(RestaurantRepository.class).in(Singleton.class);
        bind(MongoDBMenuRepository.class).to(MenuRepository.class).in(Singleton.class);
    }
}
