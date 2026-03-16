/* (C)2024 */
package ca.ulaval.glo2003.domain.shared;

import java.util.UUID;

public record Id(String value) {
    public static Id generate() {
        return new Id(UUID.randomUUID().toString());
    }

    public static Id fromString(String stringId) {
        return new Id(stringId);
    }
}
