/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sk44.mirroringtool.domain;

import java.nio.file.Path;

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
            // TODO
            System.out.println("copy " + masterFilePath.toString() + " to " + backupFilePath.toString());
        }
    },
    /**
     * Update backup file.
     */
    UPDATE("update") {
        @Override
        public void execute(Path masterFilePath, Path backupFilePath) {
            // TODO
            System.out.println("update " + masterFilePath.toString() + " to " + backupFilePath.toString());
        }
    },
    /**
     * Delete backup file.
     */
    DELETE("delete") {
        @Override
        public void execute(Path masterFilePath, Path backupFilePath) {
            // TODO
            System.out.println("delete file: " + backupFilePath.toString());
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
    private final String description;

    private TaskProcessingType(String description) {
        this.description = description;
    }

    abstract void execute(Path masterFilePath, Path backupFilePath);

    public String getDescription() {
        return description;
    }
}
