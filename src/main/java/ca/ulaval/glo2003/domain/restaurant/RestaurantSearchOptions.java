/* (C)2024 */
package ca.ulaval.glo2003.domain.restaurant;

import java.time.LocalTime;

public record RestaurantSearchOptions(String name, LocalTime from, LocalTime to) {}
