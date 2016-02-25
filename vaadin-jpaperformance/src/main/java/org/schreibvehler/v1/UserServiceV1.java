package org.schreibvehler.v1;


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
public class UserServiceV1 implements UserService
{

    private static final Logger LOG = Logger.getLogger(UserServiceV1.class.getName());

    @PersistenceContext
    private EntityManager em;


    @Override
    public Result<User> findAllUsers()
    {
        long start = System.currentTimeMillis();
        TypedQuery<User> query = em.createQuery("SELECT u FROM UserV1 u", User.class);

        List<User> list = query.getResultList();
        long end = System.currentTimeMillis();
        return new Result<User>(new TimeInterval(start, end), list);
    }


    @Override
    public Result<User> createTestData(int count)
    {
        long start = System.currentTimeMillis();
        List<User> resultList = new ArrayList<>();
        List<UserV1> entities = new ArrayList<>();
        for (int i = 0; i < count; i++)
        {
            UserV1 user = new UserV1();
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


    private void createOrganizations(List<UserV1> users)
    {
        for (int i = 0; i < 100; i++)
        {
            OrganizationV1 org = new OrganizationV1();
            org.setUsers(users);
            org.setName(RandomStringUtils.randomAlphabetic(20));
            em.persist(org);
        }

    }


    private List<OrganizationV1> getOrganizations()
    {
        TypedQuery<OrganizationV1> query = em.createQuery("Select o from OrganizationV1 o", OrganizationV1.class);
        return query.getResultList();
    }


    private List<AddressV1> createAddresses(UserV1 user)
    {
        List<AddressV1> result = new ArrayList<>();

        for (int i = 0; i < 10; i++)
        {
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
    public Result<Address> findAllAddresses(Integer userId)
    {
        long start = System.currentTimeMillis();
        TypedQuery<Address> query = em.createQuery("SELECT a FROM AddressV1 a WHERE a.user.id=:userid", Address.class);
        query.setParameter("userid", userId);

        List<Address> resultList = query.getResultList();
        long end = System.currentTimeMillis();
        return new Result<Address>(new TimeInterval(start, end), resultList);
    }


    @Override
    public Result<Organization> findAllOrganizations(Integer userId)
    {
        long start = System.currentTimeMillis();
        TypedQuery<Organization> query = em.createQuery("SELECT o FROM OrganizationV1 o WHERE :user MEMBER OF o.users", Organization.class);
        query.setParameter("user", em.find(UserV1.class, userId));

        List<Organization> resultList = query.getResultList();
        long end = System.currentTimeMillis();
        return new Result<Organization>(new TimeInterval(start, end), resultList);
    }

}
