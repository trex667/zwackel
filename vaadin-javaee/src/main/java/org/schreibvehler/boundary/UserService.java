package org.schreibvehler.boundary;

import java.util.Collection;

import javax.ejb.Stateless;
import javax.persistence.*;

@Stateless
public class UserService {

    @PersistenceContext
    private EntityManager em;

    public void createUser(User user) {
        em.persist(user);
    }

    public User findUser(Integer id) {
        return em.find(User.class, id);
    }

    public void removeUser(Integer id) {
        em.remove(findUser(id));
    }

    public void removeAllUsers() {
        em.createQuery("DELETE FROM User u").executeUpdate();
    }

    public Collection<User> findAll() {
        return em.createQuery("SELECT u FROM User u JOIN FETCH u.addresses", User.class).getResultList();
    }
}
