/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sk44.mirroringtool.domain;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Backup task processing type.
 *
 * @author sk
 */
public enum TaskProcessingType {

    /**
     * Create backup file.
     */
    COPY("copy") {
        @Override
        public void execute(Path masterFilePath, Path backupFilePath) {
            try {
                Files.copy(masterFilePath, backupFilePath, StandardCopyOption.COPY_ATTRIBUTES);
                logger.info("copy " + masterFilePath.toString() + " to " + backupFilePath.toString());
            } catch (IOException ex) {
                logger.error(ex.getMessage(), ex);
            }
        }
    },
    /**
     * Update backup file.
     */
    UPDATE("update") {
        @Override
        public void execute(Path masterFilePath, Path backupFilePath) {
            try {
                Files.copy(masterFilePath, backupFilePath,
                    StandardCopyOption.COPY_ATTRIBUTES,
                    StandardCopyOption.REPLACE_EXISTING);
                logger.info("update " + masterFilePath.toString() + " to " + backupFilePath.toString());
            } catch (IOException ex) {
                logger.error(ex.getMessage(), ex);
            }
        }
    },
    /**
     * Delete backup file.
     */
    DELETE("delete") {
        @Override
        public void execute(Path masterFilePath, Path backupFilePath) {
            try {
                Files.delete(backupFilePath);
                logger.info("delete file: " + backupFilePath.toString());
            } catch (IOException ex) {
                logger.error(ex.getMessage(), ex);
            }
        }
    },
    /**
     * Skip (No changes).
     */
    SKIP("skip") {
        @Override
        public void execute(Path masterFilePath, Path backupFilePath) {
            // no-op
        }
    };
    private static final Logger logger = LoggerFactory.getLogger(TaskProcessingType.class);
    private final String description;

    private TaskProcessingType(String description) {
        this.description = description;
    }

    abstract void execute(Path masterFilePath, Path backupFilePath);

    public String getDescription() {
        return description;
    }
}
