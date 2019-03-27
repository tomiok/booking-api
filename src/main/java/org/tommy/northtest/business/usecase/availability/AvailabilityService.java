package org.tommy.northtest.business.usecase.availability;

import java.time.LocalDate;
import java.util.Set;
import org.tommy.northtest.business.domain.SystemBooking;

public interface AvailabilityService {

  Set<LocalDate> getAvailableDays(final Set<SystemBooking> systemBookings, final LocalDate from,
                                  final LocalDate to);
}
