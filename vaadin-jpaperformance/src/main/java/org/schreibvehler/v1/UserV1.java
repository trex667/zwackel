package org.schreibvehler.v1;

import java.util.*;

import javax.persistence.*;

import org.schreibvehler.boundary.*;

@Entity
@Access(AccessType.FIELD)
public class UserV1 implements User {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;
    private String name;
    @Temporal(TemporalType.DATE)
    private Date birthdate;
    @OneToMany
    private Set<AddressV1> addresses;
    @ManyToMany
    private Set<OrganizationV1> organizations;


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

    public void setAddresses(Set<AddressV1> addresses) {
        this.addresses = addresses;
    }

    public void setOrganizations(Set<OrganizationV1> organizations) {
        this.organizations = organizations;
    }
}
