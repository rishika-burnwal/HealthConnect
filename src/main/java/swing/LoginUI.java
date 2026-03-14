package swing;

import javax.swing.*;
import java.awt.*;
import database.*;

public class LoginUI extends JFrame {

    JTextField emailField;
    JPasswordField passwordField;

    public LoginUI() {

        setTitle("Login");
        setSize(400,350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6,1,10,10));
        panel.setBorder(BorderFactory.createEmptyBorder(30,40,30,40));

        emailField = new JTextField();
        passwordField = new JPasswordField();

        JButton loginBtn = new JButton("Login");
        JButton signupBtn = new JButton("Signup");

        panel.add(new JLabel("Email"));
        panel.add(emailField);
        panel.add(new JLabel("Password"));
        panel.add(passwordField);
        panel.add(loginBtn);
        panel.add(signupBtn);

        add(panel);

        loginBtn.addActionListener(e -> login());
        signupBtn.addActionListener(e -> new SignupUI());

        setVisible(true);
    }

    private void login() {
        UserDA0 dao = new UserDA0();
        User user = dao.login(
                emailField.getText(),
                new String(passwordField.getPassword())
        );

        if (user != null) {
            Session.userId = user.getId();
            Session.userName = user.getFullName();

            new WebUI();
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,"Invalid Credentials");
        }
    }
}