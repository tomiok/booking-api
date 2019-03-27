package org.tommy.bookingapp.business.usecase.availability;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import org.tommy.bookingapp.business.domain.SystemBooking;

public interface AvailabilityService {

  List<LocalDate> getAvailableDays(final Set<SystemBooking> systemBookings, final LocalDate from,
                                   final LocalDate to);
}
