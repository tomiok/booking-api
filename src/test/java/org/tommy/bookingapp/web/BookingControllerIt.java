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

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookingControllerIt {

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
  public void bookingTest() throws Exception {
    BookingRequest req = new BookingRequest(
        "tomas",
        "lingotti",
        "tomi@msn.com",
        "2018-01-01",
        3);
    HttpEntity<String> httpEntity = new HttpEntity<>(om.writeValueAsString(req), headers);
    ResponseEntity<String> response = restTemplate
        .exchange(createURLWithPort("/booking"), HttpMethod.POST, httpEntity, String.class);

    assertThat(response.getBody()).isNotEmpty();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

  private String createURLWithPort(String uri) {
    return "http://localhost:" + port + "/api" + uri;
  }
}
