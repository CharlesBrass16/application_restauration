/* (C)2024 */
package ca.ulaval.glo2003.domain.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class IdFactoryTest {

    @Test
    public void create_shouldReturnId() {
        IdFactory idFactory = new IdFactory();

        Id id = idFactory.createId();

        assertNotNull(id);
    }
}
