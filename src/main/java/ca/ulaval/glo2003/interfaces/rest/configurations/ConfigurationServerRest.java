/* (C)2024 */
package ca.ulaval.glo2003.interfaces.rest.configurations;

import ca.ulaval.glo2003.application.ReservationService;
import ca.ulaval.glo2003.application.RestaurantService;
import ca.ulaval.glo2003.domain.customer.CustomerAttributesValidator;
import ca.ulaval.glo2003.domain.customer.CustomerFactory;
import ca.ulaval.glo2003.domain.reservation.ReservationFactory;
import ca.ulaval.glo2003.domain.reservation.ReservationValidator;
import ca.ulaval.glo2003.domain.reservation.ReservationsFilter;
import ca.ulaval.glo2003.domain.restaurant.RestaurantFactory;
import ca.ulaval.glo2003.domain.restaurant.RestaurantFilter;
import ca.ulaval.glo2003.domain.restaurant.RestaurantValidator;
import ca.ulaval.glo2003.domain.restaurant.menu.MenuValidator;
import ca.ulaval.glo2003.domain.shared.IdFactory;
import ca.ulaval.glo2003.infrastructure.environment.EnvironmentManager;
import ca.ulaval.glo2003.infrastructure.environment.SystemEnvironmentManager;
import jakarta.inject.Singleton;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class ConfigurationServerRest extends AbstractBinder {

    @Override
    protected void configure() {
        bind(SystemEnvironmentManager.class).to(EnvironmentManager.class).in(Singleton.class);
        bind(RestaurantValidator.class).to(RestaurantValidator.class);
        bind(ReservationValidator.class).to(ReservationValidator.class);
        bind(CustomerAttributesValidator.class).to(CustomerAttributesValidator.class);
        bind(IdFactory.class).to(IdFactory.class);
        bind(RestaurantFactory.class).to(RestaurantFactory.class);
        bind(ReservationFactory.class).to(ReservationFactory.class);
        bind(CustomerFactory.class).to(CustomerFactory.class);
        bindAsContract(ReservationsFilter.class).in(Singleton.class);
        bind(RestaurantFilter.class).to(RestaurantFilter.class);
        bind(MenuValidator.class).to(MenuValidator.class);
        bindAsContract(RestaurantService.class);
        bindAsContract(ReservationService.class);
    }
}
