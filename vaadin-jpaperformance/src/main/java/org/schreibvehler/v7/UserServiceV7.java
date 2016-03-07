package org.schreibvehler.v7;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
public class UserServiceV7 implements UserService
{

    private static final Logger LOG = Logger.getLogger(UserServiceV7.class.getName());

    @PersistenceContext
    private EntityManager em;


    @Override
    public Result<User> findAllUsers()
    {
        long start = System.currentTimeMillis();
        Query query = em.createNativeQuery("SELECT * FROM USERV7", UserV7.class);
        List<User> list = query.getResultList();
        long end = System.currentTimeMillis();
        return new Result<User>(new TimeInterval(start, end), list);
    }


    @Override
    public Result<User> createTestData(int count)
    {
        long start = System.currentTimeMillis();
        List<User> resultList = new ArrayList<>();
        Set<UserV7> entities = new HashSet<>();
        for (int i = 0; i < count; i++)
        {
            UserV7 user = new UserV7();
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


    private void addToOrganizations(Set<UserV7> users)
    {
        String select = "SELECT o FROM OrganizationV7 o";
        TypedQuery<OrganizationV7> organizations = em.createQuery(select, OrganizationV7.class);
        if (organizations.getResultList() == null || organizations.getResultList().size() == 0)
        {
            createOrganizations(100);
        }
        organizations = em.createQuery(select, OrganizationV7.class);
        for (OrganizationV7 org : organizations.getResultList())
        {
            org.addUsers(users);
            em.merge(org);
        }

    }


    private void createOrganizations(int amount)
    {
        for (int i = 0; i < amount; i++)
        {
            OrganizationV7 org = new OrganizationV7();
            org.setName(RandomStringUtils.randomAlphabetic(20));
            em.persist(org);
        }
    }


    private List<AddressV7> createAddresses(UserV7 user)
    {
        List<AddressV7> result = new ArrayList<>();

        for (int i = 0; i < 10; i++)
        {
            AddressV7 address = new AddressV7();
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
        Query query = em.createNativeQuery("SELECT * FROM AddressV7 WHERE user_id=:userid", AddressV7.class);
        query.setParameter("userid", userId);

        List<Address> resultList = query.getResultList();
        long end = System.currentTimeMillis();
        return new Result<Address>(new TimeInterval(start, end), resultList);
    }


    @Override
    public Result<Organization> findAllOrganizations(Integer userId)
    {
        long start = System.currentTimeMillis();
        Query query = em.createNativeQuery("SELECT org.* FROM OrganizationV7 org LEFT OUTER JOIN ORGANIZATIONV7_USERV7 org_user ON org.id = org_user.organizations_id  WHERE org_user.users_id = :user",
                                           OrganizationV7.class);
        query.setParameter("user", userId);

        List<Organization> resultList = query.getResultList();
        long end = System.currentTimeMillis();
        return new Result<Organization>(new TimeInterval(start, end), resultList);
    }

}
