/*
 */
package sk44.mirroringtool.application;

import sk44.mirroringtool.domain.Task;
import sk44.mirroringtool.domain.TaskProcessingDetail;
import sk44.mirroringtool.domain.TaskRepository;
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
        try (RepositoriesContext context = new RepositoriesContext()) {
            TaskRepository repos = context.createTaskRepository();
            Task task = repos.matches(taskId);
            if (task == null) {
                // TODO
                return;
            }
            task.test(processingDetailsHandler);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
