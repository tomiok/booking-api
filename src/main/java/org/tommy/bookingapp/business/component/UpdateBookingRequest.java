package org.tommy.bookingapp.business.component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateBookingRequest {

  private String updatedBookingFrom;

  private int days;
}
