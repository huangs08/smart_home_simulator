package controllers;
import models.RoomModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * This class is responsible for loading the various rooms
 * obtained from the house layout file
 * @author Team 4
 *
 */
public class RoomController {

	private Main maincontroller;
	public ImageView window1;
	public ImageView door1;
	public ImageView light1;

	@FXML
	private AnchorPane anchorpaneroom1;
	@FXML private Label room1;

	private RoomModel room;

	/**
	 * Receives information about a room to display in the house layout tab
	 * @param room
	 */
	public void setData(RoomModel room){
		this.room = room;
		room1.setText(room.getName());
		window1.setImage(new Image("file:src/main/resources/images/closewindow.png"));
		light1.setImage(new Image("file:src/main/resources/images/lightoff.png"));
		door1.setImage(new Image("file:src/main/resources/images/closedoor.png"));

	}
}
