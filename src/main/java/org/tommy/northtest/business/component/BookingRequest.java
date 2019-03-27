package org.tommy.northtest.business.component;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingRequest {

  private String firstName;

  private String lastName;

  private String email;

  private LocalDate bookingFrom;

  private int days;
}

