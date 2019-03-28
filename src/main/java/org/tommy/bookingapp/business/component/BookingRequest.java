package org.tommy.bookingapp.business.component;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequest {

  private String firstName;

  private String lastName;

  private String email;

  private String bookingFrom;

  private int days;
}

