package org.tommy.bookingapp.business.usecase.reservation;

import java.util.Set;

public interface ReservationService {

  /**
   * @param systemBookings All the bookings saved in the system.
   * @param userBooking    The dates that the user want to book.
   *
   * @return {@code TRUE} if the date is available, otherwise {@code FALSE}
   */
  boolean checkAvailability(final Set<Booking> systemBookings, final Booking userBooking);
}
