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

/**
 * FXML Controller class
 *
 * @author sk
 */
public class TaskFormController implements Initializable {

	@FXML
	protected void handleOKAction(ActionEvent event) {
		// TODO creation
		WindowEventListeners.INSTANCE.fire(WindowEventListeners.Events.ON_CLOSE_TASK_FORM);
	}

	@FXML
	protected void handleCancelAction(ActionEvent event) {
		WindowEventListeners.INSTANCE.fire(WindowEventListeners.Events.ON_CLOSE_TASK_FORM);
	}

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO
	}	
}
