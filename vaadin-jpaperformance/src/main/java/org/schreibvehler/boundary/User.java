package org.schreibvehler.boundary;

import java.util.*;

public interface User {

    Integer getId();
    String getName();
    Date getBirthdate();

    List<Address> getAddresses();

    List<Organization> getOrganizations();
}
