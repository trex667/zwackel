package org.schreibvehler.complexV1;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.schreibvehler.boundary.Address;
import org.schreibvehler.boundary.DataUtils;
import org.schreibvehler.boundary.Organization;
import org.schreibvehler.boundary.Result;
import org.schreibvehler.boundary.TimeInterval;
import org.schreibvehler.boundary.User;
import org.schreibvehler.boundary.UserService;
import org.schreibvehler.complexV1.OrganizationComplexV1.Type;


@Stateless
public class UserServiceComplexV1 implements UserService
{

    private static final Logger LOG = Logger.getLogger(UserServiceComplexV1.class.getName());

    @PersistenceContext
    private EntityManager em;


    @Override
    public Result<User> findAllUsers()
    {
        long start = System.currentTimeMillis();
        TypedQuery<User> query = em.createQuery("SELECT u FROM UserComplexV1 u", User.class);

        List<User> list = query.getResultList();
        long end = System.currentTimeMillis();
        return new Result<User>(new TimeInterval(start, end), list);
    }


    @Override
    public Result<User> createTestData(int count)
    {
        long start = System.currentTimeMillis();
        List<User> resultList = new ArrayList<>();
        Set<UserComplexV1> entities = new HashSet<>();
        for (int i = 0; i < count; i++)
        {
            UserComplexV1 user = new UserComplexV1();
            user.setName(RandomStringUtils.randomAlphabetic(20));
            user.setBirthdate(DateUtils.addYears(new Date(), Integer.parseInt(RandomStringUtils.random(2, "123456789")) * -1));
            em.persist(user);

            createAddresses(user);
            resultList.add(user);
            entities.add(user);
        }
        TypedQuery<OrganizationComplexV1> organizations = em.createQuery("SELECT  o FROM OrganizationComplexV1 o", OrganizationComplexV1.class);
        if (organizations.getResultList() == null || organizations.getResultList().size() == 0)
        {
            createComplexOrganizationStructure(100);
        }
        //TODO link user somehow with some departments
//        addToOrganizations(entities);
        long end = System.currentTimeMillis();
        return new Result<User>(new TimeInterval(start, end), resultList);
    }


    private void addToOrganizations(Set<UserComplexV1> users)
    {
        String select = "SELECT o FROM OrganizationV1 o";
        TypedQuery<OrganizationComplexV1> organizations = em.createQuery(select, OrganizationComplexV1.class);
        if (organizations.getResultList() == null || organizations.getResultList().size() == 0)
        {
            createComplexOrganizationStructure(100);
        }
        organizations = em.createQuery(select, OrganizationComplexV1.class);
        for (OrganizationComplexV1 org : organizations.getResultList())
        {
            org.addUsers(users);
            em.merge(org);
        }

    }


    private void createComplexOrganizationStructure(int amountOfOrganizations)
    {
        // DEVELOPMENT, PRODUCTION, MARKETING, FINANCE, HUMAN_RESOURCES, ADMINISTRATION
        OrganizationComplexV1 facility = createFacility();
        OrganizationComplexV1 development = createOrganization(facility, OrganizationComplexV1.Type.DEVELOPMENT, null, null, null);
        OrganizationComplexV1 production = createOrganization(facility, OrganizationComplexV1.Type.PRODUCTION, null, null, null);
        OrganizationComplexV1 marketing = createOrganization(facility, OrganizationComplexV1.Type.MARKETING, null, null, null);
        OrganizationComplexV1 finance = createOrganization(facility, OrganizationComplexV1.Type.FINANCE, null, null, null);
        OrganizationComplexV1 humanResources = createOrganization(facility, OrganizationComplexV1.Type.HUMAN_RESOURCES, null, null, null);
        OrganizationComplexV1 administration = createOrganization(facility, OrganizationComplexV1.Type.ADMINISTRATION, null, null, null);

        int countDevelopment = 5;
        int countMarketing = 3;
        int countFinance = 7;
        int countHumanResources = 15;
        int countAdministration = 5;
        int countProduction = amountOfOrganizations - 1 - countDevelopment - countMarketing - countFinance - countHumanResources
                              - countAdministration;

        Date begin1 = DateUtils.addYears(facility.getBegin(), 10);
        Date end1 = null;
        Date begin2 = DateUtils.addYears(facility.getBegin(), 15);
        Date end2 = DateUtils.addYears(new Date(), 20);
        Date begin3 = facility.getBegin();
        Date end3 = DateUtils.addYears(facility.getBegin(), 20);

        for (int i = 0; i < countDevelopment; i++)
        {
            Date begin;
            Date end;
            if (i < 3)
            {
                begin = begin1;
                end = end1;
            }
            else if (i == 3)
            {
                begin = begin2;
                end = end2;
            }
            else if (i == 4)
            {
                begin = begin3;
                end = end3;
            }
            else
            {
                begin = facility.getBegin();
                end = facility.getEnd();
            }
            createOrganization(development, Type.DEPARTMENT, String.valueOf(i), begin, end);

        }

        for (int i = 0; i < countMarketing; i++)
        {
            Date begin;
            Date end;
            if (i == 0)
            {
                begin = begin1;
                end = end1;
            }
            else if (i == 1)
            {
                begin = begin2;
                end = end2;
            }
            else if (i == 2)
            {
                begin = begin3;
                end = end3;
            }
            else
            {
                begin = facility.getBegin();
                end = facility.getEnd();
            }
            createOrganization(marketing, Type.DEPARTMENT, String.valueOf(i), begin, end);

        }

        for (int i = 0; i < countFinance; i++)
        {
            Date begin;
            Date end;
            if (i < 2)
            {
                begin = begin1;
                end = end1;
            }
            else if (i == 2)
            {
                begin = begin2;
                end = end2;
            }
            else if (i == 3)
            {
                begin = begin3;
                end = end3;
            }
            else
            {
                begin = facility.getBegin();
                end = facility.getEnd();
            }
            createOrganization(finance, Type.DEPARTMENT, String.valueOf(i), begin, end);

        }

        for (int i = 0; i < countHumanResources; i++)
        {
            Date begin;
            Date end;
            if (i < 5)
            {
                begin = begin1;
                end = end1;
            }
            else if (i > 4 && i < 7)
            {
                begin = begin2;
                end = end2;
            }
            else if (i == 7)
            {
                begin = begin3;
                end = end3;
            }
            else
            {
                begin = facility.getBegin();
                end = facility.getEnd();
            }
            createOrganization(humanResources, Type.DEPARTMENT, String.valueOf(i), begin, end);
        }

        for (int i = 0; i < countAdministration; i++)
        {
            Date begin;
            Date end;
            if (i < 3)
            {
                begin = begin1;
                end = end1;
            }
            else if (i == 3)
            {
                begin = begin2;
                end = end2;
            }
            else if (i == 4)
            {
                begin = begin3;
                end = end3;
            }
            else
            {
                begin = facility.getBegin();
                end = facility.getEnd();
            }
            createOrganization(administration, Type.DEPARTMENT, String.valueOf(i), begin, end);

        }

        for (int i = 0; i < countProduction; i++)
        {
            Date begin;
            Date end;
            if (i < 5)
            {
                begin = begin1;
                end = end1;
            }
            else if (i > 4 && i < 7)
            {
                begin = begin2;
                end = end2;
            }
            else if (i > 6 && i < 12)
            {
                begin = begin3;
                end = end3;
            }
            else
            {
                begin = facility.getBegin();
                end = facility.getEnd();
            }
            createOrganization(production, Type.DEPARTMENT, String.valueOf(i), begin, end);
        }

    }


