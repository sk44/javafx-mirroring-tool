/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sk44.mirroringtool;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import org.joda.time.DateTime;
import sk44.mirroringtool.application.TaskService;
import sk44.mirroringtool.domain.MirroringTask;
import sk44.mirroringtool.domain.MirroringTaskRepository;
import sk44.mirroringtool.domain.TaskProcessingDetail;
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
    private final ObservableList<TaskProcessingDetail> taskResults = FXCollections.observableArrayList();
    private TaskService taskService;
    private MainWindowViewModel viewModel;
    // buttons ----------
    @FXML
    Button buttonExecute;
    @FXML
    Button buttonTest;
    @FXML
    Button buttonClearResults;
    @FXML
    Button buttonNewTask;
    @FXML
    Button buttonEdit;
    @FXML
    Button buttonDelete;
    // task table ----------
    @FXML
    TableView<MirroringTask> taskTableView;
    @FXML
    TableColumn<MirroringTask, String> taskNameColumn;
    @FXML
    TableColumn<MirroringTask, String> taskMasterDirPathColumn;
    @FXML
    TableColumn<MirroringTask, String> taskBackupDirPathColumn;
    @FXML
    TableColumn<MirroringTask, String> taskLastExecutedColumn;
    @FXML
    TableColumn<MirroringTask, String> taskResultColumn;
    // result table ----------
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
        final MirroringTask task = taskTableView.getSelectionModel().getSelectedItem();
        if (task == null) {
            return;
        }
        new Thread(createTask(task.getId(), false)).start();
    }

    @FXML
    protected void handleTestTaskAction(ActionEvent event) {
        final MirroringTask task = taskTableView.getSelectionModel().getSelectedItem();
        if (task == null) {
            return;
        }
        new Thread(createTask(task.getId(), true)).start();
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
        MirroringTask task = taskTableView.getSelectionModel().getSelectedItem();
        if (task == null) {
            return;
        }
        PassedParameters.INSTANCE.setTaskId(task.getId());
        WindowEventListeners.INSTANCE.notify(WindowEvents.ON_OPEN_TASK_FORM);
    }

    @FXML
    protected void handleDeleteTaskAction(ActionEvent event) {
        MirroringTask task = taskTableView.getSelectionModel().getSelectedItem();
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

        viewModel = new MainWindowViewModel();
        taskService = new TaskService();

        initializeTasks();
        initializeTaskProcessingDetails();
        taskProcessingDetailsTableView.setItems(taskResults);
        buttonExecute.disableProperty().bind(viewModel.executingProperty().or(viewModel.selectedProperty().not()));
        buttonTest.disableProperty().bind(viewModel.executingProperty().or(viewModel.selectedProperty().not()));

        buttonClearResults.disableProperty().bind(viewModel.executingProperty());
        buttonNewTask.disableProperty().bind(viewModel.executingProperty());
        buttonEdit.disableProperty().bind(viewModel.executingProperty().or(viewModel.selectedProperty().not()));
        buttonDelete.disableProperty().bind(viewModel.executingProperty().or(viewModel.selectedProperty().not()));

        taskTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<MirroringTask>() {
            @Override
            public void changed(ObservableValue<? extends MirroringTask> ov, MirroringTask t, MirroringTask t1) {
                viewModel.selectedProperty().set(true);
            }
        });

        WindowEventListeners.INSTANCE.addListener(WindowEvents.ON_SAVE_TASK_FORM, new WindowEventListener() {
            @Override
            public void handleEvent() {
                refreshTaskTable();
            }
        });
    }

    private void initializeTasks() {
        taskNameColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<MirroringTask, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<MirroringTask, String> p) {
                return new SimpleStringProperty(p.getValue().getName());
            }
        });
        taskMasterDirPathColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<MirroringTask, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<MirroringTask, String> p) {
                return new SimpleStringProperty(p.getValue().getMasterDirPath());
            }
        });
        taskBackupDirPathColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<MirroringTask, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<MirroringTask, String> p) {
                return new SimpleStringProperty(p.getValue().getBackupDirPath());
            }
        });
        taskLastExecutedColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<MirroringTask, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<MirroringTask, String> p) {
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
        List<MirroringTask> items = taskTableView.getItems();
        items.removeAll(items);
        MirroringTaskRepository repos = new JpaTaskRepository();
        for (MirroringTask task : repos.all()) {
            items.add(task);
        }
    }

    private void addDetailToProcessingTable(TaskProcessingDetail detail) {
        taskResults.add(detail);
        taskProcessingDetailsTableView.scrollTo(taskResults.size() - 1);
    }

    private Task<Void> createTask(final Long mirroringTaskId, final boolean test) {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    viewModel.executingProperty().set(true);
                    if (test) {
                        taskService.test(mirroringTaskId, createAddRowHandler());
                    } else {
                        taskService.execute(mirroringTaskId, createAddRowHandler(), createFinishedHandler());
                    }
                    return null;
                } finally {
                    viewModel.executingProperty().set(false);
                }
            }

            private Action<TaskProcessingDetail> createAddRowHandler() {
                return new Action<TaskProcessingDetail>() {
                    @Override
                    public void execute(final TaskProcessingDetail obj) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                addDetailToProcessingTable(obj);
                            }
                        });
                    }
                };
            }

            private Action<Void> createFinishedHandler() {
                return new Action<Void>() {
                    @Override
                    public void execute(Void obj) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                refreshTaskTable();
                            }
                        });
                    }
                };
            }
        };
    }
}
