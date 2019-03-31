package org.tommy.bookingapp.business.component;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.tommy.bookingapp.business.domain.SystemBooking;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingSummary {

  private String bookingIdentifier;

  /**
   * Inclusive date.
   */
  private LocalDate bookingFrom;

  /**
   * Exclusive date
   */
  private LocalDate bookingTo;

  public BookingSummary(SystemBooking sb) {
    this.bookingIdentifier = sb.getBookingIdentifier();
    this.bookingFrom = sb.getBookingFrom();
    this.bookingTo = sb.getBookingTo();
  }
}
