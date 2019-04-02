package org.tommy.bookingapp.business.component;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;
import javax.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.tommy.bookingapp.business.domain.BookingRepository;
import org.tommy.bookingapp.business.domain.SystemBooking;

public class BookingGatewayImpl implements BookingGateway {

  private final BookingRepository bookingRepository;

  BookingGatewayImpl(final BookingRepository bookingRepository) {
    this.bookingRepository = bookingRepository;
  }

  @Override
  public String saveBooking(final BookingRequest request) {
    SystemBooking booking = bookingRepository.save(toDomainObject(request));
    return booking.getBookingIdentifier();
  }

  @Override
  public Set<SystemBooking> findAllBookingsFromDate(LocalDate from) {
    return bookingRepository.findAllAfter(from);
  }

  @Override
  @Transactional
  public int deleteBooking(final String identifier) {
    return bookingRepository.deleteBookingByBookingIdentifier(identifier);
  }

  @Override
  public SystemBooking findByIdentifier(final String identifier) {
    return bookingRepository.findByBookingIdentifier(identifier).orElseThrow(EntityNotFoundException::new);
  }

  @Override
  public int updateBooking(final String identifier, final LocalDate from, final LocalDate to) {
    return bookingRepository.updateBooking(identifier, from, to);
  }

  private SystemBooking toDomainObject(final BookingRequest request) {
    SystemBooking booking = new SystemBooking();

    booking.setBookingIdentifier(getRandomUuidIdentifier());
    booking.setBookingFrom(LocalDate.parse(request.getBookingFrom()));
    booking.setBookingTo(LocalDate.parse(request.getBookingFrom()).plusDays(request.getDays()));

    return booking;
  }

  private String getRandomUuidIdentifier() {
    return UUID.randomUUID().toString();
  }
}
