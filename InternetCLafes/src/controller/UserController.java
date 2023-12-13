package controller;

import java.util.Vector;

import dao.UserModel;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableSelectionModel;
import model.User;
import view.ViewAllStaff.ViewAllStaffVar;


public class UserController {
	public static volatile UserController instance = null;
	
	private UserController() {
		
	}
	
	public static UserController getInstance() {
		if(instance == null) {
			synchronized (UserController.class) {
				if(instance == null) {
					 instance = new UserController();
				}
			}
		}
		
		return instance;
	}
	
	UserModel userModel = UserModel.getInstance();
	
	public Vector<User> getAllUserData() {
		return userModel.getAllUserData();
	}
	
	public void changeUserRole(Integer UserID, String NewRole) {
		userModel.changeUserRole(UserID, NewRole);
	}
	
	public void addChangeRoleHandler(ViewAllStaffVar components) {
		components.staffTable.setOnMouseClicked(e -> {
			TableSelectionModel<User> tableSelectionModel = components.staffTable.getSelectionModel();
			
			tableSelectionModel.setSelectionMode(SelectionMode.SINGLE);
			
			User user = tableSelectionModel.getSelectedItem();
			
			if(user != null) {
				components.userIDTf.setText(user.getUserID().toString());
				
				if(user.getUserRole().equals("Admin")) {
					components.userRoleCB.getSelectionModel().select("Admin");
				}
				else if(user.getUserRole().equals("Operator")) {
					components.userRoleCB.getSelectionModel().select("Operator");
				}
				else if(user.getUserRole().equals("Computer Technician")) {
					components.userRoleCB.getSelectionModel().select("Computer Technician");
				}
				else if(user.getUserRole().equals("Customer")) {
					components.userRoleCB.getSelectionModel().select("Customer");
				}
				
			}
		});
		
		components.btnChangeRole.setOnAction(e -> {
			if(components.userIDTf.getText().isBlank()) {
				components.noRowAlert.showAndWait();
			}
			else if(components.userRoleCB.getValue().isBlank()) {
				components.invalidDataAlert.showAndWait();
			}
			else {
				changeUserRole(Integer.parseInt(components.userIDTf.getText()), components.userRoleCB.getValue());
				components.userList.clear();
				getData(components);
			}
		});
	}
	
	/*
	 * getData digunakan untuk mendapatkan data staff semua yang akan dimasukkan ke tabel
	 */
	public void getData(ViewAllStaffVar components) {
		components.userList = getAllUserData();
		components.staffTable.getItems().clear();

		for(User user : components.userList) {  
			if(user.getUserRole().equals("Admin") || user.getUserRole().equals("Operator") || user.getUserRole().equals("Computer Technician")) {
				components.staffTable.getItems().add(user);
			}
		}
	}
}
