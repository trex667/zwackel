package org.schreibvehler.v6;


import java.util.*;

import javax.persistence.*;

import org.schreibvehler.boundary.*;


@Entity
@Access(AccessType.FIELD)
public class OrganizationV6 implements Organization
{

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;

    private String name;
    @ManyToMany
    private Set<UserV6> users;


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


    @Override
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


    public void setUsers(Set<UserV6> users)
    {
        this.users = users;
    }


    public void addUsers(Set<UserV6> users)
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
