/*
 */
package sk44.mirroringtool.infrastructure.persistence.jpa;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import sk44.mirroringtool.domain.MirroringTask;
import sk44.mirroringtool.domain.MirroringTaskRepository;

/**
 * MirroringTask repository implementation with JPA.
 *
 * @author sk
 */
public class JpaTaskRepository implements MirroringTaskRepository {

    private final EntityManager em;

    public JpaTaskRepository(EntityManager em) {
        this.em = em;
    }

    public JpaTaskRepository() {
        this(EntityManagerFactoryProvider.getFactory().createEntityManager());
    }

    @Override
    public void add(MirroringTask task) {
        em.persist(task);
    }

    @Override
    public void merge(MirroringTask task) {
        em.merge(task);
    }

    @Override
    public void remove(MirroringTask task) {
        em.remove(matches(task.getId()));
    }

    @Override
    public MirroringTask matches(Long id) {
        TypedQuery<MirroringTask> q = em.createQuery(
            "SELECT t FROM MirroringTask t WHERE t.id = :id",
            MirroringTask.class);
        List<MirroringTask> founds = q.setParameter("id", id).getResultList();
        return founds.get(0);
    }

    @Override
    public List<MirroringTask> all() {
        return em.createQuery("select t from MirroringTask t", MirroringTask.class).getResultList();
    }
}
