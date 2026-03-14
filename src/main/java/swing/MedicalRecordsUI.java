package swing;

import database.DBConnection;

import swing.Session;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.*;


public class MedicalRecordsUI extends JFrame {

    JPanel panel;

    Color bg = new Color(193,216,255);
    Color cardColor = Color.white;
    Color dark = new Color(2,16,36);

    public MedicalRecordsUI(){

        setTitle("Medical Records");
        setSize(1000,700);
        setLocationRelativeTo(null);

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        panel.setBackground(bg);
        panel.setBorder(new EmptyBorder(20,20,20,20));

        JScrollPane scroll = new JScrollPane(panel);
        scroll.setBorder(null);

        add(scroll);

        loadRecords();

        setVisible(true);
    }


    void loadRecords(){

        try{

            Connection con = DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(
                            "SELECT * FROM appointment WHERE user_id = ?"
                    );

            ps.setInt(1, Session.userId);

            ResultSet rs = ps.executeQuery();

            boolean found=false;

            while(rs.next()){

                found=true;

                panel.add(createRecordCard(rs));
                panel.add(Box.createVerticalStrut(20));

            }

            if(!found){

                JLabel noData =
                        new JLabel("No Medical Records Found");

                noData.setFont(
                        new Font("Segoe UI",Font.BOLD,22)
                );

                noData.setForeground(dark);

                panel.add(noData);

            }

        }
        catch(Exception e){

            e.printStackTrace();

        }

    }



    JPanel createRecordCard(ResultSet rs){

    	JPanel card = new RoundedPanel(25);
    	card.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

    	card.setLayout(new BorderLayout());
    	card.setPreferredSize(new Dimension(900, 300));
    	card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300));
    	card.setBackground(Color.WHITE);


        int appointmentId = 0;

        try{
            appointmentId = rs.getInt("appointment_id");
        }catch(Exception e){}


        //---------------- LEFT TEXT ----------------//

        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setOpaque(false);
        left.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        try{

            JLabel doctor =
                    new JLabel("Doctor: "+rs.getString("doctor_name"));

            doctor.setFont(new Font("Segoe UI",Font.BOLD,24));
            doctor.setForeground(new Color(0,51,102));
            
            JLabel specialization =
            	    new JLabel("Specialization: "+rs.getString("specialization"));

            	specialization.setFont(new Font("Segoe UI",Font.PLAIN,19));

            	JLabel hospital =
            	    new JLabel("Hospital: "+rs.getString("hospital"));

            	hospital.setFont(new Font("Segoe UI",Font.PLAIN,19));


            JLabel patient =
                    new JLabel("Patient: "+rs.getString("patient_name"));

            patient.setFont(new Font("Segoe UI",Font.PLAIN,19));

            String healthIssue = rs.getString("health_issue");
            String appointmentDate = rs.getString("appointment_date");

            if(healthIssue == null) healthIssue = "Not Available";
            if(appointmentDate == null) appointmentDate = "Not Available";
            
            JLabel issue =
                    new JLabel("Health Issue: "+rs.getString("health_issue"));

            issue.setFont(new Font("Segoe UI",Font.PLAIN,19));


            JLabel date =
                    new JLabel("Date: "+rs.getString("appointment_date"));

            date.setFont(new Font("Segoe UI",Font.PLAIN,19));


            left.add(doctor);
            left.add(Box.createVerticalStrut(10));

            left.add(specialization);
            left.add(Box.createVerticalStrut(8));
            left.add(hospital);

            left.add(Box.createVerticalStrut(10));

            left.add(patient);
            left.add(Box.createVerticalStrut(5));

            left.add(issue);
            left.add(Box.createVerticalStrut(5));

            left.add(date);
            
            String statusText = rs.getString("status");

            JLabel status =
                    new JLabel("Status: " + statusText);

            status.setFont(new Font("Segoe UI", Font.BOLD, 19));

            // ✅ COLOR LOGIC
            if(statusText.equalsIgnoreCase("Confirmed"))
            {
                status.setForeground(new Color(34,197,94)); // Green
            }
            else if(statusText.equalsIgnoreCase("Pending"))
            {
                status.setForeground(new Color(234,179,8)); // Yellow
            }
            else if(statusText.equalsIgnoreCase("Rejected"))
            {
                status.setForeground(new Color(220,53,69)); // 🔴 RED
            }

            left.add(Box.createVerticalStrut(5));

            left.add(status);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        panel.revalidate();
        panel.repaint();



        //---------------- PREMIUM CANCEL BUTTON ----------------//

        JButton cancel = new JButton("Cancel");
        String statusText = "";

        try
        {
            statusText = rs.getString("status");
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }

        if(statusText.equalsIgnoreCase("Rejected"))
        {
            cancel.setEnabled(false);

            cancel.setBackground(Color.GRAY);

            cancel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
        else
        {
            cancel.setBackground(new Color(220,53,69));
        }

        cancel.setPreferredSize(new Dimension(140,45));

        cancel.setFont(new Font("Segoe UI",Font.BOLD,17));

        cancel.setBackground(new Color(220,53,69));

        cancel.setForeground(Color.white);

        cancel.setFocusPainted(false);

        cancel.setBorder(BorderFactory.createEmptyBorder());

        cancel.setCursor(new Cursor(Cursor.HAND_CURSOR));



        // HOVER EFFECT

        cancel.addMouseListener(new java.awt.event.MouseAdapter(){

            public void mouseEntered(java.awt.event.MouseEvent evt){

                cancel.setBackground(new Color(200,35,51));

            }

            public void mouseExited(java.awt.event.MouseEvent evt){

                cancel.setBackground(new Color(220,53,69));

            }

        });



        int finalAppointmentId = appointmentId;



        cancel.addActionListener(e -> {

            int confirm =
                    JOptionPane.showConfirmDialog(
                            null,
                            "Cancel Appointment?",
                            "Confirm",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE
                    );


            if(confirm==JOptionPane.YES_OPTION){

                deleteAppointment(finalAppointmentId);


                //---------------- SUCCESS POPUP ----------------//

                JOptionPane.showMessageDialog(
                        null,
                        "Appointment Cancelled Successfully",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );


                //---------------- REMOVE CARD SMOOTHLY ----------------//

                panel.remove(card);

                panel.revalidate();

                panel.repaint();

            }

        });



        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 20));
        right.setOpaque(false);
        right.setBackground(Color.white);
        right.add(cancel);



        card.add(left,BorderLayout.CENTER);
        card.add(right,BorderLayout.EAST);



        return card;

    }
    void deleteAppointment(int id){

        try{

            Connection con = DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(
                            "DELETE FROM appointment WHERE appointment_id=?"
                    );

            ps.setInt(1,id);

            ps.executeUpdate();

        }
        catch(Exception e){

            e.printStackTrace();

        }

    }

}