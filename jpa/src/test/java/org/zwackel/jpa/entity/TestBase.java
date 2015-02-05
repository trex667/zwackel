package org.zwackel.jpa.entity;

import javax.persistence.*;

import org.junit.*;
import org.slf4j.*;

public abstract class TestBase
{
    protected static EntityManagerFactory entityManagerFactory;
    protected EntityManager entityManager;

    Logger LOG = LoggerFactory.getLogger(getClass());

    protected synchronized EntityManagerFactory getEntityManagerFactory() {
        if (entityManagerFactory == null)
        {
            LOG.debug("create entitymanager factory");
            entityManagerFactory = Persistence.createEntityManagerFactory("zwackel");
        }
        return entityManagerFactory;
    }

    @Before
    public void before() {
        LOG.debug("create entitymanager and start transaction");
        entityManager = getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();
    }

    @After
    public void after() throws Exception {
        try {
            EntityTransaction transaction = entityManager.getTransaction();
            if (transaction.isActive()) {
                if (!transaction.getRollbackOnly()) {
                    LOG.debug("commit transaction");
                    transaction.commit();
                } else {
                    LOG.debug("rollback transaction");
                    transaction.rollback();
                }
            }
        } finally {
            try {
                LOG.debug("close entitymanager");
                entityManager.close();
            } catch (Exception ignore) {
            }
        }
    }

}
