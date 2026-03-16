/* (C)2024 */
package ca.ulaval.glo2003.infrastructure.persistence.memory;

import ca.ulaval.glo2003.domain.restaurant.RestaurantRepository;
import ca.ulaval.glo2003.domain.restaurant.RestaurantRepositoryIntegrationTest;

class InMemoryRestaurantRepositoryIntegrationTest extends RestaurantRepositoryIntegrationTest {

    @Override
    protected RestaurantRepository createRepository() {
        return new InMemoryRestaurantRepository();
    }
}
