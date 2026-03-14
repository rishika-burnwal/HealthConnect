package swing;

import database.Doctor;
import database.DoctorDAO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.net.URL;
import java.util.List;
import swing.AppointmentFormUI;


public class DoctorUI extends JFrame {

    Color light = new Color(193,216,255);
    Color medium = new Color(125,160,202);
    Color dark = new Color(2,16,36);

    JPanel mainPanel;


    public DoctorUI(int specializationId, String specializationName){

        setTitle(specializationName + " Doctors - HealthConnect");
        setSize(900,600);
        setLocationRelativeTo(null);


        mainPanel = new JPanel();
        mainPanel.setBackground(light);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));


        JScrollPane scroll = new JScrollPane(mainPanel);
        scroll.setBorder(null);

        add(scroll);


        loadDoctors(specializationId);

        setVisible(true);
    }



    void loadDoctors(int specializationId){

        DoctorDAO dao = new DoctorDAO();

        List<Doctor> doctors = dao.getDoctorsBySpecialization(specializationId);


        if(doctors.isEmpty()){

            JLabel noData = new JLabel("No doctors found");

            noData.setFont(new Font("Segoe UI",Font.BOLD,22));

            noData.setAlignmentX(Component.CENTER_ALIGNMENT);

            mainPanel.add(Box.createVerticalStrut(50));
            mainPanel.add(noData);

            return;
        }


        mainPanel.add(Box.createVerticalStrut(20));

        for(Doctor d : doctors){

            mainPanel.add(createDoctorCard(d));

            mainPanel.add(Box.createVerticalStrut(20));

        }


        mainPanel.revalidate();
        mainPanel.repaint();

    }





    JPanel createDoctorCard(Doctor d){


        JPanel wrapper = new JPanel();

        wrapper.setBackground(light);

        wrapper.setLayout(new FlowLayout(FlowLayout.CENTER));



        JPanel card = new RoundedPanel(25);

        card.setLayout(new BorderLayout(20,0));

        card.setPreferredSize(new Dimension(750,160));

        card.setBackground(Color.white);

        card.setBorder(new EmptyBorder(10,15,10,15));



        // IMAGE LEFT

        JLabel imageLabel = new JLabel();

        try {
            if(d.getImage() != null && !d.getImage().isEmpty()) {

                URL url = new URL(d.getImage());
                ImageIcon icon = new ImageIcon(url);

                Image img = icon.getImage()
                        .getScaledInstance(90,90,Image.SCALE_SMOOTH);

                imageLabel.setIcon(new ImageIcon(img));
            } 
            else {
                imageLabel.setText("No Image");
            }

        } 
        catch(Exception ex) {
            imageLabel.setText("No Image");
        }


        card.add(imageLabel,BorderLayout.WEST);




        // DETAILS CENTER

        JPanel detail = new JPanel();

        detail.setLayout(new BoxLayout(detail,BoxLayout.Y_AXIS));

        detail.setBackground(Color.white);



        JLabel name = new JLabel(d.getName());

        name.setFont(new Font("Segoe UI",Font.BOLD,20));



        JLabel exp =
                new JLabel("Experience: "+d.getExperience()+" years");

        exp.setFont(new Font("Segoe UI",Font.PLAIN,15));



        JLabel hospital =
                new JLabel("Hospital: "+d.getHospital());

        hospital.setFont(new Font("Segoe UI",Font.PLAIN,15));



        JLabel phone =
                new JLabel("Phone: "+d.getPhone());

        phone.setFont(new Font("Segoe UI",Font.PLAIN,15));
        
        JLabel availability =
                new JLabel("Available: " + d.getAvailability());

        availability.setFont(new Font("Segoe UI",Font.PLAIN,15));



        detail.add(name);

        detail.add(Box.createVerticalStrut(5));

        detail.add(exp);

        detail.add(hospital);

        detail.add(phone);
        
        detail.add(availability);



        card.add(detail,BorderLayout.CENTER);





        // BOOK BUTTON RIGHT


        JButton bookBtn =
                new JButton("Book Appointment");



        bookBtn.setFont(
                new Font("Segoe UI",Font.BOLD,14));



        bookBtn.setBackground(dark);

        bookBtn.setForeground(Color.white);

        bookBtn.setFocusPainted(false);

        bookBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        bookBtn.setPreferredSize(new Dimension(160,40));



        bookBtn.addActionListener(e -> {

        	new AppointmentFormUI(
        	        d.getId(),
        	        d.getName(),
        	        d.getAvailability()
        	);

        });




        JPanel btnPanel = new JPanel();

        btnPanel.setBackground(Color.white);

        btnPanel.setLayout(new GridBagLayout());

        btnPanel.add(bookBtn);



        card.add(btnPanel,BorderLayout.EAST);



        wrapper.add(card);


        return wrapper;

    }




}
