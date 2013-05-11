/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sk44.mirroringtool.domain;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.Date;
import sk44.mirroringtool.util.Action;

/**
 * FileVisitor implementation for task.
 *
 * @author sk
 */
public class TaskFileVisitor extends SimpleFileVisitor<Path> {
    // TODO

    private final Path masterDirPath;
    private final Path backupDirPath;
    private final Action<TaskProcessingDetail> visitFileNotifier;

    public TaskFileVisitor(Path masterDirPath, Path backupDirPath, Action<TaskProcessingDetail> visitFileNotifier) {
        this.masterDirPath = masterDirPath;
        this.backupDirPath = backupDirPath;
        this.visitFileNotifier = visitFileNotifier;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        // TODO
        // see: http://d.hatena.ne.jp/waman/20120816/1345150695
        FileTime masterLastUpdated = Files.getLastModifiedTime(file);
        Path backupFilePath = this.backupDirPath.resolve(this.masterDirPath.relativize(file));
        System.out.println(backupFilePath.toString());
        visitFileNotifier.execute(new TaskProcessingDetail(TaskProcessingType.CREATE, file, new Date(masterLastUpdated.toMillis()), null));
        return FileVisitResult.CONTINUE;
    }
}
