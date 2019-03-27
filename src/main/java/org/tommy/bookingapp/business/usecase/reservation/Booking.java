package org.tommy.bookingapp.business.usecase.reservation;

import java.time.LocalDate;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Booking {

  private final String identifier;

  /**
   * Inclusive date
   */
  private final LocalDate from;

  /**
   * Exclusive date
   */
  private final LocalDate to;

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Booking book = (Booking) o;
    return Objects.equals(identifier, book.identifier);
  }

  @Override
  public int hashCode() {
    return Objects.hash(identifier);
  }
}
