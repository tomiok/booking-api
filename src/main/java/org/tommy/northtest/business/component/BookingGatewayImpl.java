package org.tommy.northtest.business.component;

import static com.google.common.collect.Sets.newHashSet;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;
import org.springframework.transaction.annotation.Transactional;
import org.tommy.northtest.business.domain.Booking;
import org.tommy.northtest.business.domain.BookingRepository;

public class BookingGatewayImpl implements BookingGateway {

  private final BookingRepository bookingRepository;

  BookingGatewayImpl(final BookingRepository bookingRepository) {
    this.bookingRepository = bookingRepository;
  }

  @Override
  public String saveBooking(final BookingRequest request) {
    Booking booking = bookingRepository.save(toDomainObject(request));
    return booking.getBookingIdentifier();
  }

  @Override
  public Set<Booking> findAllBookings() {
    return newHashSet(bookingRepository.findAll());
  }

  @Override
  @Transactional
  public int deleteBooking(final String identifier) {
     return bookingRepository.deleteBookingByBookingIdentifier(identifier);
  }

  @Override
  public Booking findOne(final String identifier) {
    return bookingRepository.findByBookingIdentifier(identifier);
  }

  @Override
  public int updateBooking(final String identifier, final LocalDate from, final LocalDate to) {
    return bookingRepository.updateBooking(identifier, from, to);
  }

  private Booking toDomainObject(final BookingRequest request) {
    Booking booking = new Booking();

    booking.setBookingIdentifier(getRandomUuidIdentifier());
    booking.setBookingFrom(request.getBookingFrom());
    booking.setBookingTo(request.getBookingFrom().plusDays(request.getDays()));

    return booking;
  }

  private String getRandomUuidIdentifier() {
    return UUID.randomUUID().toString();
  }
}
