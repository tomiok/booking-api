package org.tommy.northtest.business.component;

import org.tommy.northtest.business.domain.User;
import org.tommy.northtest.business.domain.UserRepository;

public class UserEntityGatewayImpl implements UserEntityGateway {

  private final UserRepository userRepository;

  UserEntityGatewayImpl(final UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public User transformToModelUser(final String firstName, final String lastName, final String email) {
    User user = new User();

    user.setFirstName(firstName);
    user.setLastName(lastName);
    user.setEmail(email);
    return user;
  }

  @Override
  public User saveUser(final User user) {
    return userRepository.save(user);
  }
}
