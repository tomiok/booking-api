package org.tommy.northtest.business.usecase.availability;

import java.time.LocalDate;
import java.util.Set;

public interface AvailabilityService {

  Set<LocalDate> getAvailableDays(final Set<LocalDate> systemBookings, final LocalDate from,
                                  final LocalDate to);
}
