package database;

import java.sql.*;
import database.DBConnection;

public class UserDA0 {

	public boolean register(User user) {

	    try {

	        Connection con = DBConnection.getConnection();

	        String sql = "INSERT INTO users(full_name,email,password) VALUES(?,?,?)";

	        PreparedStatement ps = con.prepareStatement(sql);

	        ps.setString(1, user.getFullName());
	        ps.setString(2, user.getEmail());
	        ps.setString(3, user.getPassword());

	        int rows = ps.executeUpdate();

	        if(rows > 0) {
	            return true;
	        }

	    }

	    catch (Exception e) {

	        e.printStackTrace();   // VERY IMPORTANT
	        System.out.println(e.getMessage());

	    }

	    return false;
	}

    public User login(String email, String password) {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM users WHERE email=? AND password=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setFullName(rs.getString("full_name"));
                return user;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}