package org.schreibvehler.boundary;

import java.util.*;

public interface User {

    Integer getId();
    String getName();
    Date getBirthdate();

    Set<Address> getAddresses();

    Set<Organization> getOrganizations();
}
