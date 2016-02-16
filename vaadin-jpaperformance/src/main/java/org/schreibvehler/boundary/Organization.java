package org.schreibvehler.boundary;

import java.util.List;

public interface Organization {
    String getName();
    List<User> getUsers();
}
