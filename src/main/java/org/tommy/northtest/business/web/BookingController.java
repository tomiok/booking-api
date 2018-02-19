package org.tommy.northtest.business.web;

import static org.springframework.http.ResponseEntity.ok;

import java.time.LocalDate;
import org.apache.commons.lang3.Validate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tommy.northtest.business.component.BookingComponent;
import org.tommy.northtest.business.component.BookingRequest;
import org.tommy.northtest.business.component.BookingResponse;
import org.tommy.northtest.business.component.UpdateBookingRequest;
import org.tommy.northtest.business.domain.Booking;

@RestController
@RequestMapping("/booking")
public class BookingController {

  private final BookingComponent bookingComponent;

  public BookingController(final BookingComponent bookingComponent) {
    this.bookingComponent = bookingComponent;
  }

  private static AvailabilityHttpRequest checkAndGetAvailabilityToSearch(final String from, final String to) {
    LocalDate fromDate = LocalDate.parse(from);
    Validate.notNull(from, "From date cannot be null.");

    if (to == null) {
      return new AvailabilityHttpRequest(fromDate, fromDate.plusDays(30));
    }

    return new AvailabilityHttpRequest(fromDate, LocalDate.parse(to));
  }

  private static void validateDaysToBook(final int days) {
    Validate.isTrue(days > 0 && days <= 3, "You can book the campsite between 1 and 3 days!.");
  }

  @PostMapping
  public ResponseEntity<BookingResponse> book(@RequestBody BookingRequest request) {
    validateDaysToBook(request.getDays());
    return ok(bookingComponent.doCampsiteBooking(request));
  }

  @DeleteMapping("/{identifier}")
  public ResponseEntity<String> deleteBooking(@PathVariable("identifier") final String identifier) {
    if (bookingComponent.deleteBooking(identifier) != 0) {
      return ok("Booking was deleted!");
    }

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No bookings found with that identifier");
  }

  @PutMapping("/{identifier}")
  public ResponseEntity<BookingResponse> updateBooking(@PathVariable("identifier") final String identifier,
                                                       @RequestBody UpdateBookingRequest request) {
    validateDaysToBook(request.getDays());
    return ok(bookingComponent.updateBooking(identifier, request));
  }

  @GetMapping("/{identifier}")
  public ResponseEntity<Booking> viewDomainBooking(@PathVariable("identifier") final String identifier) {
    return ok(bookingComponent.viewBooking(identifier));
  }

  @GetMapping("/check")
  public ResponseEntity<?> checkAvailability(@RequestParam("from") final String from,
                                             @RequestParam("to") final String to) {
    AvailabilityHttpRequest req = checkAndGetAvailabilityToSearch(from, to);
    return ok(bookingComponent.availableDays(req.getFrom(), req.getTo()));
  }
}
