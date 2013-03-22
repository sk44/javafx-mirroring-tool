/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sk44.mirroringtool;

/**
 * Hold parameters to pass an other window.
 *
 * @author sk
 */
enum PassedParameters {

    INSTANCE;
    private Long taskId;

    public Long getTaskIdAndClear() {
        Long result = taskId;
        taskId = null;
        return result;
    }

    public boolean hasTaskId() {
        return taskId != null;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }
}
