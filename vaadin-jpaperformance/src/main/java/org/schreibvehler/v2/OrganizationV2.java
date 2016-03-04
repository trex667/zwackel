package org.schreibvehler.v2;


import java.util.Collections;
import java.util.Set;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.schreibvehler.boundary.Organization;
import org.schreibvehler.boundary.User;


@Entity
@Access(AccessType.FIELD)
public class OrganizationV2 implements Organization
{

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;

    private String name;
    @ManyToMany
    private Set<UserV2> users;


    @Override
    public String getName()
    {
        return name;
    }


    @Override
    public Set<User> getUsers()
    {
        return Collections.unmodifiableSet(users);
    }


    public Integer getId()
    {
        return id;
    }


    public void setId(Integer id)
    {
        this.id = id;
    }


    public void setName(String name)
    {
        this.name = name;
    }


    public void setUsers(Set<UserV2> users)
    {
        this.users = users;
    }


    public void addUsers(Set<UserV2> users)
    {
        if (this.users == null)
        {
            this.users = users;
        }
        else
        {
            this.users.addAll(users);
        }
    }

}
