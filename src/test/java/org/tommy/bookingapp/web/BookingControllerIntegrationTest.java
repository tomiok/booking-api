package org.tommy.bookingapp.web;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.tommy.bookingapp.business.component.BookingRequest;
import org.tommy.bookingapp.business.component.BookingResponse;
import org.tommy.bookingapp.business.component.BookingSummary;
import org.tommy.bookingapp.business.component.UpdateBookingRequest;
import org.tommy.bookingapp.business.domain.SystemBooking;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookingControllerIntegrationTest {

  private static final String BOOKING_API = "/booking";

  @LocalServerPort
  private int port;

  private TestRestTemplate restTemplate = new TestRestTemplate();

  private HttpHeaders headers = new HttpHeaders();

  private ObjectMapper om = new ObjectMapper();

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setUp() {
    headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
  }

  @Test
  public void saveAndGetBookingTest() throws Exception {
    ResponseEntity<String> response = saveBooking("2018-01-01", 3);
    assertThat(response.getBody()).isNotEmpty();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    HttpEntity<String> getEntity = new HttpEntity<>(null, headers);

    BookingSummary bookingIdentifier = om.readValue(response.getBody(), BookingSummary.class);
    String bId = bookingIdentifier.getBookingIdentifier();
    ResponseEntity<BookingSummary> getResponse = restTemplate
        .exchange(
            createURLWithPort(BOOKING_API + "/" + bId),
            HttpMethod.GET,
            getEntity,
            BookingSummary.class);

    BookingSummary resBooking = getResponse.getBody();

    assertThat(resBooking).isNotNull();
    assertThat(resBooking.getBookingIdentifier()).isEqualTo(bId);
    assertThat(resBooking.getBookingFrom()).isEqualTo("2018-01-01");
    assertThat(resBooking.getBookingTo()).isEqualTo("2018-01-03");
  }

  @Test
  public void saveAndUpdateBookingTest() throws Exception {
    ResponseEntity<String> response = saveBooking("2019-01-01", 3);
    String identifier = om.readValue(response.getBody(), SystemBooking.class).getBookingIdentifier();

    UpdateBookingRequest updateReq = new UpdateBookingRequest("2019-01-02", 3);
    HttpEntity<UpdateBookingRequest> httpReq = new HttpEntity<>(updateReq, headers);

    ResponseEntity<BookingResponse> responseValue = restTemplate
        .exchange(
            createURLWithPort(BOOKING_API + "/" + identifier),
            HttpMethod.PUT,
            httpReq,
            BookingResponse.class);

    BookingResponse httpResponse = responseValue.getBody();

    assertThat(httpResponse).isNotNull();
    assertThat(httpResponse.getBookingIdentifier()).isNotEmpty();
  }

  @Test
  public void shouldFailGivenUpdateRequestDateAlreadyBooked() throws Exception {
    LocalDate now = LocalDate.now();
    ResponseEntity<String> response = saveBooking(now.toString(), 3);
    saveBooking(now.plusDays(5).toString(), 3);
    String identifier = om.readValue(response.getBody(), SystemBooking.class).getBookingIdentifier();

    UpdateBookingRequest updateReq = new UpdateBookingRequest(now.plusDays(6).toString(), 3);
    HttpEntity<UpdateBookingRequest> httpReq = new HttpEntity<>(updateReq, headers);

    ResponseEntity<BookingResponse> res = restTemplate
        .exchange(
            createURLWithPort(BOOKING_API + "/" + identifier),
            HttpMethod.PUT,
            httpReq,
            BookingResponse.class);

    assertThat(res.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
  }

  private ResponseEntity<String> saveBooking(String from, int days) throws Exception {
    BookingRequest req = new BookingRequest(
        "tomas",
        "lingotti",
        "tomi@msn.com",
        from,
        days);
    HttpEntity<String> createEntity = new HttpEntity<>(om.writeValueAsString(req), headers);
    return restTemplate.exchange(createURLWithPort(BOOKING_API), HttpMethod.POST, createEntity, String.class);
  }

  private String createURLWithPort(String uri) {
    return "http://localhost:" + port + "/api" + uri;
  }
}
