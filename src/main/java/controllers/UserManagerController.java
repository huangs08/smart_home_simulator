package controllers;

import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import models.UserModel;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * This class is responsible for allowing the user of the simulator
 * to add other user profiles
 * @author Team 4
 *
 */
public class UserManagerController {

	@FXML private TableView tableView;
	@FXML private TableColumn tableColumnName;
	@FXML private TableColumn tableColumnStatus;
	@FXML private Label errorLabel;
	private Main mainController;
	private Stage currentStage;

	/**
	 * help to set the column of the table view with the attribute from the table list
	 */
	public void initialize(){
		tableColumnName.setCellValueFactory(new PropertyValueFactory<UserModel, String>("name"));
		tableColumnStatus.setCellValueFactory(new PropertyValueFactory<UserModel, String>("role"));
	}

	/**
	 * keep an instance of Main and currentStage
	 * set the table View with the Observable list
	 * @param maincontroller instance of Main
	 * @param currentstage current stage
	 */
	public void setMaincontroller(Main maincontroller, Stage currentstage) {
		this.mainController = maincontroller;
		this.currentStage = currentstage;
		tableView.setItems(mainController.getTempUserModelData());
	}
	/**
	 * cancel all the action that is done during user Manager window
	 * @param event button to cancel
	 */
	public void handleCancel(ActionEvent event) {
		currentStage.close();
	}

	/**
	 * helper class to create the Dialog of type User object so it can be use when user want to add or delete user
	 * @param editUserModel userModel to edit
	 * @return dialog of userModel
	 */
	public Dialog<UserModel> getDialog(UserModel editUserModel){
		String editName="";
		String editSta="";
		if(editUserModel !=null){
			editName = editUserModel.getName();
			editSta= editUserModel.getRole();
		}
		Dialog<UserModel> addDialog = new Dialog<>();
		addDialog.setTitle("Add/Edit a person");
		DialogPane dialogPane = addDialog.getDialogPane();
		dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
		TextField name = new TextField();
		name.setPromptText("User name");
		name.setText(editName);
		Label stat = new Label("Status");
		ComboBox<String> status = new ComboBox<>();
		status.getItems().addAll(
				"Parent",
				"Child",
				"Guest",
				"Stranger"
				);
		status.setValue(editSta);
		GridPane grid = new GridPane();
		grid.add(name, 1, 1);
		grid.add(status, 1, 2);
		dialogPane.setContent(grid);
		addDialog.setResultConverter((ButtonType button) -> {
			if (button == ButtonType.OK) {
				if(editUserModel !=null){
					mainController.getTempUserModelData().remove(editUserModel);
				}
				mainController.getTempUserModelData().add(new UserModel(name.getText(),status.getValue()));
			}
			return null;
		});
		return addDialog;
	}

	/**
	 * display the Dialog to add User
	 * @param event to add user
	 */
	public void handleAdd(ActionEvent event) {
		Dialog<UserModel> addDialog = getDialog(null);
		addDialog.showAndWait();
	}

	/**
	 * display the Dialog to Edit the existing user
	 * @param event to  edit user
	 */
	public void handleEdit(ActionEvent event) {
		UserModel selectedUser = (UserModel) tableView.getSelectionModel().getSelectedItem();
		if(selectedUser !=null){
			Dialog<UserModel> editDialog = getDialog(selectedUser);
			editDialog.showAndWait();
		}
		else{
			errorLabel.setText("*Cannot edit");
		}
	}

	/**
	 * Delete that is selected and remove from the list
	 * @param event to delete user
	 */
	public void handleDelete(ActionEvent event) {
		UserModel selectedUserModel = (UserModel) tableView.getSelectionModel().getSelectedItem();
		if(selectedUserModel !=null){
			mainController.getTempUserModelData().remove(selectedUserModel);
		}
		else{
			errorLabel.setText("Cannot delete");
		}
	}

	/**
	 * Save all the action in the window and update the PersonData observable list
	 * save all the user to text file to reuse in next launching app
	 * @param event to save all action
	 */
	public void handleSave(ActionEvent event) {
		mainController.getUserModelData().clear();
		mainController.getTempUserModelData().forEach((userModel)->{
			mainController.getUserModelData().add(userModel);
		});
		try {
			List<UserModel> all = mainController.getUserModelData();
			File file = new File("allUser.txt");
			PrintWriter pw = new PrintWriter(file);
			pw.print(new Gson().toJson(all));
			pw.flush();
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		currentStage.close();
	}
}
