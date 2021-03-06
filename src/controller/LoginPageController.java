package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoginPageController extends ControllerAbs
{
	@FXML Button loginButtonID;
	@FXML TextField userID;
	@FXML TextField passID;
	Stage createNewAccStage = null;
	
	
	/*
	 * When this method is called, if the login credentials are valid, the scene
	 * changes to the main menu.
	 * 
	 * To do that, first we get the scene of the main menu and pass that to the scene object. 
	 * Then we need to get the stage information and pass the scene to the stage object.
	 */
	public void loginButton(ActionEvent event) throws IOException
	{
		if(!validLogin())
			return;
		
		getEnterIngredientsPage();
	}
	
	
	/*
	 * This method passes the username and password input by the user and sends it to
	 * the model layer. The methods in that layer will check whether these login credentials
	 * match with any user accounts or not. If there is a match, then the user has 
	 * successfully logged in and can access the main menu.
	 */
	public boolean validLogin()
	{
		boolean valid = (model.classes.DbReader.findEntry(
				model.classes.DbWriter.wrapUserInfo(userID.getText(), passID.getText()),
				model.classes.DbReader.readArray("src/model/data/userdata.json"))
				!= null);
		if(!valid)
			displayErrorMessage();
		else
			hideErrorMessage();
		return valid;
	}
	
	
	/*
	 * This method opens a new window to create a new account. It will have a 
	 * text field for a username and password along with a button to submit and cancel.
	 */
	public void createNewAccountButton(ActionEvent event) throws IOException
	{
		AnchorPane createNewAccPane = (AnchorPane)FXMLLoader.load(getClass().getResource("../view/fxml/CreateNewAccount.fxml"));
		Scene createNewAccScene = new Scene(createNewAccPane,300,200);
		createNewAccScene.getStylesheets().add(getClass().getResource("../application/application.css").toExternalForm());
		
		if(createNewAccStage == null || !createNewAccStage.isShowing())
		{
			createNewAccStage = new Stage();
			createNewAccStage.setScene(createNewAccScene);
			createNewAccStage.show();
		}
		else
			createNewAccStage.toFront();
	}
}
