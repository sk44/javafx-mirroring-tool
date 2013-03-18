/*
 */
package sk44.mirroringtool.infrastructure.persistence.jpa;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import sk44.mirroringtool.domain.Task;
import sk44.mirroringtool.domain.TaskRepository;

/**
 * Task repository implementation with JPA.
 *
 * @author sk
 */
public class JpaTaskRepository implements TaskRepository {

    private final EntityManager em;

    public JpaTaskRepository(EntityManager em) {
        this.em = em;
    }

    public JpaTaskRepository() {
        this(EntityManagerFactoryProvider.getFactory().createEntityManager());
    }

    @Override
    public void add(Task task) {
        em.persist(task);
    }

    @Override
    public void merge(Task task) {
        em.merge(task);
    }

    @Override
    public Task matches(Long id) {
        Query q = em.createQuery("SELECT t FROM Task t WHERE t.id = :id", Task.class);
        List<Task> founds = q.setParameter("id", id).getResultList();
        return founds.get(0);
    }

    @Override
    public List<Task> all() {
        return em.createQuery("select t from Task t", Task.class).getResultList();
    }
}
