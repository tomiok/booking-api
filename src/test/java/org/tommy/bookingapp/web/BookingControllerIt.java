package org.tommy.bookingapp.web;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
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
import org.tommy.bookingapp.business.domain.SystemBooking;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookingControllerIt {

  private static final String BOOKING_API = "/booking";

  @LocalServerPort
  private int port;

  private TestRestTemplate restTemplate = new TestRestTemplate();

  private HttpHeaders headers = new HttpHeaders();

  private ObjectMapper om = new ObjectMapper();

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

    SystemBooking bookingIdentifier = om.readValue(response.getBody(), SystemBooking.class);
    String bId = bookingIdentifier.getBookingIdentifier();
    ResponseEntity<SystemBooking> getResponse = restTemplate
        .exchange(
            createURLWithPort(BOOKING_API + "/" + bId),
            HttpMethod.GET,
            getEntity,
            SystemBooking.class);

    SystemBooking resBooking = getResponse.getBody();

    assertThat(resBooking).isNotNull();
    assertThat(resBooking.getBookingIdentifier()).isEqualTo(bId);
    assertThat(resBooking.getBookingFrom()).isEqualTo("2018-01-01");
    assertThat(resBooking.getBookingTo()).isEqualTo("2018-01-03");
  }

  @Test
  public void saveAndUpdateBookingTest() throws Exception {
    ResponseEntity<String> response = saveBooking("2019-01-01", 3);
    String identifier = om.readValue(response.getBody(), SystemBooking.class).getBookingIdentifier();



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
