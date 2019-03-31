package org.tommy.bookingapp.business.component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBookingRequest {

  private String updatedBookingFrom;

  private int days;
}
