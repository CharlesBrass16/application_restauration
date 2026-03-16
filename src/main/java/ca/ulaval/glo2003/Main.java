/* (C)2024 */
package ca.ulaval.glo2003;

import ca.ulaval.glo2003.interfaces.configuration.PersistenceConfig;
import ca.ulaval.glo2003.interfaces.configuration.RestaloResourceConfig;
import io.sentry.Sentry;
import java.net.URI;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public class Main {
    public static final String BASE_URI = "http://0.0.0.0:8080/";

    public static HttpServer startServer() {
        Sentry.init();
        try {
            throw new Exception("This is a test.");
        } catch (Exception e) {
            Sentry.captureException(e);
        }
        var persistenceMode = getPersistenceMode();
        final ResourceConfig rc = new RestaloResourceConfig(persistenceMode);
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    private static PersistenceConfig getPersistenceMode() {
        var persistenceEnvStringVar = System.getProperty("persistence");
        System.out.println("persistenceEnvStringVar = " + persistenceEnvStringVar);
        return PersistenceConfig.fromString(persistenceEnvStringVar);
    }

    public static void main(String[] args) {
        startServer();
        System.out.printf("Jersey app started with endpoints available at %s%n", BASE_URI);
    }
}
