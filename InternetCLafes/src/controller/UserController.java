package controller;

import java.util.Vector;

import dao.UserModel;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableSelectionModel;
import model.User;
import view.Login.LoginVar;
import view.Register.RegisterVar;
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
	
	public void addRegisterHandlers(RegisterVar rv) {
		rv.btnRegister.setOnAction(e -> {
			if(rv.UserNameTf.getText().isBlank()) {
				rv.alert.setContentText("Username must not be empty");
				rv.alert.showAndWait();
			}
			else if(rv.UserNameTf.getText().length() < 7) {
				rv.alert.setContentText("Username must be at least 7 characters long");
				rv.alert.showAndWait();
			}
			else if(checkExist(rv.UserNameTf.getText())) {
				rv.alert.setContentText("Username must be unique");
				rv.alert.showAndWait();
			}
			else if(rv.PasswordTf.getText().length() < 6) {
				rv.alert.setContentText("Password must be at least 6 characters long");
				rv.alert.showAndWait();
			}
			else if(!checkAlphaNum(rv.PasswordTf.getText())) {
				rv.alert.setContentText("Password must be alphanumeric");
				rv.alert.showAndWait();
			}
			else if(rv.PasswordTf.getText().isBlank()) {
				rv.alert.setContentText("Password must not be empty");
				rv.alert.showAndWait();
			}
			else if(rv.ConfirmPasswordTf.getText().isBlank()) {
				rv.alert.setContentText("Confirm password must not be empty");
				rv.alert.showAndWait();
			}
			else if(!rv.ConfirmPasswordTf.getText().equals(rv.PasswordTf.getText())) {
				rv.alert.setContentText("Confirm password must be the same as password");
				rv.alert.showAndWait();
			}
			else {
				addNewUser(rv.UserNameTf.getText(), rv.PasswordTf.getText(), rv.UserAgeSpinner.getValue());
				
				PageController pc = PageController.getInstance();
				pc.redirectToLogin();
			}
			
			
		});
	}

	public void addLoginHandlers(LoginVar lv) {
		lv.btnLogin.setOnAction(e -> {
			if(lv.UserNameTf.getText().isBlank()) {
				lv.alert.setContentText("Username must not be empty");
				lv.alert.showAndWait();
			}
			else if(!checkExist(lv.UserNameTf.getText())) {
				lv.alert.setContentText("Username does not exist");
				lv.alert.showAndWait();
			}
			else if(lv.PasswordTf.getText().isBlank()) {
				lv.alert.setContentText("Password must not be empty");
				lv.alert.showAndWait();
			}
			else if(getUserData(lv.UserNameTf.getText(), lv.PasswordTf.getText()) == null) {
				lv.alert.setContentText("Password does not match with username");
				lv.alert.showAndWait();
			}
			else {
				
				
				PageController pc = PageController.getInstance();
				
			}
		});
	}
	
	private User getUserData(String username, String password) {
		return userModel.getUserData(username, password);
	}

	public void addNewUser(String username, String password, Integer age) {
		userModel.addNewUser(username, password, age);
	}

	private boolean checkAlphaNum(String text) {
		for (int i = 0; i < text.length(); ++i) {
		    char charAt = text.charAt(i);
		    if (!isAlphanumeric(charAt)) {
		        return false;
		    }
		}
		
		return true;
	}

	private boolean isAlphanumeric(char charAt) {
		return ('A' <= charAt && charAt <= 'Z' || 
				'a' <= charAt && charAt <= 'z' || 
				'0' <= charAt && charAt <= '9');
	}

	private boolean checkExist(String username) {
		for(User user : getAllUserData()) {
			if(user.getUserName().equals(username)) {
				return true;
			}
		}
		
		return false;
	}
}
