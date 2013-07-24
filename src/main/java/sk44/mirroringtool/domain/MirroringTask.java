/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sk44.mirroringtool.domain;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sk44.mirroringtool.util.Action;

/**
 * Task entity.
 *
 * @author sk
 */
@Entity
public class Task {

    private static final Logger logger = LoggerFactory.getLogger(Task.class);
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 50)
    private String name;
    @Column(nullable = false, length = 300)
    private String masterDirPath;
    @Column(nullable = false, length = 300)
    private String backupDirPath;
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastExecuted;
    @Column
    @Convert(converter = ActiveTypeConverter.class)
    private ActiveType activeType;

    public Task() {
        activeType = ActiveType.ACTIVE;
    }

    public void execute(Action<TaskProcessingDetail> handler) {
        execute(false, handler);
        lastExecuted = new Date();
    }

    public void test(Action<TaskProcessingDetail> handler) {
        execute(true, handler);
    }

    private void execute(boolean test, Action<TaskProcessingDetail> handler) {
        Path masterPath = new File(masterDirPath).toPath();
        Path backupPath = new File(backupDirPath).toPath();
        FileVisitor<Path> visitor = new TaskFileVisitor(masterPath, backupPath, test, handler);
        FileVisitor<Path> cleanVisitor = new CleanFileVisitor(masterPath, backupPath, test, handler);
        try {
            Files.walkFileTree(masterPath, visitor);
            Files.walkFileTree(backupPath, cleanVisitor);
        } catch (IOException ex) {
            // TODO notify error
            logger.error(ex.getMessage(), ex);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMasterDirPath() {
        return masterDirPath;
    }

    public void setMasterDirPath(String masterDirPath) {
        this.masterDirPath = masterDirPath;
    }

    public String getBackupDirPath() {
        return backupDirPath;
    }

    public void setBackupDirPath(String backupDirPath) {
        this.backupDirPath = backupDirPath;
    }

    public Date getLastExecuted() {
        return lastExecuted;
    }

    public void setLastExecuted(Date lastExecuted) {
        this.lastExecuted = lastExecuted;
    }

    public ActiveType getActiveType() {
        return activeType;
    }

    public void setActiveType(ActiveType activeType) {
        this.activeType = activeType;
    }
}
