package org.tommy.northtest.business.component;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingRequest {

  private String firstName;

  private String lastName;

  private String email;

  //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-mm-dd")
  private LocalDate bookingFrom;

  private int days;
}

