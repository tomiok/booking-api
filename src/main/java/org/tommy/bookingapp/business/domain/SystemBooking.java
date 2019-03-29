package org.tommy.bookingapp.business.domain;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class SystemBooking {

  @Id
  @GeneratedValue
  private long id;

  private String bookingIdentifier;

  /**
   * Inclusive date.
   */
  private LocalDate bookingFrom;

  /**
   * Exclusive date
   */
  private LocalDate bookingTo;

  @Version
  private long version;

  public SystemBooking(final String bookingIdentifier, final LocalDate bookingFrom, final LocalDate bookingTo) {
    this.bookingIdentifier = bookingIdentifier;
    this.bookingFrom = bookingFrom;
    this.bookingTo = bookingTo;
  }

  public LocalDate getBookingTo() {
    return this.bookingTo.minusDays(1);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("id", id)
        .append("bookingIdentifier", bookingIdentifier)
        .append("bookingFrom", bookingFrom)
        .append("bookingTo", bookingTo)
        .append("version", version)
        .toString();
  }
}
