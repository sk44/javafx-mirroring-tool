package sk44.mirroringtool.infrastructure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import sk44.mirroringtool.domain.Task;
import sk44.mirroringtool.domain.TaskRepository;

/**
 *
 * @author sk
 */
public class InMemoryTaskRepository implements TaskRepository {

	private final List<Task> tasks = new ArrayList<>();

	@Override
	public void add(Task task) {
		tasks.add(task);
	}

	@Override
	public List<Task> all() {
		return Collections.unmodifiableList(tasks);
	}
	
}
