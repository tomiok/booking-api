package org.tommy.bookingapp.business.component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponse {

  private String bookingIdentifier;

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("bookingIdentifier", bookingIdentifier)
        .toString();
  }
}
