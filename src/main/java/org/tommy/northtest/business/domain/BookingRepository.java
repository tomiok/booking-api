package org.tommy.northtest.business.domain;

import java.time.LocalDate;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface BookingRepository extends CrudRepository<Booking, Long> {

  int deleteBookingByBookingIdentifier(final String bookingIdentifier);

  Booking findByBookingIdentifier(final String bookingIdentifier);

  @Modifying
  @Transactional
  @Query("update Booking b set b.bookingFrom = :from, b.bookingTo = :to where b.bookingIdentifier = :identifier")
  int updateBooking(@Param("identifier") final String identifier, @Param("from") final LocalDate from,
                 @Param("to") final LocalDate to);
}
