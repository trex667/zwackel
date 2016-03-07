package org.schreibvehler.complexV1;

import java.util.*;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.*;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.schreibvehler.boundary.*;
import org.schreibvehler.complexV1.OrganizationComplexV1.Type;

@Stateless
public class OrganizationStructureServiceComplexV1 {

    private static final Logger LOG = Logger.getLogger(OrganizationStructureServiceComplexV1.class.getName());

    @PersistenceContext
    private EntityManager em;

    public Result<OrganizationStructureComplexV1> getCompleteStructure() {
        long start = System.currentTimeMillis();

        EntityGraph<OrganizationStructureComplexV1> graph = em.createEntityGraph(OrganizationStructureComplexV1.class);
        graph.addAttributeNodes("parent");
        graph.addAttributeNodes("child");

        TypedQuery<OrganizationStructureComplexV1> query = em
                .createQuery("SELECT o FROM OrganizationStructureComplexV1 o", OrganizationStructureComplexV1.class);
        query.setHint("javax.persistence.fetchgraph", graph);

        List<OrganizationStructureComplexV1> list = query.getResultList();
        long end = System.currentTimeMillis();

        return new Result<OrganizationStructureComplexV1>(new TimeInterval(start, end), list);
    }

    public void createTestData(Integer amountOfOrganizations) {
        // DEVELOPMENT, PRODUCTION, MARKETING, FINANCE, HUMAN_RESOURCES,
        // ADMINISTRATION
        OrganizationComplexV1 facility = determineFacility();
        OrganizationComplexV1 development = createOrganization(facility, OrganizationComplexV1.Type.DEVELOPMENT, null,
                null, null);
        OrganizationComplexV1 production = createOrganization(facility, OrganizationComplexV1.Type.PRODUCTION, null,
                null, null);
        OrganizationComplexV1 marketing = createOrganization(facility, OrganizationComplexV1.Type.MARKETING, null, null,
                null);
        OrganizationComplexV1 finance = createOrganization(facility, OrganizationComplexV1.Type.FINANCE, null, null,
                null);
        OrganizationComplexV1 humanResources = createOrganization(facility, OrganizationComplexV1.Type.HUMAN_RESOURCES,
                null, null, null);
        OrganizationComplexV1 administration = createOrganization(facility, OrganizationComplexV1.Type.ADMINISTRATION,
                null, null, null);

        int countDevelopment = 5;
        int countMarketing = 3;
        int countFinance = 7;
        int countHumanResources = 15;
        int countAdministration = 5;
        int countProduction = amountOfOrganizations - 1 - countDevelopment - countMarketing - countFinance
                - countHumanResources - countAdministration;

        Date begin1 = DateUtils.addYears(facility.getBegin(), 10);
        Date end1 = null;
        Date begin2 = DateUtils.addYears(facility.getBegin(), 15);
        Date end2 = DateUtils.addYears(new Date(), 20);
        Date begin3 = facility.getBegin();
        Date end3 = DateUtils.addYears(facility.getBegin(), 20);

        for (int i = 0; i < countDevelopment; i++) {
            Date begin;
            Date end;
            if (i < 3) {
                begin = begin1;
                end = end1;
            } else if (i == 3) {
                begin = begin2;
                end = end2;
            } else if (i == 4) {
                begin = begin3;
                end = end3;
            } else {
                begin = facility.getBegin();
                end = facility.getEnd();
            }
            createOrganization(development, Type.DEPARTMENT, String.valueOf(i), begin, end);

        }

        for (int i = 0; i < countMarketing; i++) {
            Date begin;
            Date end;
            if (i == 0) {
                begin = begin1;
                end = end1;
            } else if (i == 1) {
                begin = begin2;
                end = end2;
            } else if (i == 2) {
                begin = begin3;
                end = end3;
            } else {
                begin = facility.getBegin();
                end = facility.getEnd();
            }
            createOrganization(marketing, Type.DEPARTMENT, String.valueOf(i), begin, end);

        }

        for (int i = 0; i < countFinance; i++) {
            Date begin;
            Date end;
            if (i < 2) {
                begin = begin1;
                end = end1;
            } else if (i == 2) {
                begin = begin2;
                end = end2;
            } else if (i == 3) {
                begin = begin3;
                end = end3;
            } else {
                begin = facility.getBegin();
                end = facility.getEnd();
            }
            createOrganization(finance, Type.DEPARTMENT, String.valueOf(i), begin, end);

        }

        for (int i = 0; i < countHumanResources; i++) {
            Date begin;
            Date end;
            if (i < 5) {
                begin = begin1;
                end = end1;
            } else if (i > 4 && i < 7) {
                begin = begin2;
                end = end2;
            } else if (i == 7) {
                begin = begin3;
                end = end3;
            } else {
                begin = facility.getBegin();
                end = facility.getEnd();
            }
            createOrganization(humanResources, Type.DEPARTMENT, String.valueOf(i), begin, end);
        }

        for (int i = 0; i < countAdministration; i++) {
            Date begin;
            Date end;
            if (i < 3) {
                begin = begin1;
                end = end1;
            } else if (i == 3) {
                begin = begin2;
                end = end2;
            } else if (i == 4) {
                begin = begin3;
                end = end3;
            } else {
                begin = facility.getBegin();
                end = facility.getEnd();
            }
            createOrganization(administration, Type.DEPARTMENT, String.valueOf(i), begin, end);

        }

        for (int i = 0; i < countProduction; i++) {
            Date begin;
            Date end;
            if (i < 5) {
                begin = begin1;
                end = end1;
            } else if (i > 4 && i < 7) {
                begin = begin2;
                end = end2;
            } else if (i > 6 && i < 12) {
                begin = begin3;
                end = end3;
            } else {
                begin = facility.getBegin();
                end = facility.getEnd();
            }
            createOrganization(production, Type.DEPARTMENT, String.valueOf(i), begin, end);
        }

    }

