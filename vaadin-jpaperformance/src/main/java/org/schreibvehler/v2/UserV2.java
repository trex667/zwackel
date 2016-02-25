package org.schreibvehler.v2;

import java.util.*;

import javax.persistence.*;

import org.schreibvehler.boundary.*;

@Entity
@Access(AccessType.FIELD)
public class UserV2 implements User {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;
    private String name;
    @Temporal(TemporalType.DATE)
    private Date birthdate;
    @OneToMany(fetch = FetchType.EAGER)
    private List<AddressV2> addresses;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<OrganizationV2> organizations;


    @Override
    public String getName() {
        return name;
    }

    @Override
    public Date getBirthdate() {
        return birthdate;
    }

    @Override
    public List<Address> getAddresses() {
        return Collections.unmodifiableList(addresses);
    }

    @Override
    public List<Organization> getOrganizations() {
        return Collections.unmodifiableList(organizations);
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

    public void setAddresses(List<AddressV2> addresses) {
        this.addresses = addresses;
    }

    public void setOrganizations(List<OrganizationV2> organizations) {
        this.organizations = organizations;
    }
}
