package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import database.Connect;
import model.User;

public class UserModel {
	// UserModel menerapkan Singleton agar instance yang digunakan hanya satu dalam seluruh app.
	
	public static volatile UserModel instance = null;
	
	private UserModel() {
		
	}
	
	public static UserModel getInstance() {
		if(instance == null) {
			synchronized (UserModel.class) {
				if(instance == null) {
					 instance = new UserModel();
				}
			}
		}
		
		return instance;
	}

	public void changeUserRole(Integer userID, String newRole) {
		Connect con = Connect.getInstance();
		
		String query = "UPDATE `user` SET `UserRole`=? WHERE `UserID`=?";
		
		PreparedStatement ps = con.prepareStatement(query);
		
		
		try {
			ps.setString(1, newRole);
			ps.setInt(2, userID);
			
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Vector<User> getAllUserData() {
		Vector<User> userList = new Vector<>();

		Connect con = Connect.getInstance();
		String query = "SELECT * FROM `user`";

		ResultSet rs = con.execQuery(query);

		try {
			while(rs.next()) {
				Integer UserID = rs.getInt(1);
				String UserName = rs.getString(2);
				String UserPassword = rs.getString(3);
				Integer UserAge = rs.getInt(4);
				String UserRole = rs.getString(5);

				userList.add(new User(UserID, UserAge, UserName, UserPassword, UserRole));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return userList;
	}
}
