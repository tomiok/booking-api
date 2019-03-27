package org.tommy.bookingapp.business.component;

import java.time.LocalDate;
import java.util.Set;
import org.tommy.bookingapp.business.domain.SystemBooking;

public interface BookingGateway {

  String saveBooking(final BookingRequest request);

  Set<SystemBooking> findAllBookings(LocalDate from);

  int deleteBooking(final String identifier);

  SystemBooking findOne(final String identifier);

  int updateBooking(final String identifier, final LocalDate from, final LocalDate to);
}
