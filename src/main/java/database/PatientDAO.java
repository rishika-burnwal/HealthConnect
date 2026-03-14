package database;

import java.sql.Connection;
import swing.Session;
import java.sql.PreparedStatement;
import java.sql.*;
import java.util.*;

public class PatientDAO {

	public void savePatient(Patient p){

		try{

		Connection con = DBConnection.getConnection();


		// save patient table

		String patientSql =
				"INSERT INTO patient (doctor_id, doctor_name, patient_name, age, gender, phone, appointment_date, health_issue, user_id) VALUES (?,?,?,?,?,?,?,?,?)";

		PreparedStatement ps1 = con.prepareStatement(patientSql);

		ps1.setInt(1, p.getDoctorId());
		ps1.setString(2, p.getDoctorName());
		ps1.setString(3, p.getPatientName());
		ps1.setInt(4, p.getAge());
		ps1.setString(5, p.getGender());
		ps1.setString(6, p.getPhone());
		ps1.setString(7, p.getDate());
		ps1.setString(8, p.getHealthIssue());
		ps1.setInt(9, swing.Session.userId);

		ps1.executeUpdate();



		// save appointment table

		// fetch specialization and hospital from doctors table

		String spec = "";
		String hosp = "";

		PreparedStatement psFetch =
		con.prepareStatement(
		"SELECT d.hospital, s.name AS specialization " +
		"FROM doctors d " +
		"JOIN specialization s ON d.specialization_id = s.id " +
		"WHERE d.id = ?"
		);

		psFetch.setInt(1, p.getDoctorId());

		ResultSet rs = psFetch.executeQuery();

		if(rs.next())
		{
		spec = rs.getString("specialization");
		hosp = rs.getString("hospital");
		}



		// save appointment table

		String appointmentSql =
				"INSERT INTO appointment (doctor_id, doctor_name, specialization, hospital, patient_name, age, gender, health_issue, appointment_date, user_id) VALUES (?,?,?,?,?,?,?,?,?,?)";

		PreparedStatement ps2 =
		con.prepareStatement(appointmentSql);
		System.out.println("BOOKING Session.userId = " + Session.userId);

		ps2.setInt(1, p.getDoctorId());

		ps2.setString(2, p.getDoctorName());

		ps2.setString(3, spec);

		ps2.setString(4, hosp);

		ps2.setString(5, p.getPatientName());

		ps2.setInt(6, p.getAge());

		ps2.setString(7, p.getGender());

		ps2.setString(8, p.getHealthIssue());

		ps2.setString(9, p.getDate());
		ps2.setInt(10, swing.Session.userId);

		ps2.executeUpdate();

		}
		catch(Exception e){

		e.printStackTrace();

		}
		}
	public List<Patient> getPatientsByUserId() {

	    List<Patient> list = new ArrayList<>();

	    try {

	        Connection con = DBConnection.getConnection();

	        String sql = "SELECT * FROM patient WHERE user_id = ?";

	        PreparedStatement ps = con.prepareStatement(sql);

	        ps.setInt(1, Session.userId);

	        ResultSet rs = ps.executeQuery();

	        while (rs.next()) {

	            Patient p = new Patient();

	            p.setDoctorId(rs.getInt("doctor_id"));
	            p.setDoctorName(rs.getString("doctor_name"));
	            p.setPatientName(rs.getString("patient_name"));
	            p.setAge(rs.getInt("age"));
	            p.setGender(rs.getString("gender"));
	            p.setPhone(rs.getString("phone"));
	            p.setDate(rs.getString("appointment_date"));
	            p.setHealthIssue(rs.getString("health_issue"));

	            list.add(p);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return list;
	}
}