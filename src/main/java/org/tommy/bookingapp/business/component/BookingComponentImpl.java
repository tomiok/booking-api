package org.tommy.bookingapp.business.component;

import static java.util.stream.Collectors.toSet;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.tommy.bookingapp.business.domain.SystemBooking;
import org.tommy.bookingapp.business.usecase.availability.AvailabilityService;
import org.tommy.bookingapp.business.usecase.reservation.Booking;
import org.tommy.bookingapp.business.usecase.reservation.ReservationService;

class BookingComponentImpl implements BookingComponent {

  private static final Logger log = LoggerFactory.getLogger(BookingComponentImpl.class);

  private final BookingGateway bookingGateway;

  private final ReservationService reservationService;

  private final AvailabilityService availabilityService;

  BookingComponentImpl(final BookingGateway bookingGateway,
                       final ReservationService reservationService,
                       final AvailabilityService availabilityService) {
    this.bookingGateway = bookingGateway;
    this.reservationService = reservationService;
    this.availabilityService = availabilityService;
  }

  @Override
  public BookingResponse doCampsiteBooking(final BookingRequest bookingRequest) {
    Set<Booking> systemBookings = getSystemBookings();
    LocalDate bookingFrom = LocalDate.parse(bookingRequest.getBookingFrom());
    LocalDate bookingTo = bookingFrom.plusDays(bookingRequest.getDays());

    Booking userBook = new Booking(null, bookingFrom, bookingTo);
    boolean isAvailable = reservationService.isArrangeAvailable(systemBookings, userBook);

    if (isAvailable) {
      return new BookingResponse(bookingGateway.saveBooking(bookingRequest));
    }

    throw new IllegalArgumentException("That date is already booked!.");
  }

  @Override
  public int deleteBooking(final String identifier) {
    return bookingGateway.deleteBooking(identifier);
  }

  private Set<Booking> getSystemBookings() {
    return bookingGateway
        .findAllBookings(LocalDate.now())
        .stream()
        .map(b -> new Booking(b.getBookingIdentifier(), b.getBookingFrom(), b.getBookingTo()))
        .collect(toSet());
  }

  @Override
  @Transactional
  public BookingResponse updateBooking(final String identifier, final UpdateBookingRequest request) {
    SystemBooking booking = bookingGateway.findOne(identifier);

    Set<Booking> systemBookings = getSystemBookings();

    systemBookings
        .remove(new Booking(booking.getBookingIdentifier(), booking.getBookingFrom(), booking.getBookingTo()));
    //remove actual booking in the set.

    LocalDate bookingFrom = request.getUpdatedBookingFrom();
    LocalDate bookingTo = bookingFrom.plusDays(request.getDays());
    booking.setBookingFrom(bookingFrom);
    booking.setBookingTo(bookingTo);

    boolean isAvailable = reservationService.isArrangeAvailable(systemBookings,
        new Booking(booking.getBookingIdentifier(), booking.getBookingFrom(), booking.getBookingTo()));

    if (isAvailable) {
      int updatedBookings = bookingGateway.updateBooking(identifier, bookingFrom, bookingTo);

      log.info("{} booking was updated.", updatedBookings);
      return new BookingResponse(identifier);
    }

    throw new IllegalArgumentException("That date is already booked!.");
  }

  @Override
  public SystemBooking viewBooking(final String identifier) {
    return bookingGateway.findOne(identifier);
  }

  @Override
  public List<LocalDate> availableDays(final LocalDate from, final LocalDate to) {

    return availabilityService.getAvailableDays(
        bookingGateway.findAllBookings(LocalDate.now()),
        from,
        to
    );
  }
}
