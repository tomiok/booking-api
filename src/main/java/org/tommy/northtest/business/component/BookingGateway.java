package org.tommy.northtest.business.component;

import java.time.LocalDate;
import java.util.Set;
import org.tommy.northtest.business.domain.Booking;

public interface BookingGateway {

  String saveBooking(final BookingRequest request);

  Set<Booking> findAllBookings();

  int deleteBooking(final String identifier);

  Booking findOne(final String identifier);

  int updateBooking(final String identifier, final LocalDate from, final LocalDate to);
}
