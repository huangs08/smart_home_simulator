package models;

import javafx.scene.control.ComboBox;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * This class stores the data about each user profile
 * @author Team 4
 *
 */
public class UserModel implements Subject {

	private String name;
	private String role;
	private transient String currentLocation;	//current location of the user in the house
	private transient LocalTime time;
	private transient LocalDate date;
	private transient ComboBox<String> locationOptions;	//comboBox storing possible user locations (for edit context of simulation)
	private transient List<Observer> listOfObservers;
	private transient String season;
	private transient String seasonStart;
	private transient String seasonEnd;

	/**
	 * Getter to obtain the time
	 * @return time
	 */
	public LocalTime getTime() {
		return time;
	}

	/**
	 * Setter to set the time
	 * @param time LocalTime
	 */
	public void setTime(LocalTime time) {
		this.time = time;
	}

	/**
	 * Getter to obtain the date
	 * @return date
	 */
	public LocalDate getDate() {
		return date;
	}

	/**
	 * Setter to set the date
	 * @param date localDate
	 */
	public void setDate(LocalDate date) {
		this.date = date;
	}

	/**
	 * Constructor to set the name and permission of the user profile
	 * @param name name of the user profile
	 * @param role permission of the user profile
	 */
	public UserModel(String name, String role) {
		this.name = name;
		this.role = role;
	}

	/**
	 * Getter to get the name of the user profile
	 * @return name String
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter to set the name of the user profile
	 * @param name String
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter to get the season
	 * @return season String
	 */
	public String getSeason() {
		return season;
	}

	/**
	 * Setter to set the season
	 * @param season string
	 */
	public void setSeason(String season) {
		this.season = season;
	}

	/**
	 * Getter to get the season start
	 * @return season start
	 */
	public String getSeasonStart() {
		return seasonStart;
	}

	/**
	 * Setter to set the season start
	 * @param seasonStart String season start
	 */
	public void setSeasonStart(String seasonStart) {
		this.seasonStart = seasonStart;
	}

	/**
	 * Getter to get the season end
	 * @return seasonEnd string
	 */
	public String getSeasonEnd() {
		return seasonEnd;
	}

	/**
	 * Setter to set the season end
	 * @param seasonEnd string
	 */
	public void setSeasonEnd(String seasonEnd) {
		this.seasonEnd = seasonEnd;
	}

	/**
	 * Getter to obtain the ComboBox containing all possible rooms of the house
	 * @return ComboBox of all option
	 */
	public ComboBox<String> getLocationOptions() {
		return locationOptions;
	}

	/**
	 * Setter to set the ComboBox containing all possible rooms of the house that the user can be placed in
	 * @param locationComboBox of all location
	 */
	public void setLocationOptions(ComboBox<String> locationComboBox) {
		this.locationOptions = locationComboBox;
	}

	/**
	 * Getter to get the permission/role of the user profile
	 * @return role/permission
	 */
	public String getRole() {
		return role;
	}

	/**
	 * Setter to set the permission/role of the user profile
	 * @param role string
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * Getter to obtain the name and the role of the user profile
	 * @return name and role
	 */
	public String getNameAndRole(){ 
		return name+" : "+ role;
	}

	/**
	 * Getter to obtain the current location in the house of the user profile
	 * @return room location of the user profile
	 */
	public String getCurrentLocation() {
		return currentLocation;
	}

	/**
	 * Setter to set the current location in the house of the user profile
	 * @param currentLocation string
	 */
	public void setCurrentLocation(String currentLocation) {
		this.currentLocation = currentLocation;
		if (listOfObservers != null) {
			notifyObservers();
		}
	}
	
	/**
	 * Getter to obtain the list containing all observers
	 * @return list of observers
	 */
	public List<Observer> getListOfObservers() {
		return listOfObservers;
	}

	/**
	 * Setter to set the list of observers
	 * @param listOfObservers list 
	 */
	public void setListOfObservers(List<Observer> listOfObservers) {
		this.listOfObservers = listOfObservers;
	}

	/**
	 * Adds an observer to the list of observers
	 */
	@Override
	public void registerObserver(Observer observer) {
		listOfObservers.add(observer);
	}

	/**
	 * Removes an observer from the list of observers
	 */
	@Override
	public void unregisterObserver(Observer observer) {
		listOfObservers.remove(observer);		
	}

	/**
	 * Notifies observers that a change occurred in the subject
	 */
	@Override
	public void notifyObservers() {
		for (Observer observer : listOfObservers) {
			observer.update(this);			
		}
	}
}
