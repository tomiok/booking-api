package org.tommy.bookingapp.business.component;

import org.tommy.bookingapp.business.domain.User;

public interface UserEntityGateway {

  User transformToModelUser(final String firstName, final String lastName, final String email);

  User saveUser(final User user);
}
