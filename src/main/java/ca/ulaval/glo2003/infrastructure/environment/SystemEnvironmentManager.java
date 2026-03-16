/* (C)2024 */
package ca.ulaval.glo2003.infrastructure.environment;

public class SystemEnvironmentManager implements EnvironmentManager {

    public String getValueFromKey(String key) {
        return System.getenv(key);
    }
}
