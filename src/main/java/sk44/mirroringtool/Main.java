/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sk44.mirroringtool;

import java.io.IOException;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/**
 * JavaFX entry point.
 *
 * @author sk
 */
public class Main extends Application {

    private Stage primaryStage;
    private Stage formStage;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        primaryStage = stage;
        initListeners();

        stage.setTitle("Mirroring Tool");
        Scene scene = new Scene(loadPaneFromFXML("mainWindow.fxml"));
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                System.out.println("stage will close...");
            }
        });
    }

    @Override
    public void stop() throws Exception {
        System.out.println("application will terminate...");
        super.stop();
    }

    void initListeners() {
        // TODO サブウィンドウのイベント通知方法を検討
        WindowEventListeners listeners = WindowEventListeners.INSTANCE;
        listeners.addListener(WindowEvents.ON_OPEN_TASK_FORM,
            new WindowEventListener() {
            @Override
            public void handleEvent() {
                showTaskForm();
            }
        });
        listeners.addListener(WindowEvents.ON_SAVE_TASK_FORM, new WindowEventListener() {
            @Override
            public void handleEvent() {
                closeTaskForm();
            }
        });
        listeners.addListener(WindowEvents.ON_CANCEL_TASK_FORM, new WindowEventListener() {
            @Override
            public void handleEvent() {
                closeTaskForm();
            }
        });
    }

    void showTaskForm() {
        formStage = new Stage();
        formStage.initStyle(StageStyle.UTILITY);
        formStage.setTitle(PassedParameters.INSTANCE.hasTaskId() ? "Edit Task" : "New Task");
        formStage.initModality(Modality.APPLICATION_MODAL);
        formStage.initOwner(primaryStage);
        Scene scene = new Scene(loadPaneFromFXML("taskForm.fxml"));
        formStage.setScene(scene);
        formStage.show();
    }

    void closeTaskForm() {
        formStage.close();
    }

    private Pane loadPaneFromFXML(String fxmlPath) {
        try {
            return (Pane) FXMLLoader.load(getClass().getResource(fxmlPath));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
