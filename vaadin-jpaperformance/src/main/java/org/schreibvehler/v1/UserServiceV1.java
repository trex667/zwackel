package org.schreibvehler.v1;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.schreibvehler.boundary.DataUtils;
import org.schreibvehler.boundary.User;
import org.schreibvehler.boundary.UserService;


@Stateless
public class UserServiceV1 implements UserService
{

    @PersistenceContext
    private EntityManager em;


    @Override
    public List<User> findAllUsers()
    {
        TypedQuery<User> query = em.createQuery("SELECT u FROM UserV1 u", User.class);

        return query.getResultList();
    }


    @Override
    public List<User> createTestData(int count)
    {
        List<User> result = new ArrayList<>();
        createOrganizations();
        for (int i = 0; i < count; i++)
        {
            UserV1 user = new UserV1();
            user.setName(RandomStringUtils.randomAlphabetic(20));
            user.setBirthdate(DateUtils.addYears(new Date(), Integer.parseInt(RandomStringUtils.random(2, "123456789")) * -1));
            user.setAddresses(createAddresses());
            user.setOrganizations(getOrganizations());
            em.persist(user);

            result.add(user);
        }
        return result;
    }


    private void createOrganizations()
    {
        for (int i = 0; i < 100; i++)
        {
            OrganizationV1 org = new OrganizationV1();
            org.setName(RandomStringUtils.randomAlphabetic(20));
            em.persist(org);
        }

    }


    private List<OrganizationV1> getOrganizations()
    {
        TypedQuery<OrganizationV1> query = em.createQuery("Select o from OrganizationV1 o", OrganizationV1.class);
        return query.getResultList();
    }


    private List<AddressV1> createAddresses()
    {
        List<AddressV1> result = new ArrayList<>();

        for (int i = 0; i < 10; i++)
        {
            AddressV1 address = new AddressV1();
            address.setCity(DataUtils.getRandomCity());
            address.setCountry("Germany");
            address.setStreet(RandomStringUtils.randomAlphabetic(14));
            address.setPostCode(new Integer(RandomStringUtils.random(5, "123456789")));
            em.persist(address);
            result.add(address);
        }
        return result;
    }

}