    private OrganizationComplexV1 createOrganization(OrganizationComplexV1 parent, Type type, String nameSuffix,
            Date begin, Date end) {
        Objects.requireNonNull(type);
        if (parent == null && type != Type.FACILITY) {
            throw new IllegalArgumentException();
        }

        OrganizationComplexV1 org = new OrganizationComplexV1();
        org.setType(type);
        org.setName(type.name() + (StringUtils.isNotBlank(nameSuffix) ? "_" + nameSuffix : ""));
        if (begin == null) {
            org.setBegin(parent.getBegin());
        } else {
            org.setBegin(begin);
        }
        org.setEnd(end);

        em.persist(org);

        if (parent != null) {
            createRelation(parent, org);
        }

        return org;
    }

    private void createRelation(OrganizationComplexV1 parent, OrganizationComplexV1 child) {
        OrganizationStructureComplexV1 relation = new OrganizationStructureComplexV1();
        relation.setParent(parent);
        relation.setChild(child);
        relation.setBegin(getMax(parent.getBegin(), child.getBegin()));
        relation.setEnd(getMin(parent.getEnd(), child.getEnd()));

        em.persist(relation);
    }

    private Date getMin(Date end, Date end2) {
        if (end == null && end2 == null) {
            return null;
        }
        if (end != null && end2 == null) {
            return end;
        }
        if (end == null && end2 != null) {
            return end2;
        }
        if (end.after(end2)) {
            return end2;
        }
        return end;
    }

    private Date getMax(Date begin, Date begin2) {
        if (begin.before(begin2)) {
            return begin2;
        }
        return begin;
    }

    private OrganizationComplexV1 determineFacility() {
        Collection<OrganizationComplexV1> facilities = findOrganizations(Type.FACILITY, null, new Date());
        if (!facilities.isEmpty()) {
            return facilities.stream().findFirst().get();
        }
        return createOrganization(null, Type.FACILITY, null, DateUtils.addYears(new Date(), -50), null);
    }

    private Collection<OrganizationComplexV1> findOrganizations(Type type, OrganizationComplexV1 parent, Date validAt) {
        Objects.requireNonNull(type);
        if (parent == null && type != Type.FACILITY) {
            throw new IllegalArgumentException();
        }
        Date currentDate = new Date();
        if (validAt != null) {
            currentDate = validAt;
        }
        String qlSelect;
        Set<OrganizationComplexV1> result = new HashSet<>();
        if (parent == null) {
            qlSelect = "SELECT o FROM OrganizationComplexV1 o WHERE o.type = :type AND o.begin <= :validAt AND (o.end IS NULL OR o.end >= :validAt)";
            TypedQuery<OrganizationComplexV1> query = em.createQuery(qlSelect, OrganizationComplexV1.class);
            query.setParameter("type", type);
            query.setParameter("validAt", currentDate);
            for (OrganizationComplexV1 element : query.getResultList()) {
                result.add(element);
            }
        } else {
            qlSelect = "SELECT o FROM OrganizationStructureComplexV1 o WHERE o.parent = :parent AND o.child.type = :type AND o.begin <= :validAt AND (o.end IS NULL OR o.end >= :validAt) AND o.child.begin <= :validAt AND (o.child.end IS NULL OR o.child.end >= :validAt)";
            TypedQuery<OrganizationStructureComplexV1> query = em.createQuery(qlSelect,
                    OrganizationStructureComplexV1.class);
            query.setParameter("parent", parent);
            query.setParameter("type", type);
            query.setParameter("validAt", currentDate);
            for (OrganizationStructureComplexV1 element : query.getResultList()) {
                result.add(element.getChild());
            }
        }
        return result;
    }
}
