package org.tommy.bookingapp.business.component;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import org.tommy.bookingapp.business.domain.SystemBooking;

public interface BookingComponent {

  BookingResponse doCampsiteBooking(final BookingRequest bookingRequest);

  int deleteBooking(final String identifier);

  BookingResponse updateBooking(final String identifier, final UpdateBookingRequest request);

  SystemBooking viewBooking(final String identifier);

  List<LocalDate> availableDays(final LocalDate from, final LocalDate to);
}
