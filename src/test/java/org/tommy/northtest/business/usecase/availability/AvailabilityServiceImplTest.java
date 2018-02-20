package org.tommy.northtest.business.usecase.availability;

import static java.time.LocalDate.of;
import static java.util.stream.Collectors.toSet;

import java.time.LocalDate;
import java.util.Set;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

public class AvailabilityServiceImplTest {

  private AvailabilityService availabilityService;

  private static Set<LocalDate> createRange(final int dayFrom, final int dayTo, final int month) {
    LocalDate from = of(2018, month, dayFrom);
    LocalDate to = of(2018, month, dayTo);

    return from.datesUntil(to).collect(toSet());
  }

  @Before
  public void setUp() {
    availabilityService = new AvailabilityServiceImpl();
  }

  @Test
  public void getAvailableDays() {

    Set<LocalDate> booked = createRange(1, 13, 1);
    Set<LocalDate> days = availabilityService.getAvailableDays(booked, of(2018, 1, 1), of(2018, 1, 31));

    Assertions.assertThat(days.size()).isEqualTo(18);
  }
}