package org.schreibvehler.v3;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
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
public class UserServiceV3 implements UserService
{

    private static final Logger LOG = Logger.getLogger(UserServiceV3.class.getName());

    @PersistenceContext
    private EntityManager em;


    @Override
    public Result<User> findAllUsers()
    {
        long start = System.currentTimeMillis();
        TypedQuery<User> query = em.createQuery("SELECT u FROM UserV3 u LEFT JOIN FETCH u.addresses LEFT JOIN FETCH u.organizations", User.class);

        List<User> list = query.getResultList();

        long end = System.currentTimeMillis();
        return new Result<User>(new TimeInterval(start, end), list);
    }


    @Override
    public Result<User> createTestData(int count)
    {
        long start = System.currentTimeMillis();
        List<User> resultList = new ArrayList<>();
        List<UserV3> entities = new ArrayList<>();
        for (int i = 0; i < count; i++)
        {
            UserV3 user = new UserV3();
            user.setName(RandomStringUtils.randomAlphabetic(20));
            user.setBirthdate(DateUtils.addYears(new Date(), Integer.parseInt(RandomStringUtils.random(2, "123456789")) * -1));
            em.persist(user);

            createAddresses(user);
            resultList.add(user);
            entities.add(user);
        }
        createOrganizations(entities);
        long end = System.currentTimeMillis();
        return new Result<User>(new TimeInterval(start, end), resultList);
    }


    private void createOrganizations(List<UserV3> users)
    {
        for (int i = 0; i < 100; i++)
        {
            OrganizationV3 org = new OrganizationV3();
            org.setUsers(users);
            org.setName(RandomStringUtils.randomAlphabetic(20));
            em.persist(org);
        }

    }


    private List<AddressV3> createAddresses(UserV3 user)
    {
        List<AddressV3> result = new ArrayList<>();

        for (int i = 0; i < 10; i++)
        {
            AddressV3 address = new AddressV3();
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
        TypedQuery<Address> query = em.createQuery("SELECT a FROM AddressV3 a WHERE a.user.id=:userid", Address.class);
        query.setParameter("userid", userId);

        List<Address> resultList = query.getResultList();
        long end = System.currentTimeMillis();
        return new Result<Address>(new TimeInterval(start, end), resultList);
    }


    @Override
    public Result<Organization> findAllOrganizations(Integer userId)
    {
        long start = System.currentTimeMillis();
        TypedQuery<Organization> query = em.createQuery("SELECT o FROM OrganizationV3 o WHERE :user MEMBER OF o.users", Organization.class);
        query.setParameter("user", em.find(UserV3.class, userId));

        List<Organization> resultList = query.getResultList();
        long end = System.currentTimeMillis();
        return new Result<Organization>(new TimeInterval(start, end), resultList);
    }

}
