package org.schreibvehler.boundary;

import java.util.List;

public interface UserService {

    List<User> findAllUsers();
    
    void ensureTestData();
}
