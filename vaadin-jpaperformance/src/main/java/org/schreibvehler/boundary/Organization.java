package org.schreibvehler.boundary;

import java.util.Set;

public interface Organization {
    String getName();
    Set<User> getUsers();
}
