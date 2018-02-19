package org.tommy.northtest.business.component;

import java.time.LocalDate;
import java.util.Set;
import org.tommy.northtest.business.domain.Booking;

public interface BookingComponent {

  BookingResponse doCampsiteBooking(final BookingRequest bookingRequest);

  int deleteBooking(final String identifier);

  BookingResponse updateBooking(final String identifier, final UpdateBookingRequest request);

  Booking viewBooking(final String identifier);

  Set<LocalDate> availableDays(final LocalDate from, final LocalDate to);
}
