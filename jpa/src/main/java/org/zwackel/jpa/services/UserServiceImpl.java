package org.zwackel.jpa.services;

import javax.ejb.Stateless;
import javax.persistence.*;

import org.zwackel.jpa.entity.*;

@Stateless
public class UserServiceImpl implements UserService {

    @PersistenceContext
    private EntityManager em;
    
    public User createUser(User user) {
        em.persist(user);
        return user;
    }

    public User addAddress(Integer id, Address address) {
        User user = findUser(id);
        user.setAddress(address);
        return user;
    }

    public User findUser(Integer id) {
        return em.find(User.class, id);
    }

    public User findUser(String shortName) {
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.shortName = :1", User.class);
        query.setParameter("shortname", shortName);
        return query.getSingleResult();
    }

}
