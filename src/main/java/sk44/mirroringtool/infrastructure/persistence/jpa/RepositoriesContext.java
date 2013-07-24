/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sk44.mirroringtool.infrastructure.persistence.jpa;

import javax.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sk44.mirroringtool.domain.MirroringTaskRepository;

/**
 * Providing repositories and managing connections.
 *
 * @author sk
 */
public class RepositoriesContext implements AutoCloseable {

    private static final Logger logger = LoggerFactory.getLogger(RepositoriesContext.class);
    private EntityManager em;
    private boolean committed = false;

    public RepositoriesContext() {
        em = EntityManagerFactoryProvider.getFactory().createEntityManager();
        beginTransaction();
    }

    private void beginTransaction() {
        em.getTransaction().begin();
    }

    public void saveChanges() {
        em.getTransaction().commit();
        committed = true;
        logger.info("transaction commit succeeded.");
    }

    private void rollbackTransaction() {
        em.getTransaction().rollback();
        logger.info("transaction rollback succeeded.");
    }

    public MirroringTaskRepository createTaskRepository() {
        return new JpaTaskRepository(em);
    }

    @Override
    public void close() {
        if (em != null) {
            if (committed == false) {
                rollbackTransaction();
            }
            em.close();
        }
    }
}
