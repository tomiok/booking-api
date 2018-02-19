package org.tommy.northtest.business.usecase.reservation;

import java.time.LocalDate;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Book {

  private final String identifier;

  private final LocalDate from;

  private final LocalDate to;

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Book book = (Book) o;
    return Objects.equals(identifier, book.identifier);
  }

  @Override
  public int hashCode() {

    return Objects.hash(identifier);
  }
}
