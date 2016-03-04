package org.schreibvehler.boundary;

import java.util.Set;

public interface Organization {
    Integer getId();
    String getName();
    Set<User> getUsers();
}
