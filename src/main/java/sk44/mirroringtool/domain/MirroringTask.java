/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sk44.mirroringtool.domain;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Access;
import javax.persistence.AccessType;
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
import sk44.mirroringtool.util.Func;

/**
 * Mirroring task entity.
 *
 * @author sk
 */
@Entity
@Access(AccessType.FIELD)
public class MirroringTask {

    private static final Logger logger = LoggerFactory.getLogger(MirroringTask.class);
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Column(nullable = false, length = 50)
    public String name;
    @Column(nullable = false, length = 300)
    public String masterDirPath;
    @Column(nullable = false, length = 300)
    public String backupDirPath;
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    public Date lastExecuted;
    @Column(nullable = false)
    public boolean auto;
//    @Column(nullable = false)
//    @Convert(converter = ActiveTypeConverter.class)
//    public ActiveType activeType;
    @Column(nullable = false)
    @Convert(converter = ResultTypeConverter.class)
    public ResultType resultType;

    public MirroringTask() {
        auto = true;
        resultType = ResultType.NONE;
    }

    public void execute(Action<TaskProcessingDetail> handler, Func<Boolean> stop) {
        resultType = execute(false, handler, stop);
        lastExecuted = new Date();
    }

    public void test(Action<TaskProcessingDetail> handler, Func<Boolean> stop) {
        execute(true, handler, stop);
    }

    private ResultType execute(boolean test, Action<TaskProcessingDetail> handler, Func<Boolean> stop) {
        Path masterPath = new File(masterDirPath).toPath();
        Path backupPath = new File(backupDirPath).toPath();
        TaskFileVisitor visitor = new TaskFileVisitor(masterPath, backupPath, test, handler, stop);
        CleanFileVisitor cleanVisitor = new CleanFileVisitor(masterPath, backupPath, test, handler, stop);
        try {
            Files.walkFileTree(masterPath, visitor);
            if (visitor.getResultType() == ResultType.STOP) {
                return visitor.getResultType();
            }
            Files.walkFileTree(backupPath, cleanVisitor);
            return cleanVisitor.getResultType();
        } catch (IOException ex) {
            // TODO notify error
            logger.error(ex.getMessage(), ex);
            return ResultType.ERROR;
        }

    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.id);
        hash = 67 * hash + Objects.hashCode(this.name);
        hash = 67 * hash + Objects.hashCode(this.masterDirPath);
        hash = 67 * hash + Objects.hashCode(this.backupDirPath);
        hash = 67 * hash + Objects.hashCode(this.lastExecuted);
        hash = 67 * hash + Objects.hashCode(this.auto);
        hash = 67 * hash + Objects.hashCode(this.resultType);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MirroringTask other = (MirroringTask) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.masterDirPath, other.masterDirPath)) {
            return false;
        }
        if (!Objects.equals(this.backupDirPath, other.backupDirPath)) {
            return false;
        }
        if (!Objects.equals(this.lastExecuted, other.lastExecuted)) {
            return false;
        }
        if (this.auto != other.auto) {
            return false;
        }
        if (this.resultType != other.resultType) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MirroringTask{" + "id=" + id + '}';
    }
}
