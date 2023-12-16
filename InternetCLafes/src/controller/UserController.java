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
	// User Controller untuk menambahkan handler untuk event CRUD User (misal pada login dan register)
	// User Controller menggunakan singleton agar satu instance saja yang terpakai
	
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
	
	// Akses user model untuk akses database
	UserModel userModel = UserModel.getInstance();
	
	// mendapatkan semua data user
	public Vector<User> getAllUserData() {
		return userModel.getAllUserData();
	}
	
	// update user role
	public void changeUserRole(Integer UserID, String NewRole) {
		userModel.changeUserRole(UserID, NewRole);
	}
	
	// untuk mendapatkan list berisi data semua Computer Technician yang terdaftar di database
	public Vector<User> getAllTechnician(){
		return userModel.getAllTechnician();
	}
	
	// handler event change user role (mouse click table dan button change role)
	public void addChangeRoleHandler(ViewAllStaffVar components) {
		// mengatur logic kalau ada record di table staff yang di select
		components.staffTable.setOnMouseClicked(e -> {
			TableSelectionModel<User> tableSelectionModel = components.staffTable.getSelectionModel();
			
			tableSelectionModel.setSelectionMode(SelectionMode.SINGLE);
			
			// menampung selected item dari table
			User user = tableSelectionModel.getSelectedItem();
			
			// kalau user ada yang di-select
			if(user != null) {
				
				// set text field user id ke id user yang di-select
				components.userIDTf.setText(user.getUserID().toString());
				
				// kalau Admin role user yang di-select,
				// set text dropdown ke Admin
				if(user.getUserRole().equals("Admin")) {
					components.userRoleCB.getSelectionModel().select("Admin");
				}
				// kalau Operator role user yang di-select,
				// set text dropdown ke Operator
				else if(user.getUserRole().equals("Operator")) {
					components.userRoleCB.getSelectionModel().select("Operator");
				}
				// kalau Computer Technician role user yang di-select,
				// set text dropdown ke Computer Technician
				else if(user.getUserRole().equals("Computer Technician")) {
					components.userRoleCB.getSelectionModel().select("Computer Technician");
				}
				// kalau Customer role user yang di-select,
				// set text dropdown ke Customer
				else if(user.getUserRole().equals("Customer")) {
					components.userRoleCB.getSelectionModel().select("Customer");
				}
				
			}
		});
		
		// atur logic kalau button change role ditekan
		components.btnChangeRole.setOnAction(e -> {
			// kalau text field user id kosong atau tidak ada row staff yang di-select
			if(components.userIDTf.getText().isBlank()) {
				components.noRowAlert.showAndWait();
			}
			// kalau role kosong
			else if(components.userRoleCB.getValue().isBlank()) {
				components.invalidDataAlert.showAndWait();
			}
			// kalau valid datanya, update data user melalui changeUserRole method dan refresh data pada tabel
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
	
	// menangani event button register diclick
	public void addRegisterHandlers(RegisterVar rv) {
		rv.btnRegister.setOnAction(e -> {
			// kalau username kosong
			if(rv.UserNameTf.getText().isBlank()) {
				rv.alert.setContentText("Username must not be empty");
				rv.alert.showAndWait();
			}
			// kalau username tidak min. 7 char
			else if(rv.UserNameTf.getText().length() < 7) {
				rv.alert.setContentText("Username must be at least 7 characters long");
				rv.alert.showAndWait();
			}
			// kalau username tidak unik
			else if(checkExist(rv.UserNameTf.getText())) {
				rv.alert.setContentText("Username must be unique");
				rv.alert.showAndWait();
			}
			// kalau password tidak min. 6 char
			else if(rv.PasswordTf.getText().length() < 6) {
				rv.alert.setContentText("Password must be at least 6 characters long");
				rv.alert.showAndWait();
			}
			// kalau password tidak alpha numeric
			else if(!checkAlphaNum(rv.PasswordTf.getText())) {
				rv.alert.setContentText("Password must be alphanumeric");
				rv.alert.showAndWait();
			}
			// kalau password kosong
			else if(rv.PasswordTf.getText().isBlank()) {
				rv.alert.setContentText("Password must not be empty");
				rv.alert.showAndWait();
			}
			// kalau confirm password kosong
			else if(rv.ConfirmPasswordTf.getText().isBlank()) {
				rv.alert.setContentText("Confirm password must not be empty");
				rv.alert.showAndWait();
			}
			// kalau confirm password tidak sama isinya dengan password
			else if(!rv.ConfirmPasswordTf.getText().equals(rv.PasswordTf.getText())) {
				rv.alert.setContentText("Confirm password must be the same as password");
				rv.alert.showAndWait();
			}
			// kalau valid, insert User baru ke database, terus redirect ke login
			else {
				addNewUser(rv.UserNameTf.getText(), rv.PasswordTf.getText(), rv.UserAgeSpinner.getValue());
				
				PageController pc = PageController.getInstance();
				pc.redirectToLogin();
			}
			
			
		});
	}

	// atur validasi login
	public void addLoginHandlers(LoginVar lv) {
		lv.btnLogin.setOnAction(e -> {
			// cari user yang corresponding dengan username dan password
			User user = getUserData(lv.UserNameTf.getText(), lv.PasswordTf.getText());
			
			// kalau username kosong
			if(lv.UserNameTf.getText().isBlank()) {
				lv.alert.setContentText("Username must not be empty");
				lv.alert.showAndWait();
			}
			// kalau username tidak ada di database
			else if(!checkExist(lv.UserNameTf.getText())) {
				lv.alert.setContentText("Username does not exist");
				lv.alert.showAndWait();
			}
//			kalau password kosong
			else if(lv.PasswordTf.getText().isBlank()) {
				lv.alert.setContentText("Password must not be empty");
				lv.alert.showAndWait();
			}
//			kalau tidak ada user record di database
			else if(user == null) {
				lv.alert.setContentText("Password does not match with username");
				lv.alert.showAndWait();
			}
			// kalau valid, redirect ke home page dan masukkan role sebagai parameter
			else {
				PageController pc = PageController.getInstance();
				pc.redirectToHome(user);
			}
		});
	}
	
	// dapatkan user record dari database berdasarkan username dan password
	private User getUserData(String username, String password) {
		return userModel.getUserData(username, password);
	}

	// insert user baru ke database
	public void addNewUser(String username, String password, Integer age) {
		userModel.addNewUser(username, password, age);
	}

	// cek apa string isinya alphanumeric char saja
	private boolean checkAlphaNum(String text) {
		for (int i = 0; i < text.length(); ++i) {
		    char charAt = text.charAt(i);
		    if (!isAlphanumeric(charAt)) {
		        return false;
		    }
		}
		
		return true;
	}

	// kalau selain a-z, A-Z, 0-9, bukan alphanumeric
	private boolean isAlphanumeric(char charAt) {
		return ('A' <= charAt && charAt <= 'Z' || 
				'a' <= charAt && charAt <= 'z' || 
				'0' <= charAt && charAt <= '9');
	}

	// cek apa ada record user yang sama username-nya
	private boolean checkExist(String username) {
		for(User user : getAllUserData()) {
			if(user.getUserName().equals(username)) {
				return true;
			}
		}
		
		return false;
	}
}
