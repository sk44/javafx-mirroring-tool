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
import sk44.mirroringtool.util.Action;

/**
 * FileVisitor implementation for clean.
 *
 * @author sk
 */
public class CleanFileVisitor extends SimpleFileVisitor<Path> {

    private final Path masterDirPath;
    private final Path backupDirPath;
    private final boolean test;
    private final Action<TaskProcessingDetail> visitFileNotifier;

    public CleanFileVisitor(Path masterDirPath, Path backupDirPath, boolean test, Action<TaskProcessingDetail> visitFileNotifier) {
        this.masterDirPath = masterDirPath;
        this.backupDirPath = backupDirPath;
        this.test = test;
        this.visitFileNotifier = visitFileNotifier;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        Path masterFilePath = this.masterDirPath.resolve(this.backupDirPath.relativize(file));
        TaskProcessingDetail detail = TaskProcessingDetail.createDeleteDetailOf(masterFilePath, file);
        if (test == false) {
            detail.execute();
        }
        // TODO skip は表示しなくていい
        visitFileNotifier.execute(detail);
        return FileVisitResult.CONTINUE;
    }
}
