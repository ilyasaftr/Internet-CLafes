package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class Connect {

	private final String USERNAME = "root";
	private final String PASSWORD = "";

	private final String DATABASE = "internet-clafes";

	private final String HOST = "localhost:3306";
	private final String CONNECTION = String.format("jdbc:mysql//%s/%s", HOST, DATABASE);

	public ResultSet rs;
	public ResultSetMetaData rsm;

	private Statement st;
	
	private Connection con;
	private static volatile Connect connect;

	private Connect() {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static Connect getInstance() {
		if(connect == null) {
			synchronized (Connect.class) {
				if(connect == null) {
					connect = new Connect();
				}
			}
		}
		
		return connect;
	}
	
	public ResultSet execQuery(String query) {
		try {
			rs = st.executeQuery(query);
			rsm = rs.getMetaData();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rs;
	}
	
	public void execUpdate(String query) {
		try {
			st.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public PreparedStatement prepareStatement(String query) {
		PreparedStatement ps = null;
		
		try {
			ps = con.prepareStatement(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return ps;
	}

}
