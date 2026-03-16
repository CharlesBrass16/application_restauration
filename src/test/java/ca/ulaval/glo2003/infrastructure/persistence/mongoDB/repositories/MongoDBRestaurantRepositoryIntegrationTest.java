/* (C)2024 */
package ca.ulaval.glo2003.infrastructure.persistence.mongoDB.repositories;

import static org.mockito.Mockito.when;

import ca.ulaval.glo2003.domain.restaurant.RestaurantRepository;
import ca.ulaval.glo2003.domain.restaurant.RestaurantRepositoryIntegrationTest;
import ca.ulaval.glo2003.infrastructure.environment.EnvironmentManager;
import ca.ulaval.glo2003.infrastructure.persistence.mongoDB.MongoDatastore;
import org.mockito.Mockito;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
class MongoDBRestaurantRepositoryIntegrationTest extends RestaurantRepositoryIntegrationTest {
    @Container final MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7");

    @Override
    protected RestaurantRepository createRepository() {

        System.out.println(mongoDBContainer.getConnectionString());
        EnvironmentManager mockEnvironmentManager = Mockito.mock(EnvironmentManager.class);
        when(mockEnvironmentManager.getValueFromKey("MONGO_DATABASE")).thenReturn("db");
        when(mockEnvironmentManager.getValueFromKey("MONGO_CLUSTER_URL"))
                .thenReturn(mongoDBContainer.getConnectionString());
        MongoDatastore mongoDatastore = new MongoDatastore(mockEnvironmentManager);
        return new MongoDBRestaurantRepository(mongoDatastore);
    }
}
