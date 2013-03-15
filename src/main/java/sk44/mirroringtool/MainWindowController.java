/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sk44.mirroringtool;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sk44.mirroringtool.domain.Task;
import sk44.mirroringtool.domain.TaskRepository;
import sk44.mirroringtool.infrastructure.persistence.jpa.JpaTaskRepository;

/**
 * FXML Controller class
 *
 * @author sk
 */
public class MainWindowController implements Initializable {

    @FXML
    TableView<Task> taskTableView;
    @FXML
    TableColumn<Task, String> taskNameColumn;
    @FXML
    TableColumn<Task, String> taskMasterDirPathColumn;
    @FXML
    TableColumn<Task, String> taskBackupDirPathColumn;

    @FXML
    protected void handleNewTaskAction(ActionEvent event) {
        WindowEventListeners.INSTANCE.notify(WindowEvents.ON_OPEN_TASK_FORM);
    }

    @FXML
    protected void handleEditTaskAction(ActionEvent event) {
        Task task = taskTableView.getSelectionModel().getSelectedItem();
        if (task != null) {
            System.out.println(task.getId());
        }
    }

    @FXML
    protected void handleDeleteTaskAction(ActionEvent event) {
        // TODO
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        taskNameColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("name"));
        taskMasterDirPathColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("masterDirPath"));
        taskBackupDirPathColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("backupDirPath"));

        refreshTaskTable();

        WindowEventListeners.INSTANCE.addListener(WindowEvents.ON_SAVE_TASK_FORM, new WindowEventListener() {
            @Override
            public void handleEvent() {
                refreshTaskTable();
            }
        });
    }

    private void refreshTaskTable() {
        List<Task> items = taskTableView.getItems();
        items.removeAll(items);
        TaskRepository repos = new JpaTaskRepository();
        for (Task task : repos.all()) {
            items.add(task);
        }
    }
}
