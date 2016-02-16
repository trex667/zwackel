package org.schreibvehler.boundary;

import java.util.*;

public interface User {

    TimeInterval getTimeInterval();
    
    String getName();
    Date getBirthDate();
    
    List<Address> getAddresses();

    List<Organization> getOrganizations();
}