    private OrganizationComplexV1 createOrganization(OrganizationComplexV1 parent, Type type, String nameSuffix, Date begin, Date end)
    {
        Objects.requireNonNull(type);
        if (parent == null && type != Type.FACILITY)
        {
            throw new IllegalArgumentException();
        }

        OrganizationComplexV1 org = new OrganizationComplexV1();
        org.setType(type);
        org.setName(type.name() + (StringUtils.isNotBlank(nameSuffix) ? "_" + nameSuffix : ""));
        if (begin == null)
        {
            org.setBegin(parent.getBegin());
        }
        else
        {
            org.setBegin(begin);
        }
        org.setEnd(end);

        em.persist(org);

        if (parent != null)
        {
            createRelation(parent, org);
        }

        return org;
    }


    private void createRelation(OrganizationComplexV1 parent, OrganizationComplexV1 child)
    {
        OrganizationStructureComplexV1 relation = new OrganizationStructureComplexV1();
        relation.setParent(parent);
        relation.setChild(child);
        relation.setBegin(getMax(parent.getBegin(), child.getBegin()));
        relation.setEnd(getMin(parent.getEnd(), child.getEnd()));

        em.persist(relation);
    }


    private Date getMin(Date end, Date end2)
    {
        if (end == null && end2 == null)
        {
            return null;
        }
        if (end != null && end2 == null)
        {
            return end;
        }
        if (end == null && end2 != null)
        {
            return end2;
        }
        if (end.after(end2))
        {
            return end2;
        }
        return end;
    }


    private Date getMax(Date begin, Date begin2)
    {
        if (begin.before(begin2))
        {
            return begin2;
        }
        return begin;
    }


    private OrganizationComplexV1 createFacility()
    {
        return createOrganization(null, Type.FACILITY, null, DateUtils.addYears(new Date(), -50), null);
    }


    private List<AddressComplexV1> createAddresses(UserComplexV1 user)
    {
        List<AddressComplexV1> result = new ArrayList<>();

        for (int i = 0; i < 10; i++)
        {
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
    public Result<Address> findAllAddresses(Integer userId)
    {
        long start = System.currentTimeMillis();
        TypedQuery<Address> query = em.createQuery("SELECT a FROM AddressComplexV1 a WHERE a.user.id=:userid", Address.class);
        query.setParameter("userid", userId);

        List<Address> resultList = query.getResultList();
        long end = System.currentTimeMillis();
        return new Result<Address>(new TimeInterval(start, end), resultList);
    }


    @Override
    public Result<Organization> findAllOrganizations(Integer userId)
    {
        long start = System.currentTimeMillis();
        TypedQuery<Organization> query = em.createQuery("SELECT o FROM OrganizationComplexV1 o WHERE :user MEMBER OF o.users", Organization.class);
        query.setParameter("user", em.find(UserComplexV1.class, userId));

        List<Organization> resultList = query.getResultList();
        long end = System.currentTimeMillis();
        return new Result<Organization>(new TimeInterval(start, end), resultList);
    }

}
