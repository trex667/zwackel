package org.schreibvehler.v2;

import java.util.*;

import javax.persistence.*;

import org.schreibvehler.boundary.*;

@Entity
@Access(AccessType.FIELD)
public class OrganizationV2 implements Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;

    private String name;
    @ManyToMany
    private List<UserV2> users;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<User> getUsers() {
        return Collections.unmodifiableList(users);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsers(List<UserV2> users) {
        this.users = users;
    }

}