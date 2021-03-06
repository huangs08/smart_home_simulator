package controllers;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import java.io.File;
import com.google.gson.Gson;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import models.RoomModel;
import models.HouseRoomsModel;
import models.InvalidFileTypeException;
import models.InvalidHouseLayoutException;
import java.util.Scanner;

/**
 * This class acts as a controller for the houseLayout.fxml
 * It allows users to load a house layout
 * @author Team 4
 *
 */
public class HouseLayoutController {

	@FXML private JFXButton continueButton;
	private String pathToFile = null;
	@FXML private Label pathToFileLabel;
	private File chosenFile;
	private Main mainController;

	/**
	 * This method sets the controller provided in the parameter to replace the
	 * main controller so that it can get access to all the users from the user model.
	 * @param mainController controller that will replace the main controller
	 */
	public void setMainController(Main mainController) {
		this.mainController = mainController;
	}

	/**
	 * This method handles when the upload button is clicked. It will prompt a window to
	 * let choose a file in the computer system.
	 * @param mouseEvent on mouse click
	 */
	public void onUploadClick(MouseEvent mouseEvent) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Upload House Layout File");
		File chosenFile = fileChooser.showOpenDialog(null);
		if(chosenFile != null) {
			pathToFile = chosenFile.getAbsolutePath();
			pathToFileLabel.setText(pathToFile);
			continueButton.setDisable(false);
		}
	}

	/**
	 * This method will read the file that is chosen to be uploaded. It creates a string that will be
	 * a replica of the file.
	 * @param url path to the file
	 * @return jsonString that represents the file
	 */
	public String readFromFile(String url){
		String jsonString="";
		try{
			File file = new File(url);
			Scanner readFile = new Scanner(file);
			while(readFile.hasNextLine()){
				jsonString +=readFile.nextLine()+"\r\n";
			}
			readFile.close();
			return jsonString;
		} catch (Exception e) {
			System.out.println("The file can not be found.");
		}
		return null;
	}

	/**
	 * This method will extract the information from the json and place it in the room model array.
	 * @param jsonText string to be read
	 * @return array from room model that contains all the rooms in the house layout file.
	 * @throws InvalidHouseLayoutException invalid house layout file
	 */
	public RoomModel[] extractFromJson(String jsonText) throws InvalidHouseLayoutException {
		try {
			RoomModel[] arrayRoomModel = new Gson().fromJson(jsonText, RoomModel[].class);
			if (arrayRoomModel.length == 0) {
				throw new Exception();
			}
			return arrayRoomModel;			
		}
		catch (Exception e) {
			throw new InvalidHouseLayoutException();
		}
	}

	/**
	 * This method extracts the information from the file provided and creates data
	 * in the Room model. It will then make the window switch to the simulation parameters window.
	 * @param mouseEvent on mouse click
	 */
	public void onContinueClick(MouseEvent mouseEvent) {
		try {
			if (!pathToFile.endsWith(".txt")) {
				throw new InvalidFileTypeException(".txt");
			}
			else {
				String jsonString = readFromFile(pathToFile);
				RoomModel[] allRoomModels = extractFromJson(jsonString);
				HouseRoomsModel.getInstance().setAllRooms(allRoomModels);
				mainController.closeWindow();
				mainController.setSimulationParametersWindow();
			}
		}
		catch (InvalidFileTypeException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Invalid File Type");
			alert.setHeaderText("Invalid File Type Error");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
		catch (InvalidHouseLayoutException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Invalid House Layout");
			alert.setHeaderText("Invalid House Layout Error");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}