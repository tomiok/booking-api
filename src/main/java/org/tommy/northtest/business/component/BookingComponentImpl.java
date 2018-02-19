package org.tommy.northtest.business.component;

import static java.util.stream.Collectors.toSet;

import java.time.LocalDate;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tommy.northtest.business.domain.Booking;
import org.tommy.northtest.business.usecase.availability.AvailabilityService;
import org.tommy.northtest.business.usecase.reservation.Book;
import org.tommy.northtest.business.usecase.reservation.ReservationService;

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
    Set<Book> systemBookings = getSystemBookings();
    LocalDate bookingFrom = bookingRequest.getBookingFrom();
    LocalDate bookingTo = bookingFrom.plusDays(bookingRequest.getDays());

    Book userBook = new Book(null, bookingFrom, bookingTo);
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

  private Set<Book> getSystemBookings() {
    return bookingGateway
        .findAllBookings()
        .stream()
        .map(b -> new Book(b.getBookingIdentifier(), b.getBookingFrom(), b.getBookingTo()))
        .collect(toSet());
  }

  @Override
  public BookingResponse updateBooking(final String identifier, final UpdateBookingRequest request) {
    Booking booking = bookingGateway.findOne(identifier);
    if (booking == null) {
      throw new IllegalArgumentException("No bookings found with that identifier!");
    }

    Set<Book> systemBookings = getSystemBookings();

    systemBookings.remove(new Book(booking.getBookingIdentifier(), booking.getBookingFrom(), booking.getBookingTo()));
    //remove actual booking in the set.

    LocalDate bookingFrom = request.getUpdatedBookingFrom();
    LocalDate bookingTo = bookingFrom.plusDays(request.getDays());
    booking.setBookingFrom(bookingFrom);
    booking.setBookingTo(bookingTo);

    boolean isAvailable = reservationService.isArrangeAvailable(systemBookings,
        new Book(booking.getBookingIdentifier(), booking.getBookingFrom(), booking.getBookingTo()));

    if (isAvailable) {
      int updatedBookings = bookingGateway.updateBooking(identifier, bookingFrom, bookingTo);

      log.info("{} booking was updated.", updatedBookings);
      return new BookingResponse(identifier);
    }

    throw new IllegalArgumentException("That date is already booked!.");
  }

  @Override
  public Booking viewBooking(final String identifier) {
    return bookingGateway.findOne(identifier);
  }

  @Override
  public Set<LocalDate> availableDays(final LocalDate from, final LocalDate to) {

    return availabilityService.getAvailableDays(bookingGateway
        .findAllBookings()
        .stream()
        .map(b -> createSet(b.getBookingFrom(), b.getBookingTo()))
        .flatMap(Set::stream)
        .collect(toSet()), from, to);
  }

  private Set<LocalDate> createSet(final LocalDate from, final LocalDate to) {
    return from.datesUntil(to).collect(toSet());
  }
}
