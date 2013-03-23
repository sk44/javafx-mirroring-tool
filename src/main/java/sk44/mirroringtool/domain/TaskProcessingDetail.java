package sk44.mirroringtool.domain;

import java.nio.file.Path;
import java.util.Date;

/**
 * Backup task process details.
 *
 * @author sk
 */
public class TaskProcessingDetail {

    private final TaskProcessingType processType;
    private final Path path;
    private final Date masterLastUpdated;
    private final Date backupLastUpdated;

    public TaskProcessingDetail(TaskProcessingType processType, Path path, Date masterLastUpdated, Date backupLastUpdated) {
        this.processType = processType;
        this.path = path;
        this.masterLastUpdated = masterLastUpdated;
        this.backupLastUpdated = backupLastUpdated;
    }

    public TaskProcessingType getProcessType() {
        return processType;
    }

    public Path getPath() {
        return path;
    }

    public Date getMasterLastUpdated() {
        return masterLastUpdated;
    }

    public Date getBackupLastUpdated() {
        return backupLastUpdated;
    }
}
