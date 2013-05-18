/*
 */
package sk44.mirroringtool.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);

    public Task findBy(Long taskId) {
        try (RepositoriesContext context = new RepositoriesContext()) {
            return context.createTaskRepository().matches(taskId);
        }
    }

    public void merge(Task task) {
        try (RepositoriesContext context = new RepositoriesContext()) {
            context.createTaskRepository().merge(task);
            context.saveChanges();
        }
    }

    public void delete(Task task) {
        try (RepositoriesContext context = new RepositoriesContext()) {
            context.createTaskRepository().remove(task);
            context.saveChanges();
        }
    }

    public void execute(Long taskId, Action<TaskProcessingDetail> processingDetailsHandler, Action<Void> callback) {
        try (RepositoriesContext context = new RepositoriesContext()) {
            TaskRepository repos = context.createTaskRepository();
            Task task = repos.matches(taskId);
            if (task == null) {
                logger.warn("task [" + taskId + "] is not found.");
                return;
            }
            task.execute(processingDetailsHandler);
            context.saveChanges();
        }
        // TODO null 渡すのがアレ
        callback.execute(null);
    }

    public void executeAll() {
        // TODO
    }

    public void test(Long taskId, Action<TaskProcessingDetail> processingDetailsHandler) {
        try (RepositoriesContext context = new RepositoriesContext()) {
            TaskRepository repos = context.createTaskRepository();
            Task task = repos.matches(taskId);
            if (task == null) {
                logger.warn("task [" + taskId + "] is not found.");
                return;
            }
            task.test(processingDetailsHandler);
        }
    }
}
