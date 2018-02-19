package org.tommy.northtest.business.component;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateBookingRequest {

  private LocalDate updatedBookingFrom;

  private int days;
}
