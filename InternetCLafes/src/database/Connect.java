package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class Connect {

	/*
	 * USERNAME dan PASSWORD digunakan untuk menyimpan kredensial yang dibutuhkan untuk mengautentikasi koneksi MySQL
	 *
	 * Sedangkan, DATABASE dan HOST digunakan untuk membuat connection string.
	 */
	private final String USERNAME = "root";
	private final String PASSWORD = "";

	private final String DATABASE = "internet-clafes";

	private final String HOST = "localhost:3306";
	private final String CONNECTION = String.format("jdbc:mysql://%s/%s", HOST, DATABASE);

	/*
	 * ResultSet digunakan untuk menyimpan hasil query SELECT,
	 * sedangkan ResultSetMetaData berisi metadata dari ResultSet
	 * sendiri.
	 */
	public ResultSet rs;
	public ResultSetMetaData rsm;

	/*
	 * Statement digunakan untuk menjalankan SQL query.
	 */
	private Statement st;

	/*
	 * Connection digunakan untuk menyimpan connection dengan JDBC Driver yang dipakai.
	 */
	private Connection con;

	/*
	 * Singleton diterapkan pada class ini agar connection database
	 * yang dibuat hanya satu.
	 */
	private static volatile Connect connect;

	private Connect() {

		/*
		 * Code di bawah digunakan untuk menyiapkan JDBC Driver
		 * yang akan digunakan.
		 */

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
			st = con.createStatement();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// Apabila sudah ada instance Connect, getInstance akan mengembalikan instance tersebut.
	// Namun, kalau belum ada, dia akan menciptakan instance baru.
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

	// execQuery akan digunakan dalam kasus SELECT dan mengembalikan hasil query dalam bentuk ResultSet.
	public ResultSet execQuery(String query) {
		try {
			rs = st.executeQuery(query);
			rsm = rs.getMetaData();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rs;
	}

	// execUpdate akan digunakan dalam kasus UPDATE, DELETE, dan INSERT.
	// Dia tidak mengembalikan apa-apa.
	public void execUpdate(String query) {
		try {
			st.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// prepareStatement digunakan untuk membuat prepareStatement,
	// yakni statement yang dapat menerima berbagai parameter.
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
