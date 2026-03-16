/* (C)2024 */
package ca.ulaval.glo2003.infrastructure.persistence.memory;

import ca.ulaval.glo2003.domain.reservation.ReservationRepository;
import ca.ulaval.glo2003.domain.reservation.ReservationRepositoryIntegrationTest;

class InMemoryReservationRepositoryIntegrationTest extends ReservationRepositoryIntegrationTest {

    @Override
    protected ReservationRepository createRepository() {
        return new InMemoryReservationRepository();
    }
}
