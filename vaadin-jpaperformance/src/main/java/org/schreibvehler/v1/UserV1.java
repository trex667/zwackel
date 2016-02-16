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
    private List<AddressV1> addresses;
    @ManyToMany
    private List<OrganizationV1> organizations;

    @Transient
    TimeInterval timeInterval;
    
    @Override
    public TimeInterval getTimeInterval() {
        return timeInterval;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Date getBirthDate() {
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddresses(List<AddressV1> addresses) {
        this.addresses = addresses;
    }

    public void setOrganizations(List<OrganizationV1> organizations) {
        this.organizations = organizations;
    }

    public void setTimeInterval(TimeInterval timeInterval) {
        this.timeInterval = timeInterval;
    }

}
