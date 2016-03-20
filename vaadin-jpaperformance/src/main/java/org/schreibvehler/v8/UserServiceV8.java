package org.schreibvehler.v8;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.schreibvehler.boundary.*;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.*;
import java.util.logging.Logger;

@Stateless
public class UserServiceV8 implements UserService {

    private static final Logger LOG = Logger.getLogger(UserServiceV8.class.getName());

    @PersistenceContext
    private EntityManager em;

    @Override
    public Result<User> findAllUsers() {
        return findAllUsers(null, null);
    }

    @Override
    public Result<User> findAllUsers(Integer startPosition, Integer fetchSize) {
        long start = System.currentTimeMillis();
        TypedQuery<User> query = em.createQuery("SELECT u FROM UserV8 u", User.class);
        if (isPaging(startPosition, fetchSize)) {
            query.setFirstResult(startPosition);
            query.setMaxResults(fetchSize);
        }

        List<User> list = query.getResultList();
        long end = System.currentTimeMillis();
        return new Result<User>(new TimeInterval(start, end), list);
    }

    boolean isPaging(Integer startPosition, Integer fetchSize) {
        if (startPosition == null || fetchSize == null || startPosition < 0 || fetchSize <= 1) {
            return false;
        }
        return true;
    }

    @Override
    public Result<User> createTestData(int count) {
        long start = System.currentTimeMillis();
        List<User> resultList = new ArrayList<>();
        Set<UserV8> entities = new HashSet<>();
        for (int i = 0; i < count; i++) {
            UserV8 user = new UserV8();
            user.setName(RandomStringUtils.randomAlphabetic(20));
            user.setBirthdate(
                    DateUtils.addYears(new Date(), Integer.parseInt(RandomStringUtils.random(2, "123456789")) * -1));
            em.persist(user);

            createAddresses(user);
            resultList.add(user);
            entities.add(user);
        }
        addToOrganizations(entities);
        long end = System.currentTimeMillis();
        return new Result<User>(new TimeInterval(start, end), resultList);
    }

    private void addToOrganizations(Set<UserV8> users) {
        String select = "SELECT o FROM OrganizationV8 o";
        TypedQuery<OrganizationV8> organizations = em.createQuery(select, OrganizationV8.class);
        if (organizations.getResultList() == null || organizations.getResultList().size() == 0) {
            createOrganizations(100);
        }
        organizations = em.createQuery(select, OrganizationV8.class);
        for (OrganizationV8 org : organizations.getResultList()) {
            org.addUsers(users);
            em.merge(org);
        }

    }


    private void createOrganizations(int amount) {
        for (int i = 0; i < amount; i++) {
            OrganizationV8 org = new OrganizationV8();
            org.setName(RandomStringUtils.randomAlphabetic(20));
            em.persist(org);
        }
    }

    private List<AddressV8> createAddresses(UserV8 user) {
        List<AddressV8> result = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            AddressV8 address = new AddressV8();
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
    public Result<Address> findAllAddresses(Integer userId) {
        long start = System.currentTimeMillis();
        TypedQuery<Address> query = em.createQuery("SELECT a FROM AddressV8 a WHERE a.user.id=:userid", Address.class);
        query.setParameter("userid", userId);

        List<Address> resultList = query.getResultList();
        long end = System.currentTimeMillis();
        return new Result<Address>(new TimeInterval(start, end), resultList);
    }

    @Override
    public Result<Organization> findAllOrganizations(Integer userId) {
        long start = System.currentTimeMillis();
        TypedQuery<Organization> query = em.createQuery("SELECT o FROM OrganizationV8 o WHERE :user MEMBER OF o.users",
                Organization.class);
        query.setParameter("user", em.find(UserV8.class, userId));

        List<Organization> resultList = query.getResultList();
        long end = System.currentTimeMillis();
        return new Result<Organization>(new TimeInterval(start, end), resultList);
    }

}
