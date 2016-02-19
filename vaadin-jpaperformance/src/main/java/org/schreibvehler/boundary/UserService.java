package org.schreibvehler.boundary;

import java.util.List;

public interface UserService {

    List<User> findAllUsers();

    List<User> createTestData(int count);

    List<Address> findAllAddresses(Integer userId);
}
