package org.schreibvehler.v2;

import javax.persistence.*;

import org.schreibvehler.boundary.Address;

@Entity
@Access(AccessType.FIELD)
public class AddressV2 implements Address {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;

    private String street;
    private Integer postCode;
    private String city;
    private String country;
    @ManyToOne
    private UserV2 user;

    @Override
    public String getStreet() {
        return street;
    }

    @Override
    public Integer getPostCode() {
        return postCode;
    }

    @Override
    public String getCity() {
        return city;
    }

    @Override
    public String getCountry() {
        return country;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setPostCode(Integer postCode) {
        this.postCode = postCode;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public UserV2 getUser() {
        return user;
    }

    public void setUser(UserV2 user) {
        this.user = user;
    }

}
