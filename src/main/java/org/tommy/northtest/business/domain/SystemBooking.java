package org.tommy.northtest.business.domain;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;
import lombok.Getter;
import lombok.Setter;

@Entity
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

}
