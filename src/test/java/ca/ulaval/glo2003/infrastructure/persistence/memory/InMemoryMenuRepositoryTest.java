/* (C)2024 */
package ca.ulaval.glo2003.infrastructure.persistence.memory;

import ca.ulaval.glo2003.domain.restaurant.menu.MenuRepository;
import ca.ulaval.glo2003.domain.restaurant.menu.MenuRepositoryIntegrationTest;

class InMemoryMenuRepositoryTest extends MenuRepositoryIntegrationTest {

    @Override
    protected MenuRepository createRepository() {
        return new InMemoryMenuRepository();
    }
}
