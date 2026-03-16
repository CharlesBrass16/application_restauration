/* (C)2024 */
package ca.ulaval.glo2003.interfaces.rest.resources;

import static org.junit.jupiter.api.Assertions.*;

import ca.ulaval.glo2003.interfaces.configuration.PersistenceConfig;
import ca.ulaval.glo2003.interfaces.configuration.RestaloResourceConfig;
import jakarta.ws.rs.core.Application;
import org.glassfish.jersey.test.JerseyTest;

public class ReservationResourceIntegrationTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new RestaloResourceConfig(PersistenceConfig.IN_MEMORY);
    }
}
