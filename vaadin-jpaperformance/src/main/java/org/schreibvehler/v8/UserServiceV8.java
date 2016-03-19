package org.schreibvehler.v8;


import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.schreibvehler.boundary.*;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
        long start = System.currentTimeMillis();
        Query query = em.createNativeQuery("SELECT DISTINCT u.*, a.*, o.* FROM USERV8 u, AddressV8 a, OrganizationV8 o, ORGANIZATIONV8_USERV8 ou WHERE u.id = a.user_id AND u.id = ou.users_id AND o.id = ou.organizations_id", UserV8.class);
        List<User> list = query.getResultList();
        long end = System.currentTimeMillis();
        return new Result<User>(new TimeInterval(start, end), list);
    }


    @Override
    public Result<User> createTestData(int count) {
        long start = System.currentTimeMillis();
        List<User> resultList = new ArrayList<>();
        Set<UserV8> entities = new HashSet<>();
        for (int i = 0; i < count; i++) {
            UserV8 user = new UserV8();
            user.setName(RandomStringUtils.randomAlphabetic(20));
            user.setBirthdate(DateUtils.addYears(new Date(), Integer.parseInt(RandomStringUtils.random(2, "123456789")) * -1));
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
        Query query = em.createNativeQuery("SELECT * FROM AddressV8 WHERE user_id=:userid", AddressV8.class);
        query.setParameter("userid", userId);

        List<Address> resultList = query.getResultList();
        long end = System.currentTimeMillis();
        return new Result<Address>(new TimeInterval(start, end), resultList);
    }


    @Override
    public Result<Organization> findAllOrganizations(Integer userId) {
        long start = System.currentTimeMillis();
        Query query = em.createNativeQuery("SELECT org.* FROM OrganizationV8 org LEFT OUTER JOIN ORGANIZATIONV8_USERV8 org_user ON org.id = org_user.organizations_id  WHERE org_user.users_id = :user",
                OrganizationV8.class);
        query.setParameter("user", userId);

        List<Organization> resultList = query.getResultList();
        long end = System.currentTimeMillis();
        return new Result<Organization>(new TimeInterval(start, end), resultList);
    }

}
