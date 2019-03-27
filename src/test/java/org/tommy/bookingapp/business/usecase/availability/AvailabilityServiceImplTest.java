package org.tommy.bookingapp.business.usecase.availability;

import static java.time.LocalDate.of;

import com.google.common.collect.Sets;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import org.tommy.bookingapp.business.domain.SystemBooking;

public class AvailabilityServiceImplTest {

  private AvailabilityService availabilityService;

  @Before
  public void setUp() {
    availabilityService = new AvailabilityServiceImpl();
  }

  @Test
  public void getAvailableDays() {

    Set<SystemBooking> booked =
        Sets.newHashSet(
            new SystemBooking(
                "xxaa1",
                of(2018, 1, 1),
                of(2018, 1, 3)),
            new SystemBooking(
                "xxaa2",
                of(2018,1,10),
                of(2018,1,13)),
            new SystemBooking(
                "xxaa23",
                of(2018,1,20),
                of(2018,1,21))
        );
    List<LocalDate> days = availabilityService.getAvailableDays(booked, of(2018, 1, 1), of(2018, 1, 31));

    System.out.println(days);
    System.out.println(days.size());
  }
}