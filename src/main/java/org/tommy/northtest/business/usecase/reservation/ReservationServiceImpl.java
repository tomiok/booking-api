package org.tommy.northtest.business.usecase.reservation;

import java.time.LocalDate;
import java.util.Set;

public class ReservationServiceImpl implements ReservationService {

  private static boolean isOverlapping(final Book systemBook, final Book userBook) {
    LocalDate startSystem = systemBook.getFrom();
    LocalDate endSystem = systemBook.getTo();

    LocalDate startUser = userBook.getFrom();
    LocalDate endUser = userBook.getTo();

    return ((startSystem.isBefore(endUser)) && (startUser.isBefore(endSystem)));
  }

  @Override
  public boolean isArrangeAvailable(final Set<Book> systemBookings, final Book userBooking) {

    boolean overlaps = systemBookings
        .stream()
        .anyMatch(book -> isOverlapping(book, userBooking));

    return !overlaps;
  }
}
