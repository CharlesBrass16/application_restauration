/* (C)2024 */
package ca.ulaval.glo2003.interfaces.configuration;

import ca.ulaval.glo2003.interfaces.rest.configurations.ConfigurationInMemory;
import ca.ulaval.glo2003.interfaces.rest.configurations.ConfigurationMongoDB;
import ca.ulaval.glo2003.interfaces.rest.configurations.ConfigurationServerRest;
import org.glassfish.jersey.server.ResourceConfig;

public class RestaloResourceConfig extends ResourceConfig {

    public RestaloResourceConfig(PersistenceConfig persistenceConfig) {
        packages("ca.ulaval.glo2003");

        if (persistenceConfig == PersistenceConfig.MONGO_DB) {
            register(ConfigurationMongoDB.class);
        } else {
            register(ConfigurationInMemory.class);
        }

        register(ConfigurationServerRest.class);
    }
}
