/*
 */
package sk44.mirroringtool.application;

import javax.persistence.EntityManager;
import sk44.mirroringtool.domain.Task;
import sk44.mirroringtool.infrastructure.persistence.jpa.EntityManagerFactoryProvider;
import sk44.mirroringtool.infrastructure.persistence.jpa.JpaTaskRepository;

/**
 * Application service.
 *
 * @author sk
 */
public class TaskService {

    public Task findBy(Long taskId) {
        return new JpaTaskRepository().matches(taskId);
    }

    public void merge(Task task) {
        EntityManager em = EntityManagerFactoryProvider.getFactory().createEntityManager();
        em.getTransaction().begin();
        new JpaTaskRepository(em).merge(task);
        em.getTransaction().commit();
    }

    public void delete(Task task) {
        EntityManager em = EntityManagerFactoryProvider.getFactory().createEntityManager();
        em.getTransaction().begin();
        new JpaTaskRepository(em).remove(task);
        em.getTransaction().commit();
    }
}
