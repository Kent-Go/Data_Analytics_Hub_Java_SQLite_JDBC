/*

 * AlertPopUp.java
 * 
 * Version: 1.0
 *
 * Date: 01/10/2023
 * 
 * © 2023 Go Chee Kin.
 * 
 * All rights reserved.
 */
package analytics.controller;

import javafx.geometry.Dimension2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Screen;

/**
 * 
 * The AlertPopUp class provides methods to create error, information and
 * confirmation alert in Data Analytics Hub application.
 */
public class AlertPopUp {

    private static AlertPopUp alertPopUp;

    /**
     * The method to create AlertPopUp instance only if it did not exist
     * 
     */
    public static AlertPopUp getInstance() {

	if (alertPopUp != null) {
	    return alertPopUp; // the reference is already referred to the object
	}

	// otherwise we create 1 object of the model and return it
	alertPopUp = new AlertPopUp();

	return alertPopUp;
    }

    /**
     * The method to return Alert object for displaying error
     * 
     * @return errorAlert The Alert object
     */
    public Alert showErrorAlert(String headerText, String errorMessage) {
	Alert errorAlert = new Alert(AlertType.ERROR);
	errorAlert.setHeaderText(headerText);
	errorAlert.setContentText(errorMessage);
	return errorAlert;
    }

    /**
     * The method to return Alert object for displaying information
     * 
     * @return infoAlert The Alert object
     */
    public Alert showInfoAlert(String headerText, String infoMessage) {
	Alert infoAlert = new Alert(AlertType.INFORMATION);
	infoAlert.setHeaderText(headerText);
	infoAlert.setContentText(infoMessage);
	return infoAlert;
    }

    /**
     * The method to return Alert object for getting user confirmation
     * 
     * @return confirmAlert The Alert object
     */
    public Alert showConfirmAlert(String headerText, String confirmationMessage) {
	Alert confirmAlert = new Alert(AlertType.CONFIRMATION);
	confirmAlert.setHeaderText(headerText);
	confirmAlert.setContentText(confirmationMessage);
	return confirmAlert;
    }
}
