/* (C)2024 */
package ca.ulaval.glo2003.domain.shared;

import java.time.LocalTime;

public record Availability(LocalTime startTime, int remainingPlaces) {}
