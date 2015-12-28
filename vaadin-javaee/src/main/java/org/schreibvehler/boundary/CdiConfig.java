
package org.schreibvehler.boundary;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.persistence.*;

public class CdiConfig {

    /**
     * Exposes entity manager for CDI tools like DeltaSpike Data
     */
    @Produces
    @Dependent
    @PersistenceContext(unitName = "vaadin-javaee")
    public EntityManager entityManager;

}