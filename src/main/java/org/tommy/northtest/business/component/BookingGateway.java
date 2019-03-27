package org.tommy.northtest.business.component;

import java.time.LocalDate;
import java.util.Set;
import org.springframework.data.jpa.repository.Query;
import org.tommy.northtest.business.domain.SystemBooking;

public interface BookingGateway {

  String saveBooking(final BookingRequest request);

  @Query("select b from Booking b where b.bookingFrom > :from ")
  Set<SystemBooking> findAllBookings(LocalDate from);

  int deleteBooking(final String identifier);

  SystemBooking findOne(final String identifier);

  int updateBooking(final String identifier, final LocalDate from, final LocalDate to);
}
