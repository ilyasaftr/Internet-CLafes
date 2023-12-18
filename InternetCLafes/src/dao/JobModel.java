package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import database.Connect;
import model.Job;

public class JobModel {
	// JobModel menerapkan Singleton agar instance yang digunakan hanya satu dalam seluruh app.
	
	public static volatile JobModel instance = null;
	
	private JobModel() {
		
	}
	
	public static JobModel getInstance() {
		if(instance == null) {
			synchronized (JobModel.class) {
				if(instance == null) {
					 instance = new JobModel();
				}
			}
		}
		
		return instance;
	}

	// Mendapatkan semua data job dari database
	public Vector<Job> getAllJobData() {
		Vector<Job> jobList = new Vector<>();

		Connect con = Connect.getInstance();
		String query = "SELECT * FROM `job`";

		ResultSet rs = con.execQuery(query);

		try {
			while(rs.next()) {
				Integer Job_ID = rs.getInt("Job_ID");
				Integer UserID = rs.getInt("UserID");
				Integer PC_ID = rs.getInt("PC_ID");
				String JobStatus = rs.getString("JobStatus");

				jobList.add(new Job(Job_ID, UserID, PC_ID, JobStatus));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return jobList;
	}
	
	// Menambahkan job baru ke database
	public void addNewJob(Integer UserID, Integer PcID) {
		Connect con = Connect.getInstance();
		
		String query = "INSERT INTO `job`(`UserID`, `PC_ID`, `JobStatus`) VALUES (?,?,?)";
		
		PreparedStatement ps = con.prepareStatement(query);
		
		try {
			ps.setInt(1, UserID);
			ps.setInt(2, PcID);
			
			// secara default, job status akan uncomplete ketika tambah job
			ps.setString(3, "UnComplete");
			
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// Memperbarui status job
	public void updateJobStatus(Integer JobID, String JobStatus) {
		Connect con = Connect.getInstance();
		
		String query = "UPDATE `job` SET `JobStatus` = ? WHERE `Job_ID` = ?;";
		
		PreparedStatement ps = con.prepareStatement(query);
		
		try {
			ps.setString(1, JobStatus);
			ps.setInt(2, JobID);
			
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Mendapatkan job dari technician dari userID
	public Vector<Job> getTechnicianJob(Integer userID) {
		Vector<Job> jobList = new Vector<>();

		Connect con = Connect.getInstance();
		
		String query = "SELECT * FROM `job` WHERE `UserID` = ?";
		PreparedStatement ps = con.prepareStatement(query);

		try {
			ps.setInt(1, userID);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				Integer Job_ID = rs.getInt("Job_ID");
				Integer UserID = rs.getInt("UserID");
				Integer PC_ID = rs.getInt("PC_ID");
				String JobStatus = rs.getString("JobStatus");

				jobList.add(new Job(Job_ID, UserID, PC_ID, JobStatus));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return jobList;
	}
	
	// Mendapatkan job dari job ID
	public Job getJobByJobID(Integer jobID) {
		Vector<Job> jobList = new Vector<>();

		Connect con = Connect.getInstance();
		
		String query = "SELECT * FROM `job` WHERE `Job_ID` = ?";
		PreparedStatement ps = con.prepareStatement(query);

		try {
			ps.setInt(1, jobID);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				Integer Job_ID = rs.getInt("Job_ID");
				Integer UserID = rs.getInt("UserID");
				Integer PC_ID = rs.getInt("PC_ID");
				String JobStatus = rs.getString("JobStatus");

				jobList.add(new Job(Job_ID, UserID, PC_ID, JobStatus));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return (jobList.isEmpty())? null: jobList.firstElement();
	}
	
	// Mendapatkan job yang UnComplete dari PC ID
	public Vector<Job> getJobByPCID(Integer pcID) {
		Vector<Job> jobList = new Vector<>();

		Connect con = Connect.getInstance();
		
		String query = "SELECT * FROM `job` WHERE `PC_ID` = ? AND  `JobStatus` = 'UnComplete';";
		PreparedStatement ps = con.prepareStatement(query);

		try {
			ps.setInt(1, pcID);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				Integer Job_ID = rs.getInt("Job_ID");
				Integer UserID = rs.getInt("UserID");
				Integer PC_ID = rs.getInt("PC_ID");
				String JobStatus = rs.getString("JobStatus");

				jobList.add(new Job(Job_ID, UserID, PC_ID, JobStatus));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return jobList;
	}
}
