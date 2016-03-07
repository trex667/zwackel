package org.schreibvehler.complexV1;

import java.util.*;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.*;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.schreibvehler.boundary.*;

@Stateless
public class UserServiceComplexV1 implements UserService {

    private static final Logger LOG = Logger.getLogger(UserServiceComplexV1.class.getName());

    @PersistenceContext
    private EntityManager em;

    @Inject
    private OrganizationStructureServiceComplexV1 orgStructureService;

    @Override
    public Result<User> findAllUsers() {
        long start = System.currentTimeMillis();
        TypedQuery<User> query = em.createQuery("SELECT u FROM UserComplexV1 u", User.class);

        List<User> list = query.getResultList();
        long end = System.currentTimeMillis();
        return new Result<User>(new TimeInterval(start, end), list);
    }

    @Override
    public Result<User> createTestData(int count) {
        long start = System.currentTimeMillis();
        List<User> resultList = new ArrayList<>();
        Set<UserComplexV1> entities = new HashSet<>();
        for (int i = 0; i < count; i++) {
            UserComplexV1 user = new UserComplexV1();
            user.setName(RandomStringUtils.randomAlphabetic(20));
            user.setBirthdate(
                    DateUtils.addYears(new Date(), Integer.parseInt(RandomStringUtils.random(2, "123456789")) * -1));
            em.persist(user);

            createAddresses(user);
            resultList.add(user);
            entities.add(user);
        }
        TypedQuery<OrganizationComplexV1> organizations = em.createQuery("SELECT  o FROM OrganizationComplexV1 o",
                OrganizationComplexV1.class);
        if (organizations.getResultList() == null || organizations.getResultList().size() == 0) {
            createComplexOrganizationStructure(100);
        }
        // TODO link user somehow with some departments
        // addToOrganizations(entities);
        long end = System.currentTimeMillis();
        return new Result<User>(new TimeInterval(start, end), resultList);
    }

    private void addToOrganizations(Set<UserComplexV1> users) {
        String select = "SELECT o FROM OrganizationV1 o";
        TypedQuery<OrganizationComplexV1> organizations = em.createQuery(select, OrganizationComplexV1.class);
        if (organizations.getResultList() == null || organizations.getResultList().size() == 0) {
            createComplexOrganizationStructure(100);
        }
        organizations = em.createQuery(select, OrganizationComplexV1.class);
        for (OrganizationComplexV1 org : organizations.getResultList()) {
            org.addUsers(users);
            em.merge(org);
        }

    }

    private void createComplexOrganizationStructure(int amountOfOrganizations) {
        orgStructureService.createTestData(amountOfOrganizations);
    }

    private List<AddressComplexV1> createAddresses(UserComplexV1 user) {
        List<AddressComplexV1> result = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            AddressComplexV1 address = new AddressComplexV1();
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
        TypedQuery<Address> query = em.createQuery("SELECT a FROM AddressComplexV1 a WHERE a.user.id=:userid",
                Address.class);
        query.setParameter("userid", userId);

        List<Address> resultList = query.getResultList();
        long end = System.currentTimeMillis();
        return new Result<Address>(new TimeInterval(start, end), resultList);
    }

    @Override
    public Result<Organization> findAllOrganizations(Integer userId) {
        long start = System.currentTimeMillis();
        TypedQuery<Organization> query = em
                .createQuery("SELECT o FROM OrganizationComplexV1 o WHERE :user MEMBER OF o.users", Organization.class);
        query.setParameter("user", em.find(UserComplexV1.class, userId));

        List<Organization> resultList = query.getResultList();
        long end = System.currentTimeMillis();
        return new Result<Organization>(new TimeInterval(start, end), resultList);
    }

}
