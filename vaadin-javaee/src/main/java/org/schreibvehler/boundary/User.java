package org.schreibvehler.boundary;

import java.util.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.FIELD)
public class User extends AbstractEntity {
    private static final long serialVersionUID = -7546375363619283295L;
    @NotNull
    private String shortName;
    private String firstName;
    private String lastName;
    @Temporal(TemporalType.DATE)
    private Date birthdate;

    @OneToMany
    private List<Address> addresses;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> skills;

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @Override
    public String toString() {
        return String.format("User[id=%d; shortname=%s]", getId(), shortName);
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getShortName() {
        return shortName;
    }

    public List<Address> getAddresses() {
        return addresses != null ? addresses : Collections.emptyList();
    }

    public void setAddresses(List<Address> addresses_arg) {
        addresses = addresses_arg;
    }
}
