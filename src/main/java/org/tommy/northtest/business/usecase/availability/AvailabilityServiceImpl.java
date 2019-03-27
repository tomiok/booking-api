package org.tommy.northtest.business.usecase.availability;

import static java.util.stream.Collectors.toSet;

import java.time.LocalDate;
import java.time.Period;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.tommy.northtest.business.domain.SystemBooking;

public class AvailabilityServiceImpl implements AvailabilityService {

  @Override
  public Set<LocalDate> getAvailableDays(
      final Set<SystemBooking> systemBookings,
      final LocalDate from,
      final LocalDate to) {

    return systemBookings.stream()
        .map(b -> createSet(b.getBookingFrom(), b.getBookingTo()))
        .flatMap(Set::stream)
        .collect(toSet());
  }

  private Set<LocalDate> createSet(final LocalDate from, final LocalDate to) {
    int days = Period.between(from, to).getDays();
    return Stream.iterate(from, f -> from.plusDays(1))
        .limit(days)
        .collect(Collectors.toSet());
  }
}
