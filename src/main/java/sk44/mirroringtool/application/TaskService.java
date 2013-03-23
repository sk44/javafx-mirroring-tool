/*
 */
package sk44.mirroringtool.application;

import javax.persistence.EntityManager;
import sk44.mirroringtool.domain.Task;
import sk44.mirroringtool.domain.TaskProcessingDetail;
import sk44.mirroringtool.domain.TaskProcessingType;
import sk44.mirroringtool.infrastructure.persistence.jpa.EntityManagerFactoryProvider;
import sk44.mirroringtool.infrastructure.persistence.jpa.JpaTaskRepository;
import sk44.mirroringtool.util.Action;

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

    public void execute(Long taskId) {
        // TODO
    }

    public void executeAll() {
        // TODO
    }

    public void test(Long taskId, Action<TaskProcessingDetail> processingDetailsHandler) {
        // TODO
        for (int i = 0; i < 10; i++) {
            System.out.println(i);
            processingDetailsHandler.execute(new TaskProcessingDetail(i % 3 == 0 ? TaskProcessingType.CREATE : TaskProcessingType.UPDATE, null, null, null));
        }
    }
}
