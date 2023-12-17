package model;

public class User {

	private Integer UserID, UserAge;
	
	// User role must be either “Admin”, “Customer”, “Operator”, or “Computer Technician”
	private String UserName, UserPassword, UserRole;

	public User(Integer userID, Integer userAge, String userName, String userPassword, String userRole) {
		super();
		UserID = userID;
		UserAge = userAge;
		UserName = userName;
		UserPassword = userPassword;
		UserRole = userRole;
	}

	public Integer getUserID() {
		return UserID;
	}

	public void setUserID(Integer userID) {
		UserID = userID;
	}

	public Integer getUserAge() {
		return UserAge;
	}

	public void setUserAge(Integer userAge) {
		UserAge = userAge;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getUserPassword() {
		return UserPassword;
	}

	public void setUserPassword(String userPassword) {
		UserPassword = userPassword;
	}

	public String getUserRole() {
		return UserRole;
	}

	public void setUserRole(String userRole) {
		UserRole = userRole;
	}


}
