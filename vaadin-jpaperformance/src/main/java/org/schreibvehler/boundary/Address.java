package org.schreibvehler.boundary;

public interface Address {
    Integer getId();
    String getStreet();
    Integer getPostCode();
    String getCity();
    String getCountry();
}
