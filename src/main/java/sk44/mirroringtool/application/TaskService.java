/*
 */
package sk44.mirroringtool.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sk44.mirroringtool.domain.MirroringTask;
import sk44.mirroringtool.domain.MirroringTaskRepository;
import sk44.mirroringtool.domain.TaskProcessingDetail;
import sk44.mirroringtool.infrastructure.persistence.jpa.RepositoriesContext;
import sk44.mirroringtool.util.Action;
import sk44.mirroringtool.util.Func;

/**
 * Application service.
 *
 * @author sk
 */
public class TaskService {

    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);

    public MirroringTask findBy(Long taskId) {
        try (RepositoriesContext context = new RepositoriesContext()) {
            return context.createTaskRepository().matches(taskId);
        }
    }

    public void merge(MirroringTask task) {
        try (RepositoriesContext context = new RepositoriesContext()) {
            context.createTaskRepository().merge(task);
            context.saveChanges();
        }
    }

    public void delete(MirroringTask task) {
        try (RepositoriesContext context = new RepositoriesContext()) {
            context.createTaskRepository().remove(task);
            context.saveChanges();
        }
    }

    public void execute(Long taskId, Action<TaskProcessingDetail> processingDetailsHandler, Func<Boolean> stop) {
        try (RepositoriesContext context = new RepositoriesContext()) {
            MirroringTaskRepository repos = context.createTaskRepository();
            MirroringTask task = repos.matches(taskId);
            if (task == null) {
                logger.warn("task [" + taskId + "] is not found.");
                return;
            }
            task.execute(processingDetailsHandler, stop);
            context.saveChanges();
        }
    }

    public void executeAll() {
        // TODO
    }

    public void test(Long taskId, Action<TaskProcessingDetail> processingDetailsHandler, Func<Boolean> stop) {
        try (RepositoriesContext context = new RepositoriesContext()) {
            MirroringTaskRepository repos = context.createTaskRepository();
            MirroringTask task = repos.matches(taskId);
            if (task == null) {
                logger.warn("task [" + taskId + "] is not found.");
                return;
            }
            task.test(processingDetailsHandler, stop);
        }
    }
}
