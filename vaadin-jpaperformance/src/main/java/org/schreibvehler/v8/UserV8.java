package org.schreibvehler.v8;

import org.schreibvehler.boundary.Address;
import org.schreibvehler.boundary.Organization;
import org.schreibvehler.boundary.User;

import javax.persistence.*;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

@Entity
@Access(AccessType.FIELD)
public class UserV8 implements User {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;
    private String name;
    @Temporal(TemporalType.DATE)
    private Date birthdate;
    @OneToMany(mappedBy = "user")
    private Set<AddressV8> addresses;
    @ManyToMany(mappedBy = "users")
    private Set<OrganizationV8> organizations;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Date getBirthdate() {
        return birthdate;
    }

    @Override
    public Set<Address> getAddresses() {
        return Collections.unmodifiableSet(addresses);
    }

    @Override
    public Set<Organization> getOrganizations() {
        return Collections.unmodifiableSet(organizations);
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddresses(Set<AddressV8> addresses) {
        this.addresses = addresses;
    }

    public void setOrganizations(Set<OrganizationV8> organizations) {
        this.organizations = organizations;
    }
}
