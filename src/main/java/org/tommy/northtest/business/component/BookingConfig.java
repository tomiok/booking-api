package org.tommy.northtest.business.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.tommy.northtest.business.domain.BookingRepository;
import org.tommy.northtest.business.domain.UserRepository;
import org.tommy.northtest.business.usecase.availability.AvailabilityService;
import org.tommy.northtest.business.usecase.availability.AvailabilityServiceImpl;
import org.tommy.northtest.business.usecase.reservation.ReservationService;
import org.tommy.northtest.business.usecase.reservation.ReservationServiceImpl;

@Configuration
public class BookingConfig {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private BookingRepository bookingRepository;

  @Bean
  public UserEntityGateway userEntityGateway() {
    return new UserEntityGatewayImpl(userRepository);
  }

  @Bean
  public BookingGateway bookingGateway() {
    return new BookingGatewayImpl(bookingRepository);
  }

  @Bean
  public ReservationService reservationService() {
    return new ReservationServiceImpl();
  }

  @Bean
  public AvailabilityService availabilityService() {
    return new AvailabilityServiceImpl();
  }

  @Bean
  public BookingComponent bookingComponent() {
    return new BookingComponentImpl(bookingGateway(), reservationService(), availabilityService());
  }
}
