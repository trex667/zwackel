package org.schreibvehler.v1;

import java.util.*;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.*;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.schreibvehler.boundary.*;

@Stateless
public class UserServiceV1 implements UserService {

    private static final Logger LOG = Logger.getLogger(UserServiceV1.class.getName());

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<User> findAllUsers() {
        TypedQuery<User> query = em.createQuery("SELECT u FROM UserV1 u", User.class);

        return query.getResultList();
    }

    @Override
    public List<User> createTestData(int count) {
        List<User> result = new ArrayList<>();
        List<UserV1> entities = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            UserV1 user = new UserV1();
            user.setName(RandomStringUtils.randomAlphabetic(20));
            user.setBirthdate(
                    DateUtils.addYears(new Date(), Integer.parseInt(RandomStringUtils.random(2, "123456789")) * -1));
            em.persist(user);

            createAddresses(user);
            result.add(user);
            entities.add(user);
        }
        createOrganizations(entities);
        return result;
    }

    private void createOrganizations(List<UserV1> users) {
        for (int i = 0; i < 100; i++) {
            OrganizationV1 org = new OrganizationV1();
            org.setUsers(users);
            org.setName(RandomStringUtils.randomAlphabetic(20));
            em.persist(org);
        }

    }

    private List<OrganizationV1> getOrganizations() {
        TypedQuery<OrganizationV1> query = em.createQuery("Select o from OrganizationV1 o", OrganizationV1.class);
        return query.getResultList();
    }

    private List<AddressV1> createAddresses(UserV1 user) {
        List<AddressV1> result = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            AddressV1 address = new AddressV1();
            address.setUser(user);
            address.setCity(DataUtils.getRandomCity());
            address.setCountry("Germany");
            address.setStreet(RandomStringUtils.randomAlphabetic(14));
            address.setPostCode(new Integer(RandomStringUtils.random(5, "123456789")));
            em.persist(address);
            result.add(address);
        }
        return result;
    }

    @Override
    public List<Address> findAllAddresses(Integer userId) {
        TypedQuery<Address> query = em.createQuery("SELECT a FROM AddressV1 a WHERE a.user.id=:userid", Address.class);
        query.setParameter("userid", userId);

        LOG.info(String.format("Found %d addresses for userId %d", query.getResultList().size(), userId));
        return query.getResultList();
    }

    @Override
    public List<Organization> findAllOrganizations(Integer userId) {
        TypedQuery<Organization> query = em.createQuery("SELECT o FROM OrganizationV1 o WHERE :user MEMBER OF o.users", Organization.class);
        query.setParameter("user", em.find(UserV1.class, userId));
        
        LOG.info(String.format("Found %d organizations for userId %d", query.getResultList().size(), userId));
        return query.getResultList();
    }

}
