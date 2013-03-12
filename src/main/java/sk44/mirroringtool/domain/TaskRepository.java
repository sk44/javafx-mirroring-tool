package sk44.mirroringtool.domain;

import java.util.List;

/**
 * Task repository.
 * 
 * @author sk
 */
public interface TaskRepository {

	void add(Task task);

	List<Task> all();
	
}
