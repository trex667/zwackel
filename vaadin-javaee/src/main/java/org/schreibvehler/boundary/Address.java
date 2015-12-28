package org.schreibvehler.boundary;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
@Access(AccessType.FIELD)
public class Address extends AbstractEntity {

    private static final long serialVersionUID = 664418609139844091L;

    @ManyToOne
    @NotNull
    private User user;

    private String street;
    private String zipCode;
    private String city;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        ToStringBuilder builder = new ToStringBuilder(this);
        builder.append("street", street);
        builder.append("zipCode", zipCode);
        builder.append("city", city);
        return builder.toString();
    }
}
