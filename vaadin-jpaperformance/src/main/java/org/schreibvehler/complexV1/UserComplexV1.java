package org.schreibvehler.complexV1;


import java.util.Collections;
import java.util.Date;
import java.util.Set;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.schreibvehler.boundary.Address;
import org.schreibvehler.boundary.Organization;
import org.schreibvehler.boundary.User;


@Entity
@Access(AccessType.FIELD)
public class UserComplexV1 implements User
{

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;
    private String name;
    @Temporal(TemporalType.DATE)
    private Date birthdate;
    @OneToMany(mappedBy = "user")
    private Set<AddressComplexV1> addresses;
    @ManyToMany(mappedBy = "users")
    private Set<OrganizationComplexV1> organizations;


    @Override
    public String getName()
    {
        return name;
    }


    @Override
    public Date getBirthdate()
    {
        return birthdate;
    }


    @Override
    public Set<Address> getAddresses()
    {
        return Collections.unmodifiableSet(addresses);
    }


    @Override
    public Set<Organization> getOrganizations()
    {
        return Collections.unmodifiableSet(organizations);
    }


    @Override
    public Integer getId()
    {
        return id;
    }


    public void setId(Integer id)
    {
        this.id = id;
    }


    public void setBirthdate(Date birthdate)
    {
        this.birthdate = birthdate;
    }


    public void setName(String name)
    {
        this.name = name;
    }


    public void setAddresses(Set<AddressComplexV1> addresses)
    {
        this.addresses = addresses;
    }


    public void setOrganizations(Set<OrganizationComplexV1> organizations)
    {
        this.organizations = organizations;
    }
}
