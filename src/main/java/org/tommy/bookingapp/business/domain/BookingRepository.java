package org.tommy.bookingapp.business.domain;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import javax.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface BookingRepository extends CrudRepository<SystemBooking, Long> {

  @Lock(value = LockModeType.OPTIMISTIC)
  @Override
  SystemBooking save(SystemBooking s);

  int deleteBookingByBookingIdentifier(final String bookingIdentifier);

  Optional<SystemBooking> findByBookingIdentifier(final String bookingIdentifier);

  @Modifying
  @Transactional
  @Query("update SystemBooking b set b.bookingFrom = :from, b.bookingTo = :to where b.bookingIdentifier = :identifier")
  int updateBooking(@Param("identifier") final String identifier, @Param("from") final LocalDate from,
                    @Param("to") final LocalDate to);

  @Query("select b from SystemBooking b where b.bookingFrom > :from ")
  Set<SystemBooking> findAllAfter(@Param("from") LocalDate from);
}
