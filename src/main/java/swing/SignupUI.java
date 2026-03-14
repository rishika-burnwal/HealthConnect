package swing;

import javax.swing.*;
import java.awt.*;
import database.*;


public class SignupUI extends JFrame {

    JTextField nameField, emailField;
    JPasswordField passwordField;

    public SignupUI() {

        setTitle("Signup");
        setSize(400,400);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(7,1,10,10));
        panel.setBorder(BorderFactory.createEmptyBorder(30,40,30,40));

        nameField = new JTextField();
        emailField = new JTextField();
        passwordField = new JPasswordField();

        JButton registerBtn = new JButton("Register");

        panel.add(new JLabel("Full Name"));
        panel.add(nameField);
        panel.add(new JLabel("Email"));
        panel.add(emailField);
        panel.add(new JLabel("Password"));
        panel.add(passwordField);
        panel.add(registerBtn);

        add(panel);

        registerBtn.addActionListener(e -> register());

        setVisible(true);
    }

    private void register() {
        User user = new User();
        user.setFullName(nameField.getText());
        user.setEmail(emailField.getText());
        user.setPassword(new String(passwordField.getPassword()));

        UserDA0 dao = new UserDA0();

        if (dao.register(user)) {
            JOptionPane.showMessageDialog(this,"Account Created!");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,"Email Already Exists!");
        }
    }
}