/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sk44.mirroringtool.domain;

/**
 * Backup task processing type.
 *
 * @author sk
 */
public enum TaskProcessingType {

    CREATE("create"), UPDATE("update"), DELETE("delete");
    private final String description;

    private TaskProcessingType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
