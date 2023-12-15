package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import database.Connect;
import model.PC;

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
	public Vector<PC> getAllPCData() {
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

	public PC getPCDetail(Integer pcID) {
		Vector<PC> pcList = new Vector<>();

		Connect con = Connect.getInstance();
		String query = "SELECT * FROM `pc` WHERE `PC_ID` = ?";
		PreparedStatement ps = con.prepareStatement(query);
		ResultSet rs;

		try {
			ps.setInt(1, pcID);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				Integer PC_ID = rs.getInt(1);
				String PC_Condition = rs.getString(2);

				pcList.add(new PC(PC_ID, PC_Condition));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return pcList.isEmpty()? null: pcList.firstElement();
	}

	public void addNewPC(int pcId) {
		Connect con = Connect.getInstance();
		
		String query = "INSERT INTO `pc`(`PC_ID`, `PC_Condition`) VALUES (?, ?)";
		
		PreparedStatement ps = con.prepareStatement(query);
		
		try {
			ps.setInt(1, pcId);
			
			// Usable sebagai condition default
			ps.setString(2, "Usable");
			
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void updatePCCondition(int PcID, String Condition) {
		Connect con = Connect.getInstance();
		
		String query = "UPDATE `pc` SET `PC_Condition`=? WHERE `PC_ID`=?";
		
		PreparedStatement ps = con.prepareStatement(query);
		
		try {
			ps.setString(1, Condition);
			ps.setInt(2, PcID);
			
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deletePC(int PcID) {
		Connect con = Connect.getInstance();
		
		String query = "DELETE FROM `pc` WHERE `PC_ID` = ?";
		
		PreparedStatement ps = con.prepareStatement(query);
		
		try {
			ps.setInt(1, PcID);
			
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
