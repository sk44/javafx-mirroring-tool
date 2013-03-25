/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sk44.mirroringtool;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import sk44.mirroringtool.application.TaskService;
import sk44.mirroringtool.domain.Task;
import sk44.mirroringtool.domain.TaskProcessingDetail;
import sk44.mirroringtool.domain.TaskRepository;
import sk44.mirroringtool.infrastructure.persistence.jpa.JpaTaskRepository;
import sk44.mirroringtool.util.Action;

/**
 * FXML Controller class
 *
 * @author sk
 */
public class MainWindowController implements Initializable {

    private TaskService taskService;
    @FXML
    TableView<Task> taskTableView;
    @FXML
    TableColumn<Task, String> taskNameColumn;
    @FXML
    TableColumn<Task, String> taskMasterDirPathColumn;
    @FXML
    TableColumn<Task, String> taskBackupDirPathColumn;
    @FXML
    TableView<TaskProcessingDetail> taskProcessingDetailsTableView;
    @FXML
    TableColumn<TaskProcessingDetail, String> processingDescriptionColumn;
    @FXML
    TableColumn<TaskProcessingDetail, String> processingPathColumn;
    @FXML
    TableColumn<TaskProcessingDetail, String> processingMasterLastUpdatedColumn;
    @FXML
    TableColumn<TaskProcessingDetail, String> processingBackupLastUpdatedColumn;

    @FXML
    protected void handleExecuteTaskAction(ActionEvent event) {
        Task task = taskTableView.getSelectionModel().getSelectedItem();
        if (task == null) {
            return;
        }
        taskService.execute(task.getId());
    }

    @FXML
    protected void handleTestTaskAction(ActionEvent event) {
        Task task = taskTableView.getSelectionModel().getSelectedItem();
        if (task == null) {
            return;
        }
        taskService.test(task.getId(), new Action<TaskProcessingDetail>() {
            @Override
            public void execute(TaskProcessingDetail obj) {
                addDetailToProcessingTable(obj);
            }
        });
    }

    @FXML
    protected void handleClearResultsAction(ActionEvent event) {
        taskProcessingDetailsTableView.getItems().clear();
    }

    @FXML
    protected void handleNewTaskAction(ActionEvent event) {
        WindowEventListeners.INSTANCE.notify(WindowEvents.ON_OPEN_TASK_FORM);
    }

    @FXML
    protected void handleEditTaskAction(ActionEvent event) {
        Task task = taskTableView.getSelectionModel().getSelectedItem();
        if (task == null) {
            return;
        }
        PassedParameters.INSTANCE.setTaskId(task.getId());
        WindowEventListeners.INSTANCE.notify(WindowEvents.ON_OPEN_TASK_FORM);
    }

    @FXML
    protected void handleDeleteTaskAction(ActionEvent event) {
        Task task = taskTableView.getSelectionModel().getSelectedItem();
        if (task == null) {
            return;
        }
        taskService.delete(task);
        refreshTaskTable();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        taskService = new TaskService();

        // initialize task table.
        // TODO プロパティ名書く以外にやり方ない？
        taskNameColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("name"));
        taskMasterDirPathColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("masterDirPath"));
        taskBackupDirPathColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("backupDirPath"));

        refreshTaskTable();

        // initialize processing table.
        taskProcessingDetailsTableView.getItems().clear();
        processingDescriptionColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<TaskProcessingDetail, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<TaskProcessingDetail, String> p) {
                return new SimpleStringProperty(p.getValue().getProcessType().getDescription());
            }
        });
        // TODO

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

    private void addDetailToProcessingTable(TaskProcessingDetail detail) {
        final ObservableList<TaskProcessingDetail> items = taskProcessingDetailsTableView.getItems();
        items.add(detail);
        // TODO 初回実行時に中身が空っぽになる
//        taskProcessingDetailsTableView.scrollTo(items.size() - 1);
    }
}
