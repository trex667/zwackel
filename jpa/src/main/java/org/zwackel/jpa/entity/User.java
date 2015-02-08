package org.zwackel.jpa.entity;

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
    private Date birthdate;
    @Embedded
    private List<Accessibility> accessibilities = new ArrayList<Accessibility>();
    @Embedded
    private Accessibility email;

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

    public List<Accessibility> getAccessibilities() {
        return accessibilities;
    }

    public void addAccessibility(Accessibility accessibility) {
        accessibilities.add(accessibility);
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Accessibility getEmail() {
        return email;
    }

    public void setEmail(Accessibility email) {
        this.email = email;
    }

}
