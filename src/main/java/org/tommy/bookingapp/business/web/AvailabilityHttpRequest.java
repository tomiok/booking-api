package org.tommy.bookingapp.business.web;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AvailabilityHttpRequest {

  private LocalDate from;

  private LocalDate to;
}
