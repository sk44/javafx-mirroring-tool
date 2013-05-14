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
 * FileVisitor implementation for task.
 *
 * @author sk
 */
public class TaskFileVisitor extends SimpleFileVisitor<Path> {

    private final Path masterDirPath;
    private final Path backupDirPath;
    private final boolean test;
    private final Action<TaskProcessingDetail> visitFileNotifier;

    public TaskFileVisitor(Path masterDirPath, Path backupDirPath, boolean test, Action<TaskProcessingDetail> visitFileNotifier) {
        this.masterDirPath = masterDirPath;
        this.backupDirPath = backupDirPath;
        this.test = test;
        this.visitFileNotifier = visitFileNotifier;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        // TODO
        // see: http://d.hatena.ne.jp/waman/20120816/1345150695

        Path backupFilePath = this.backupDirPath.resolve(this.masterDirPath.relativize(file));
        TaskProcessingDetail detail = TaskProcessingDetail.createProcessingDetailOf(file, backupFilePath);
        if (test == false) {
            detail.execute();
        }
        visitFileNotifier.execute(detail);
        return FileVisitResult.CONTINUE;
    }
}
