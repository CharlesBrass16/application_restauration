/* (C)2024 */
package ca.ulaval.glo2003.infrastructure.persistence.mongoDB;

import ca.ulaval.glo2003.infrastructure.environment.EnvironmentManager;
import com.mongodb.client.MongoClients;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import jakarta.inject.Inject;

public class MongoDatastore {

    private final Datastore datastore;

    @Inject
    public MongoDatastore(EnvironmentManager env) {
        var dbName = env.getValueFromKey("MONGO_DATABASE");
        var mongoUrl = env.getValueFromKey("MONGO_CLUSTER_URL");
        System.out.println("Connecting to MongoDB at " + mongoUrl + " with database " + dbName);
        datastore = Morphia.createDatastore(MongoClients.create(mongoUrl), dbName);

        datastore.getMapper().mapPackage("ca.ulaval.glo2003");
    }

    public Datastore getDatastore() {
        return datastore;
    }
}
