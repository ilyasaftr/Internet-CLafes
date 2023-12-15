package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import database.Connect;
import model.PC;
import model.User;

public class PCModel {
	// PCModel menerapkan Singleton agar instance yang digunakan hanya satu dalam seluruh app.
	
	public static volatile PCModel instance = null;
	
	private PCModel() {
		
	}
	
	public static PCModel getInstance() {
		if(instance == null) {
			synchronized (PCModel.class) {
				if(instance == null) {
					 instance = new PCModel();
				}
			}
		}
		
		return instance;
	}

	// Method untuk mendapatkan list berisi semua PC data
	public static Vector<PC> getAllPCData() {
		Vector<PC> pcList = new Vector<>();

		Connect con = Connect.getInstance();
		String query = "SELECT * FROM `pc`";

		ResultSet rs = con.execQuery(query);

		try {
			while(rs.next()) {
				Integer PC_ID = rs.getInt(1);
				String PC_Condition = rs.getString(2);

				pcList.add(new PC(PC_ID, PC_Condition));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return pcList;
	}
}
