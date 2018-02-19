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
public class Booking {

  @Id
  @GeneratedValue
  private long id;

  private String bookingIdentifier;

  private LocalDate bookingFrom;

  private LocalDate bookingTo;

  @Version
  private long version;

}
