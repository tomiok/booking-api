package org.tommy.northtest.business.usecase.reservation;

import java.util.Set;

public interface ReservationService {

  boolean isArrangeAvailable(final Set<Booking> systemBookings, final Booking userBooking);
}
