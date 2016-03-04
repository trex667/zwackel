package org.schreibvehler.v6;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.schreibvehler.boundary.Address;
import org.schreibvehler.boundary.DataUtils;
import org.schreibvehler.boundary.Organization;
import org.schreibvehler.boundary.Result;
import org.schreibvehler.boundary.TimeInterval;
import org.schreibvehler.boundary.User;
import org.schreibvehler.boundary.UserService;


@Stateless
public class UserServiceV6 implements UserService
{

    private static final Logger LOG = Logger.getLogger(UserServiceV6.class.getName());

    @PersistenceContext
    private EntityManager em;


    @Override
    public Result<User> findAllUsers()
    {
        long start = System.currentTimeMillis();

        EntityGraph<UserV6> graph = em.createEntityGraph(UserV6.class);
        graph.addAttributeNodes("organizations");
        TypedQuery<User> query = em.createQuery("SELECT DISTINCT u FROM UserV6 u", User.class);
        query.setHint("javax.persistence.fetchgraph", graph);

        List<User> list = query.getResultList();
        long end = System.currentTimeMillis();
        return new Result<User>(new TimeInterval(start, end), list);
    }


    @Override
    public Result<User> createTestData(int count)
    {
        long start = System.currentTimeMillis();
        List<User> resultList = new ArrayList<>();
        Set<UserV6> entities = new HashSet<>();
        for (int i = 0; i < count; i++)
        {
            UserV6 user = new UserV6();
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


    private void addToOrganizations(Set<UserV6> users)
    {
        String select = "SELECT o FROM OrganizationV6 o";
        TypedQuery<OrganizationV6> organizations = em.createQuery(select, OrganizationV6.class);
        if (organizations.getResultList() == null || organizations.getResultList().size() == 0)
        {
            createOrganizations(100);
        }
        organizations = em.createQuery(select, OrganizationV6.class);
        for (OrganizationV6 org : organizations.getResultList())
        {
            org.addUsers(users);
            em.merge(org);
        }

    }


    private void createOrganizations(int amount)
    {
        for (int i = 0; i < amount; i++)
        {
            OrganizationV6 org = new OrganizationV6();
            org.setName(RandomStringUtils.randomAlphabetic(20));
            em.persist(org);
        }
    }


    private List<AddressV6> createAddresses(UserV6 user)
    {
        List<AddressV6> result = new ArrayList<>();

        for (int i = 0; i < 10; i++)
        {
            AddressV6 address = new AddressV6();
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
    public Result<Address> findAllAddresses(Integer userId)
    {
        long start = System.currentTimeMillis();
        TypedQuery<Address> query = em.createQuery("SELECT a FROM AddressV6 a WHERE a.user.id=:userid", Address.class);
        query.setParameter("userid", userId);

        List<Address> resultList = query.getResultList();
        long end = System.currentTimeMillis();
        return new Result<Address>(new TimeInterval(start, end), resultList);
    }


    @Override
    public Result<Organization> findAllOrganizations(Integer userId)
    {
        long start = System.currentTimeMillis();
        TypedQuery<Organization> query = em.createQuery("SELECT o FROM OrganizationV6 o WHERE :user MEMBER OF o.users", Organization.class);
        query.setParameter("user", em.find(UserV6.class, userId));

        List<Organization> resultList = query.getResultList();
        long end = System.currentTimeMillis();
        return new Result<Organization>(new TimeInterval(start, end), resultList);
    }

}
