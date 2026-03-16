/* (C)2024 */
package ca.ulaval.glo2003.domain.reservation;

import java.time.LocalDate;

public record ReservationSearchOptions(String customerName, LocalDate date) {}
