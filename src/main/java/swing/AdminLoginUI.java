package swing;

import database.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class AdminLoginUI extends JFrame {

    JTextField usernameField;
    JPasswordField passwordField;

    public AdminLoginUI(){

        setTitle("Admin Login");

        setSize(400,300);

        setLocationRelativeTo(null);

        setLayout(null);


        JLabel title = new JLabel("Admin Login");

        title.setFont(new Font("Segoe UI",Font.BOLD,22));

        title.setBounds(130,20,200,30);

        add(title);



        JLabel userLabel = new JLabel("Username");

        userLabel.setBounds(50,80,100,25);

        add(userLabel);


        usernameField = new JTextField();

        usernameField.setBounds(150,80,180,25);

        add(usernameField);



        JLabel passLabel = new JLabel("Password");

        passLabel.setBounds(50,120,100,25);

        add(passLabel);


        passwordField = new JPasswordField();

        passwordField.setBounds(150,120,180,25);

        add(passwordField);



        JButton loginBtn = new JButton("Login");

        loginBtn.setBounds(140,180,120,30);

        add(loginBtn);



        loginBtn.addActionListener(e -> login());


        setVisible(true);

    }



    void login(){

        String username = usernameField.getText();

        String password = new String(passwordField.getPassword());

        try{

            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM admin WHERE username=? AND password=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1,username);

            ps.setString(2,password);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){

                JOptionPane.showMessageDialog(this,"Login Successful");

                new AdminDashboardUI();

                dispose();

            }

            else{

                JOptionPane.showMessageDialog(this,"Invalid Credentials");

            }

        }

        catch(Exception e){

            e.printStackTrace();

        }

    }

}