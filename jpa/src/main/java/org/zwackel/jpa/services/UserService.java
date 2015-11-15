package org.zwackel.jpa.services;

import org.zwackel.jpa.entity.*;

public interface UserService {
    User createUser(User user);

    User addAddress(Integer userId, Address address);

    User findUser(Integer id);

    User findUser(String shortName);
}
