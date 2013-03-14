/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sk44.mirroringtool;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import sk44.mirroringtool.domain.TaskRepository;
import sk44.mirroringtool.infrastructure.persistence.jpa.JpaTaskRepository;

/**
 * FXML Controller class
 *
 * @author sk
 */
public class MainWindowController implements Initializable {

	@FXML
	protected void handleNewTaskAction(ActionEvent event) {
		WindowEventListeners.INSTANCE.notify(WindowEvents.ON_OPEN_TASK_FORM);
	}
	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO set results to table
        TaskRepository repos = new JpaTaskRepository();
        System.out.println(repos.all());
	}	
}
