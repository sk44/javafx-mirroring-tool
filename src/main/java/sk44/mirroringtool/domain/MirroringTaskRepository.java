package sk44.mirroringtool.domain;

import java.util.List;

/**
 * MirroringTask repository.
 *
 * @author sk
 */
public interface MirroringTaskRepository {

    void add(MirroringTask task);

    void merge(MirroringTask task);

    void remove(MirroringTask task);

    MirroringTask matches(Long id);

    List<MirroringTask> all();
}
