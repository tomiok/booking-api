package org.tommy.northtest.business.usecase.reservation;

import java.util.Set;

public interface ReservationService {

  /**
   * @param systemBookings All the bookings saved in the system.
   * @param userBooking    The dates that the user want to book.
   *
   * @return {@code TRUE} if the date is available, otherwise {@code FALSE}
   */
  boolean isArrangeAvailable(final Set<Booking> systemBookings, final Booking userBooking);
}
