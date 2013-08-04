/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sk44.mirroringtool.domain;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sk44.mirroringtool.util.Action;
import sk44.mirroringtool.util.Func;

/**
 * FileVisitor implementation for clean.
 *
 * @author sk
 */
public class CleanFileVisitor extends SimpleFileVisitor<Path> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CleanFileVisitor.class);
    private final Path masterDirPath;
    private final Path backupDirPath;
    private final boolean test;
    private final Action<TaskProcessingDetail> visitFileNotifier;
    private final Func<Boolean> stop;
    private ResultType resultType;

    public ResultType getResultType() {
        return resultType;
    }

    public CleanFileVisitor(Path masterDirPath, Path backupDirPath, boolean test, Action<TaskProcessingDetail> visitFileNotifier, Func<Boolean> stop) {
        this.masterDirPath = masterDirPath;
        this.backupDirPath = backupDirPath;
        this.test = test;
        this.visitFileNotifier = visitFileNotifier;
        this.stop = stop;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        if (stop.execute().booleanValue()) {
            LOGGER.info("cleaning file will terminate.");
            resultType = ResultType.STOP;
            return FileVisitResult.TERMINATE;
        }
        Path masterFilePath = this.masterDirPath.resolve(this.backupDirPath.relativize(file));
        TaskProcessingDetail detail = TaskProcessingDetail.createDeleteDetailOf(masterFilePath, file);
        if (test == false) {
            detail.execute();
        }
        if (detail.getProcessType() != TaskProcessingType.SKIP) {
            visitFileNotifier.execute(detail);
        }
        resultType = ResultType.SUCCESS;
        return FileVisitResult.CONTINUE;
    }
}
