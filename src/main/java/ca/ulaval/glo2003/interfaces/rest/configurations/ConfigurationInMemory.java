/* (C)2024 */
package ca.ulaval.glo2003.interfaces.rest.configurations;

import ca.ulaval.glo2003.domain.reservation.ReservationRepository;
import ca.ulaval.glo2003.domain.restaurant.RestaurantRepository;
import ca.ulaval.glo2003.domain.restaurant.menu.MenuRepository;
import ca.ulaval.glo2003.infrastructure.persistence.memory.InMemoryMenuRepository;
import ca.ulaval.glo2003.infrastructure.persistence.memory.InMemoryReservationRepository;
import ca.ulaval.glo2003.infrastructure.persistence.memory.InMemoryRestaurantRepository;
import jakarta.inject.Singleton;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class ConfigurationInMemory extends AbstractBinder {

    @Override
    protected void configure() {
        bind(InMemoryRestaurantRepository.class).to(RestaurantRepository.class).in(Singleton.class);
        bind(InMemoryReservationRepository.class)
                .to(ReservationRepository.class)
                .in(Singleton.class);
        bind(InMemoryMenuRepository.class).to(MenuRepository.class).in(Singleton.class);
    }
}
