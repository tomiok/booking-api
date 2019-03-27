package org.tommy.bookingapp.business.usecase.reservation;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.junit.Before;
import org.junit.Test;

public class ReservationServiceImplTest {

  private ReservationService reservationService;

  private static Set<Booking> setUpBooks() {
    LocalDate start_1 = LocalDate.of(2018, 1, 1);
    LocalDate end_1 = LocalDate.of(2018, 1, 4);
    Booking book1 = new Booking(createIdentifier(), start_1, end_1);

    LocalDate start_2 = LocalDate.of(2018, 1, 6);
    LocalDate end_2 = LocalDate.of(2018, 1, 9);
    Booking book2 = new Booking(createIdentifier(), start_2, end_2);

    Set<Booking> books = new HashSet<>();

    books.add(book1);
    books.add(book2);

    return books;
  }

  private static Booking createUserBook(final int year, final int month, final int day, int days) {
    LocalDate from = LocalDate.of(year, month, day);
    LocalDate to = from.plusDays(days);

    return new Booking(createIdentifier(), from, to);
  }

  private static String createIdentifier() {
    return UUID.randomUUID().toString();
  }

  @Before
  public void setUp() {
    reservationService = new ReservationServiceImpl();
  }

  @Test
  public void isArrangeAvailable() {
    Set<Booking> books = setUpBooks();
    boolean available = reservationService.isArrangeAvailable(books, createUserBook(2018, 1, 5, 2));

    assertThat(available).isFalse();
  }

  @Test
  public void shouldReturnTrue_whenCampsiteIsAvailable() {
    Set<Booking> books = setUpBooks();
    boolean available = reservationService.isArrangeAvailable(books, createUserBook(2018, 1, 5, 0));

    assertThat(available).isTrue();
  }
}
