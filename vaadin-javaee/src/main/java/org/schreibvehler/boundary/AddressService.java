package org.schreibvehler.boundary;

import java.util.Collection;

import javax.inject.Inject;
import javax.persistence.*;

public class AddressService {
    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserService userService;

    public void createAddress(Address address) {
        em.persist(address);
    }

    public void removeAddress(Address address) {
        em.remove(address);
    }

    public void updateAddress(Address address) {
        em.merge(address);
    }

    public Address findAddress(Integer id) {
        return em.find(Address.class, id);
    }

    public Collection<Address> findAll() {
        return em.createQuery("SELECT a FROM Address a", Address.class).getResultList();
    }

    public Collection<Address> findAll(Integer userId) {
        User user = userService.findUser(userId);

        TypedQuery<Address> query = em.createQuery("SELECT a FROM Address a WHERE a.user = :user", Address.class);
        query.setParameter("user", user);
        return query.getResultList();
    }

    public void removeAllAdresses() {
        em.createQuery("DELETE FROM Address a").executeUpdate();
    }
}
