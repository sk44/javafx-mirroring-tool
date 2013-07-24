/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sk44.mirroringtool;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import sk44.mirroringtool.application.TaskService;
import sk44.mirroringtool.domain.MirroringTask;

/**
 * FXML Controller class
 *
 * @author sk
 */
public class TaskFormController implements Initializable {

    interface ChosenDirectoryHandler {

        void handleDir(File dir);
    }
    private MirroringTask task;
    @FXML
    private TextField taskName;
    @FXML
    private TextField masterDirPath;
    @FXML
    private TextField backupDirPath;

    @FXML
    protected void handleBrowseMasterDir(ActionEvent event) {
        openDirectoryChooser("Choose Master Dir...", new ChosenDirectoryHandler() {
            @Override
            public void handleDir(File dir) {
                masterDirPath.setText(dir.getAbsolutePath());
            }
        });
    }

    @FXML
    protected void handleBrowseBackupDir(ActionEvent event) {
        openDirectoryChooser("Choose Backup Dir...", new ChosenDirectoryHandler() {
            @Override
            public void handleDir(File dir) {
                backupDirPath.setText(dir.getAbsolutePath());
            }
        });
    }

    private void openDirectoryChooser(String title, ChosenDirectoryHandler handler) {
        DirectoryChooser dc = new DirectoryChooser();
        dc.setTitle(title);
        // TODO set arg
        File dir = dc.showDialog(null);
        if (dir != null) {
            handler.handleDir(dir);
        }
    }

    @FXML
    protected void handleOKAction(ActionEvent event) {

        // TODO validate
        task.setName(taskName.getText());
        task.setMasterDirPath(masterDirPath.getText());
        task.setBackupDirPath(backupDirPath.getText());

        new TaskService().merge(task);

        WindowEventListeners.INSTANCE.notify(WindowEvents.ON_SAVE_TASK_FORM);
    }

    @FXML
    protected void handleCancelAction(ActionEvent event) {
        WindowEventListeners.INSTANCE.notify(WindowEvents.ON_SAVE_TASK_FORM);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Long passedTaskId = PassedParameters.INSTANCE.getTaskIdAndClear();
        if (passedTaskId == null) {
            task = new MirroringTask();
        } else {
            task = new TaskService().findBy(passedTaskId);
            backupDirPath.textProperty().set(task.getBackupDirPath());
            masterDirPath.textProperty().set(task.getMasterDirPath());
            taskName.textProperty().set(task.getName());
        }
    }
}
