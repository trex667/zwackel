package org.schreibvehler.v1;

import java.util.*;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.*;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.schreibvehler.boundary.*;
import org.schreibvehler.v5.OrganizationV5;
import org.schreibvehler.v5.UserV5;

@Stateless
public class UserServiceV1 implements UserService {

    private static final Logger LOG = Logger.getLogger(UserServiceV1.class.getName());

    @PersistenceContext
    private EntityManager em;

    @Override
    public Result<User> findAllUsers() {
        long start = System.currentTimeMillis();
        TypedQuery<User> query = em.createQuery("SELECT u FROM UserV1 u", User.class);

        List<User> list = query.getResultList();
        long end = System.currentTimeMillis();
        return new Result<User>(new TimeInterval(start, end), list);
    }

    @Override
    public Result<User> createTestData(int count) {
        long start = System.currentTimeMillis();
        List<User> resultList = new ArrayList<>();
        Set<UserV1> entities = new HashSet<>();
        for (int i = 0; i < count; i++) {
            UserV1 user = new UserV1();
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

    private void addToOrganizations(Set<UserV1> users)
    {
        String select = "SELECT o FROM OrganizationV1 o";
        TypedQuery<OrganizationV1> organizations = em.createQuery(select, OrganizationV1.class);
        if (organizations.getResultList() == null || organizations.getResultList().size() == 0)
        {
            createOrganizations(100);
        }
        organizations = em.createQuery(select, OrganizationV1.class);
        for (OrganizationV1 org : organizations.getResultList())
        {
            org.addUsers(users);
            em.merge(org);
        }

    }


    private void createOrganizations(int amount)
    {
        for (int i = 0; i < amount; i++)
        {
            OrganizationV1 org = new OrganizationV1();
            org.setName(RandomStringUtils.randomAlphabetic(20));
            em.persist(org);
        }
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
    public Result<Address> findAllAddresses(Integer userId) {
        long start = System.currentTimeMillis();
        TypedQuery<Address> query = em.createQuery("SELECT a FROM AddressV1 a WHERE a.user.id=:userid", Address.class);
        query.setParameter("userid", userId);

        List<Address> resultList = query.getResultList();
        long end = System.currentTimeMillis();
        return new Result<Address>(new TimeInterval(start, end), resultList);
    }

    @Override
    public Result<Organization> findAllOrganizations(Integer userId) {
        long start = System.currentTimeMillis();
        TypedQuery<Organization> query = em.createQuery("SELECT o FROM OrganizationV1 o WHERE :user MEMBER OF o.users",
                Organization.class);
        query.setParameter("user", em.find(UserV1.class, userId));

        List<Organization> resultList = query.getResultList();
        long end = System.currentTimeMillis();
        return new Result<Organization>(new TimeInterval(start, end), resultList);
    }

}
