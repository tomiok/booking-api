package org.tommy.bookingapp.business.component;

import java.time.LocalDate;
import java.util.List;
import org.tommy.bookingapp.business.domain.SystemBooking;

public interface BookingComponent {

  BookingResponse doCampsiteBooking(final BookingRequest bookingRequest);

  int deleteBooking(final String identifier);

  BookingResponse updateBooking(final String identifier, final UpdateBookingRequest request);

  BookingSummary findBookingByIdentifier(final String identifier);

  List<LocalDate> findAvailableDays(final LocalDate from, final LocalDate to);
}
