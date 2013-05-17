package sk44.mirroringtool.domain;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import org.joda.time.DateTime;

/**
 * Backup task process details.
 *
 * @author sk
 */
public class TaskProcessingDetail {

    private final TaskProcessingType processType;
    private final Path masterFilePath;
    private final Path backupFilePath;
    private final DateTime masterLastUpdated;
    private final DateTime backupLastUpdated;

    static TaskProcessingDetail createProcessingDetailOf(Path masterFilePath, Path backupFilePath) throws IOException {
        DateTime masterLastModified = lastModifiedOf(masterFilePath);
        DateTime backupLastModified = lastModifiedOf(backupFilePath);

        // マスター起点なので削除はない
        TaskProcessingType processingType;
        if (Files.exists(backupFilePath) == false) {
            processingType = TaskProcessingType.COPY;
        } else {
            processingType = isMasterUpdated(masterLastModified, backupLastModified)
                ? TaskProcessingType.UPDATE : TaskProcessingType.SKIP;
        }
        return new TaskProcessingDetail(processingType, masterFilePath, backupFilePath, masterLastModified, backupLastModified);
    }

    static TaskProcessingDetail createDeleteDetailOf(Path masterFilePath, Path backupFilePath) throws IOException {
        DateTime masterLastModified = lastModifiedOf(masterFilePath);
        DateTime backupLastModified = lastModifiedOf(backupFilePath);
        TaskProcessingType processingType;
        if (Files.exists(masterFilePath) == false) {
            processingType = TaskProcessingType.DELETE;
        } else {
            processingType = TaskProcessingType.SKIP;
        }

        return new TaskProcessingDetail(processingType, masterFilePath, backupFilePath, masterLastModified, backupLastModified);
    }

    private static DateTime lastModifiedOf(Path path) throws IOException {
        if (Files.exists(path) == false) {
            return null;
        }
        FileTime fileTime = Files.getLastModifiedTime(path);
        return new DateTime(fileTime.toMillis());
    }

    private static boolean isMasterUpdated(DateTime masterLastUpdated, DateTime backupLastUpdated) {
        if (masterLastUpdated == null || backupLastUpdated == null) {
            return false;
        }
        return masterLastUpdated.isAfter(backupLastUpdated);
    }

    private TaskProcessingDetail(TaskProcessingType processType, Path path, Path backupFilePath, DateTime masterLastUpdated, DateTime backupLastUpdated) {
        this.processType = processType;
        this.masterFilePath = path;
        this.backupFilePath = backupFilePath;
        this.masterLastUpdated = masterLastUpdated;
        this.backupLastUpdated = backupLastUpdated;
    }

    void execute() {
        processType.execute(masterFilePath, backupFilePath);
    }

    public TaskProcessingType getProcessType() {
        return processType;
    }

    public Path getMasterFilePath() {
        return masterFilePath;
    }

    public DateTime getMasterLastUpdated() {
        return masterLastUpdated;
    }

    public DateTime getBackupLastUpdated() {
        return backupLastUpdated;
    }
}
