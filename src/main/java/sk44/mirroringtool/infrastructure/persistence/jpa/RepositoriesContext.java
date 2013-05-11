/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sk44.mirroringtool.infrastructure.persistence.jpa;

import javax.persistence.EntityManager;
import sk44.mirroringtool.domain.TaskRepository;

/**
 * Providing repositories and managing connections.
 *
 * @author sk
 */
public class RepositoriesContext implements AutoCloseable {

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
    }

    private void rollbackTransaction() {
        em.getTransaction().rollback();
    }

    public TaskRepository createTaskRepository() {
        return new JpaTaskRepository(em);
    }

    @Override
    public void close() throws Exception {
        if (em != null) {
            if (committed == false) {
                rollbackTransaction();
            }
            em.close();
        }
    }
}
