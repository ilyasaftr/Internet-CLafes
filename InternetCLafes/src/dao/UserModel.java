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

	// untuk update user role ke database
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

	// untuk mendapatkan list berisi data semua user yang terdaftar di database
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

	// untuk meng-insert user baru ke database
	public void addNewUser(String username, String password, Integer age) {
		Connect con = Connect.getInstance();
		
		String query = "INSERT INTO `user`(`UserName`, `UserPassword`, `UserAge`, `UserRole`) VALUES (?, ?, ?, ?)";
		
		PreparedStatement ps = con.prepareStatement(query);
		
		try {
			ps.setString(1, username);
			ps.setString(2, password);
			ps.setInt(3, age);
			
			// Customer sebagai role default
			ps.setString(4, "Customer");
			
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Digunakan untuk mendapatkan data user dengan username dan password yang diketahui
	public User getUserData(String username, String password) {
		Vector<User> userList = new Vector<>();
		
		Connect con = Connect.getInstance();
		
		// untuk filtering data User berdasarkan username dan password
		String query = "SELECT * FROM `user` WHERE `UserName` = ? AND `UserPassword` = ?";

		PreparedStatement ps = con.prepareStatement(query);
		ResultSet rs;
		
		try {
			ps.setString(1, username);
			ps.setString(2, password);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				Integer UserID = rs.getInt(1);
				String UserName = rs.getString(2);
				String UserPassword = rs.getString(3);
				Integer UserAge = rs.getInt(4);
				String UserRole = rs.getString(5);

				userList.add(new User(UserID, UserAge, UserName, UserPassword, UserRole));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return (userList.isEmpty())? null: userList.firstElement();
	}
}
