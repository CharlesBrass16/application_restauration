/* (C)2024 */
package ca.ulaval.glo2003.interfaces.configuration;

public enum PersistenceConfig {
    MONGO_DB("mongo"),
    IN_MEMORY("inmemory");

    private String text;

    PersistenceConfig(String value) {
        this.text = value;
    }

    public static PersistenceConfig fromString(String text) {
        for (PersistenceConfig b : PersistenceConfig.values()) {
            if (b.text.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}
