package org.schreibvehler.v8;


import org.schreibvehler.boundary.Organization;
import org.schreibvehler.boundary.User;

import javax.persistence.*;
import java.util.Collections;
import java.util.Set;


@Entity
@Access(AccessType.FIELD)
public class OrganizationV8 implements Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;

    private String name;
    @ManyToMany
    private Set<UserV8> users;


    @Override
    public String getName() {
        return name;
    }


    @Override
    public Set<User> getUsers() {
        return Collections.unmodifiableSet(users);
    }


    @Override
    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }


    public void setName(String name) {
        this.name = name;
    }


    public void setUsers(Set<UserV8> users) {
        this.users = users;
    }


    public void addUsers(Set<UserV8> users) {
        if (this.users == null) {
            this.users = users;
        } else {
            this.users.addAll(users);
        }
    }

}
