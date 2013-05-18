/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sk44.mirroringtool;

import java.net.URL;
import java.util.Date;
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
import javafx.util.Callback;
import org.joda.time.DateTime;
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

    private static final String DATE_FORMAT_FOR_LAST_MODIFIED = "yyyy-MM-dd HH:mm:ss:SSS";
    private static final String DATE_FORMAT_FOR_LAST_EXECUTED = "yyyy-MM-dd HH:mm:ss";
    private static final String DATE_VALUE_FOR_NULL = "--";
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
    TableColumn<Task, String> taskLastExecutedColumn;
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
        taskService.execute(task.getId(), new Action<TaskProcessingDetail>() {
            @Override
            public void execute(TaskProcessingDetail obj) {
                addDetailToProcessingTable(obj);
            }
        }, new Action<Void>() {
            @Override
            public void execute(Void obj) {
                refreshTaskTable();
            }
        });
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

        initializeTasks();
        initializeTaskProcessingDetails();

        WindowEventListeners.INSTANCE.addListener(WindowEvents.ON_SAVE_TASK_FORM, new WindowEventListener() {
            @Override
            public void handleEvent() {
                refreshTaskTable();
            }
        });
    }

    private void initializeTasks() {
        taskNameColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Task, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Task, String> p) {
                return new SimpleStringProperty(p.getValue().getName());
            }
        });
        taskMasterDirPathColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Task, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Task, String> p) {
                return new SimpleStringProperty(p.getValue().getMasterDirPath());
            }
        });
        taskBackupDirPathColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Task, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Task, String> p) {
                return new SimpleStringProperty(p.getValue().getBackupDirPath());
            }
        });
        taskLastExecutedColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Task, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Task, String> p) {
                Date lastExecuted = p.getValue().getLastExecuted();
                if (lastExecuted == null) {
                    return new SimpleStringProperty(DATE_VALUE_FOR_NULL);
                }
                return new SimpleStringProperty(new DateTime(lastExecuted).toString(DATE_FORMAT_FOR_LAST_EXECUTED));
            }
        });
        // TODO 残りの列

        refreshTaskTable();
    }

    private void initializeTaskProcessingDetails() {
        taskProcessingDetailsTableView.getItems().clear();
        processingDescriptionColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<TaskProcessingDetail, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<TaskProcessingDetail, String> p) {
                return new SimpleStringProperty(p.getValue().getProcessType().getDescription());
            }
        });
        processingPathColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<TaskProcessingDetail, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<TaskProcessingDetail, String> p) {
                return new SimpleStringProperty(p.getValue().getMasterFilePath().toString());
            }
        });
        processingMasterLastUpdatedColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<TaskProcessingDetail, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<TaskProcessingDetail, String> p) {
                DateTime lastModified = p.getValue().getMasterLastUpdated();
                if (lastModified == null) {
                    return new SimpleStringProperty(DATE_VALUE_FOR_NULL);
                }
                return new SimpleStringProperty(lastModified.toString(DATE_FORMAT_FOR_LAST_MODIFIED));
            }
        });
        processingBackupLastUpdatedColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<TaskProcessingDetail, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<TaskProcessingDetail, String> p) {
                DateTime lastModified = p.getValue().getBackupLastUpdated();
                if (lastModified == null) {
                    return new SimpleStringProperty(DATE_VALUE_FOR_NULL);
                }
                return new SimpleStringProperty(lastModified.toString(DATE_FORMAT_FOR_LAST_MODIFIED));
            }
        });
        // TODO 残りの列の初期化
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
