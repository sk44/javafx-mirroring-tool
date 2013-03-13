/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sk44.mirroringtool;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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

		// TODO connection test. delete!
//		EntityManagerFactory emf =
//	                Persistence.createEntityManagerFactory("mirroring-toolPU");
//       EntityManager em = emf.createEntityManager();
//	        TypedQuery tquery = em.createQuery("select t from Task t", Task.class);
//        List<Task> list = tquery.getResultList();
//		System.out.println(list);

		primaryStage = stage;
		initListeners();

		stage.setTitle("Mirroring Tool");
		Scene scene = new Scene(loadPaneFromFXML("mainWindow.fxml"));
		stage.setScene(scene);
		stage.show();
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
		listeners.addListener(WindowEvents.ON_CLOSE_TASK_FORM, new WindowEventListener() {
			@Override
			public void handleEvent() {
				closeTaskForm();
			}
		});
	}

	void showTaskForm() {
		formStage = new Stage();
		formStage.initStyle(StageStyle.UTILITY);
		formStage.setTitle("New Task");
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
