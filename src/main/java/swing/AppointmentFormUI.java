package swing;

import javax.swing.*;

import javax.swing.border.EmptyBorder;
import java.awt.*;
import database.Patient;
import database.PatientDAO;
import java.time.Year;
import com.toedter.calendar.JDateChooser;
import java.util.Date;

public class AppointmentFormUI extends JFrame {
	// ✅ ADD THESE GLOBAL VARIABLES

	

	JButton submitBtn;

	String doctorAvailability;

	int doctorId;

	String doctorName;

    Color light = new Color(193,232,255);
    Color dark = new Color(2,16,36);

    JTextField nameField, ageField, phoneField, healthField;
    JComboBox<String> genderBox;
    
    JDateChooser dateChooser;

    public AppointmentFormUI(int doctorId, String doctorName, String availability) {
    	
    	this.doctorId = doctorId;

    	this.doctorName = doctorName;

    	this.doctorAvailability = availability;
    	setVisible(true);

        setTitle("Book Appointment - " + doctorName);
        setSize(750,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel background = new JPanel(new GridBagLayout());
        background.setBackground(light);

        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(Color.white);
        card.setBorder(new EmptyBorder(40,50,40,50));

        background.add(card);
        add(background);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15,15,15,15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("Segoe UI", Font.BOLD, 18);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 18);

        // Name
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel nameLabel = new JLabel("Patient Name:");
        nameLabel.setFont(labelFont);
        card.add(nameLabel, gbc);

        gbc.gridx = 1;
        nameField = createField(fieldFont);
        card.add(nameField, gbc);

        // Age
        gbc.gridx = 0; gbc.gridy++;
        JLabel ageLabel = new JLabel("Age:");
        ageLabel.setFont(labelFont);
        card.add(ageLabel, gbc);

        gbc.gridx = 1;
        ageField = createField(fieldFont);
        card.add(ageField, gbc);

        // Gender
        gbc.gridx = 0; gbc.gridy++;
        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setFont(labelFont);
        card.add(genderLabel, gbc);

        gbc.gridx = 1;
        genderBox = new JComboBox<>(new String[]{
                "Select Gender", "Male", "Female", "Other"
        });
        genderBox.setFont(fieldFont);
        genderBox.setPreferredSize(new Dimension(350,45));
        card.add(genderBox, gbc);

        // Phone
        gbc.gridx = 0; gbc.gridy++;
        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setFont(labelFont);
        card.add(phoneLabel, gbc);

        gbc.gridx = 1;
        phoneField = createField(fieldFont);
        card.add(phoneField, gbc);

     // Appointment Date Calendar

        gbc.gridx = 0; gbc.gridy++;

        JLabel dateLabel = new JLabel("Appointment Date:");

        dateLabel.setFont(labelFont);

        card.add(dateLabel, gbc);


        gbc.gridx = 1;

        dateChooser = new JDateChooser();

        dateChooser.setFont(fieldFont);

        dateChooser.setPreferredSize(new Dimension(350,45));

        dateChooser.setDateFormatString("yyyy-MM-dd");
        dateChooser.setMinSelectableDate(new Date());

        card.add(dateChooser, gbc);
        
        dateChooser.addPropertyChangeListener("date", e -> {

            Date selectedDate = dateChooser.getDate();

            if(selectedDate == null) return;

            String day =
                    new java.text.SimpleDateFormat("EEE")
                            .format(selectedDate);

            if(doctorAvailability.contains(day))
            {
                submitBtn.setEnabled(true);
            }
            else
            {
                submitBtn.setEnabled(true); // keep enabled
            }

        });

        // Health Issue
        gbc.gridx = 0; gbc.gridy++;
        JLabel healthLabel = new JLabel("Health Issue:");
        healthLabel.setFont(labelFont);
        card.add(healthLabel, gbc);

        gbc.gridx = 1;
        healthField = createField(fieldFont);
        card.add(healthField, gbc);

        // Submit Button
        gbc.gridx = 0; gbc.gridy++;
        gbc.gridwidth = 2;

        submitBtn = new JButton("Book Appointment");
        submitBtn.setFont(new Font("Segoe UI", Font.BOLD, 20));
        submitBtn.setBackground(dark);
        submitBtn.setForeground(Color.white);
        submitBtn.setFocusPainted(false);
        submitBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        submitBtn.setPreferredSize(new Dimension(350,50));

        submitBtn.addActionListener(e -> {

            // ✅ First check if date selected
            Date selectedDateObj = dateChooser.getDate();

            if(selectedDateObj == null)
            {
                JOptionPane.showMessageDialog(
                        this,
                        "Please select appointment date!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }


            // ✅ Check doctor availability

            String day =
                    new java.text.SimpleDateFormat("EEE")
                            .format(selectedDateObj);


            if(!doctorAvailability.contains(day))
            {

                JOptionPane.showMessageDialog(
                        this,
                        "Doctor is not available on " + day,
                        "Not Available",
                        JOptionPane.WARNING_MESSAGE
                );

                return;

            }


            // ✅ Check other fields

            if(nameField.getText().isEmpty() ||
               ageField.getText().isEmpty() ||
               genderBox.getSelectedIndex() == 0 ||
               phoneField.getText().isEmpty() ||
               healthField.getText().isEmpty())
            {

                JOptionPane.showMessageDialog(
                        this,
                        "Please fill all fields!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );

                return;

            }


            // ✅ Phone validation

            String phone = phoneField.getText().trim();

            if(!phone.matches("\\d{10}"))
            {

                JOptionPane.showMessageDialog(
                        this,
                        "Phone number must be exactly 10 digits!",
                        "Invalid Phone",
                        JOptionPane.ERROR_MESSAGE
                );

                return;

            }


            // ✅ Format date

            String selectedDateString =
                    new java.text.SimpleDateFormat("yyyy-MM-dd")
                            .format(selectedDateObj);



            // ✅ Save patient

            Patient p = new Patient();

            p.setDoctorId(doctorId);

            p.setDoctorName(doctorName);

            p.setPatientName(nameField.getText());

            p.setAge(Integer.parseInt(ageField.getText()));

            p.setGender(genderBox.getSelectedItem().toString());

            p.setPhone(phone);

            p.setDate(selectedDateString);

            p.setHealthIssue(healthField.getText());


            PatientDAO dao = new PatientDAO();

            dao.savePatient(p);


            JOptionPane.showMessageDialog(
                    this,
                    "Appointment Successfully Booked!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );


            dispose();

        });
        card.add(submitBtn, gbc);

        setVisible(true);
    }

    private JTextField createField(Font font){
        JTextField field = new JTextField();
        field.setFont(font);
        field.setPreferredSize(new Dimension(350,45));
        return field;
    }
}