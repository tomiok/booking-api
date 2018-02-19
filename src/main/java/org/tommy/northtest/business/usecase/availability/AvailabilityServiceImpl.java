package org.tommy.northtest.business.usecase.availability;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

public class AvailabilityServiceImpl implements AvailabilityService {

  @Override
  public Set<LocalDate> getAvailableDays(final Set<LocalDate> systemBookings, final LocalDate from,
                                         final LocalDate to) {

    Set<LocalDate> userRequest = from.datesUntil(to).collect(Collectors.toSet());
    userRequest.removeAll(systemBookings);
    return userRequest;
  }
}
