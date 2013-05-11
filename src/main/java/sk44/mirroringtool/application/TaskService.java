/*
 */
package sk44.mirroringtool.application;

import sk44.mirroringtool.domain.Task;
import sk44.mirroringtool.domain.TaskProcessingDetail;
import sk44.mirroringtool.domain.TaskProcessingType;
import sk44.mirroringtool.infrastructure.persistence.jpa.RepositoriesContext;
import sk44.mirroringtool.util.Action;

/**
 * Application service.
 *
 * @author sk
 */
public class TaskService {

    public Task findBy(Long taskId) {
        try (RepositoriesContext context = new RepositoriesContext()) {
            return context.createTaskRepository().matches(taskId);
        } catch (Exception e) {
            // AutoCloseable は検査例外をスローするので、 catch するしかない・・・
            throw new RuntimeException(e);
        }
    }

    public void merge(Task task) {
        try (RepositoriesContext context = new RepositoriesContext()) {
            context.createTaskRepository().merge(task);
            context.saveChanges();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(Task task) {
        try (RepositoriesContext context = new RepositoriesContext()) {
            context.createTaskRepository().remove(task);
            context.saveChanges();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
