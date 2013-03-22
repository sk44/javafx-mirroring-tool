package sk44.mirroringtool.domain;

import java.util.List;

/**
 * Task repository.
 *
 * @author sk
 */
public interface TaskRepository {

    void add(Task task);

    void merge(Task task);

    void remove(Task task);

    Task matches(Long id);

    List<Task> all();
}
