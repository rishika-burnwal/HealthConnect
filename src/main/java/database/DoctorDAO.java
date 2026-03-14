package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DoctorDAO {

    public List<Doctor> getDoctorsBySpecialization(int specializationId) {

        List<Doctor> list = new ArrayList<>();

        String query = "SELECT * FROM doctors WHERE specialization_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            System.out.println("Specialization ID received: " + specializationId);

            ps.setInt(1, specializationId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Doctor d = new Doctor();

                d.setId(rs.getInt("id"));  // 🔥 VERY IMPORTANT
                d.setName(rs.getString("name"));
                d.setExperience(rs.getInt("experience"));
                d.setHospital(rs.getString("hospital"));
                d.setPhone(rs.getString("phone"));
                d.setImage(rs.getString("image"));
                d.setAvailability(rs.getString("availability"));

                list.add(d);
            }

            System.out.println("Doctors found: " + list.size());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
