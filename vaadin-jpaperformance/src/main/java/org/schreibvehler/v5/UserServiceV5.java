package org.schreibvehler.v5;

import java.util.*;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.*;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.schreibvehler.boundary.*;

@Stateless
public class UserServiceV5 implements UserService {

    private static final Logger LOG = Logger.getLogger(UserServiceV5.class.getName());

    @PersistenceContext
    private EntityManager em;

    @Override
    public Result<User> findAllUsers() {
        long start = System.currentTimeMillis();
        
        EntityGraph<UserV5> graph = em.createEntityGraph(UserV5.class);
        graph.addAttributeNodes("addresses");
        graph.addAttributeNodes("organizations");
        TypedQuery<User> query = em.createQuery("SELECT DISTINCT u FROM UserV5 u", User.class);
        query.setHint("javax.persistence.fetchgraph", graph);

        List<User> list = query.getResultList();
        long end = System.currentTimeMillis();
        return new Result<User>(new TimeInterval(start, end), list);
    }

    @Override
    public Result<User> createTestData(int count) {
        long start = System.currentTimeMillis();
        List<User> resultList = new ArrayList<>();
        Set<UserV5> entities = new HashSet<>();
        for (int i = 0; i < count; i++) {
            UserV5 user = new UserV5();
            user.setName(RandomStringUtils.randomAlphabetic(20));
            user.setBirthdate(
                    DateUtils.addYears(new Date(), Integer.parseInt(RandomStringUtils.random(2, "123456789")) * -1));
            em.persist(user);

            createAddresses(user);
            resultList.add(user);
            entities.add(user);
        }
        createOrganizations(entities);
        long end = System.currentTimeMillis();
        return new Result<User>(new TimeInterval(start, end), resultList);
    }

    private void createOrganizations(Set<UserV5> users) {
        for (int i = 0; i < 100; i++) {
            OrganizationV5 org = new OrganizationV5();
            org.setUsers(users);
            org.setName(RandomStringUtils.randomAlphabetic(20));
            em.persist(org);
        }

    }

    private List<AddressV5> createAddresses(UserV5 user) {
        List<AddressV5> result = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            AddressV5 address = new AddressV5();
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
        TypedQuery<Address> query = em.createQuery("SELECT a FROM AddressV5 a WHERE a.user.id=:userid", Address.class);
        query.setParameter("userid", userId);

        List<Address> resultList = query.getResultList();
        long end = System.currentTimeMillis();
        return new Result<Address>(new TimeInterval(start, end), resultList);
    }

    @Override
    public Result<Organization> findAllOrganizations(Integer userId) {
        long start = System.currentTimeMillis();
        TypedQuery<Organization> query = em.createQuery("SELECT o FROM OrganizationV5 o WHERE :user MEMBER OF o.users",
                Organization.class);
        query.setParameter("user", em.find(UserV5.class, userId));

        List<Organization> resultList = query.getResultList();
        long end = System.currentTimeMillis();
        return new Result<Organization>(new TimeInterval(start, end), resultList);
    }

}
