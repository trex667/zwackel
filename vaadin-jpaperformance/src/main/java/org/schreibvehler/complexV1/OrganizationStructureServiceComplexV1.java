package org.schreibvehler.complexV1;


import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.schreibvehler.boundary.Result;
import org.schreibvehler.boundary.TimeInterval;


@Stateless
public class OrganizationStructureServiceComplexV1
{

    private static final Logger LOG = Logger.getLogger(OrganizationStructureServiceComplexV1.class.getName());

    @PersistenceContext
    private EntityManager em;


    public Result<OrganizationStructureComplexV1> getCompleteStructure()
    {
        long start = System.currentTimeMillis();

        EntityGraph<OrganizationStructureComplexV1> graph = em.createEntityGraph(OrganizationStructureComplexV1.class);
        graph.addAttributeNodes("parent");
        graph.addAttributeNodes("child");

        TypedQuery<OrganizationStructureComplexV1> query = em.createQuery("SELECT o FROM OrganizationStructureComplexV1 o",
                                                                          OrganizationStructureComplexV1.class);
        query.setHint("javax.persistence.fetchgraph", graph);

        List<OrganizationStructureComplexV1> list = query.getResultList();
        long end = System.currentTimeMillis();

        return new Result<OrganizationStructureComplexV1>(new TimeInterval(start, end), list);
    }
}
