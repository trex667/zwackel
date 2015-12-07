package org.schreibvehler.boundary;

import java.util.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.FIELD)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;
    @NotNull
    private String shortName;
    private String firstName;
    private String lastName;
    @Temporal(TemporalType.DATE)
    private Date birthdate;

    @OneToMany
    private Collection<Address> addresses;

    @ElementCollection(fetch = FetchType.EAGER)
    private Collection<String> skills;

    public void setId(int id) {
        this.id = id;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (id == null ? 0 : id);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        User other = (User) obj;
        if (id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.format("User[id=%d; shortname=%s]", id, shortName);
    }

    public Integer getId() {
        return id;
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

    public void setId(Integer id) {
        this.id = id;
    }

    public Collection<Address> getAddress() {
        return addresses != null ? addresses : Collections.emptyList();
    }

    public void setAddresses(Collection<Address> addresses_arg) {
        addresses = addresses_arg;
    }

    public void addAddress(Address address) {
        if (this.addresses == null) {
            addresses = new ArrayList<>();
        }
        addresses.add(address);
    }

    public Collection<String> getSkills() {
        return skills;
    }

    public void addSkill(String skill) {
        if (skills == null) {
            skills = new ArrayList<String>();
        }
        skills.add(skill);
    }

}
