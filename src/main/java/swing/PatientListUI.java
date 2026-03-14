package swing;

import database.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PatientListUI extends JPanel {

    JTable table;
    DefaultTableModel model;

    Color sidebarColor = new Color(15,23,42);
    Color bgColor = new Color(248,250,252);

    public PatientListUI(){

        

        setSize(1000,550);

        

        setLayout(new BorderLayout());



        // Header
        JLabel header = new JLabel("Patient Records");

        header.setFont(new Font("Segoe UI",Font.BOLD,22));

        header.setBorder(BorderFactory.createEmptyBorder(10,20,10,10));

        add(header,BorderLayout.NORTH);



        // Table
        model = new DefaultTableModel();

        table = new JTable(model);

        table.setRowHeight(28);

        table.setFont(new Font("Segoe UI",Font.PLAIN,14));



        JTableHeader th = table.getTableHeader();

        th.setFont(new Font("Segoe UI",Font.BOLD,15));

        th.setBackground(new Color(226,232,240));



        model.addColumn("Appointment ID");

        model.addColumn("Patient Name");

        model.addColumn("Doctor");

        model.addColumn("Health Issue");

        model.addColumn("Date");

        model.addColumn("Status");



        JScrollPane scroll = new JScrollPane(table);

        add(scroll,BorderLayout.CENTER);



        loadPatients();



        setVisible(true);

    }



    // LOAD DATA FROM DATABASE

    void loadPatients(){

        try{

            Connection con = DBConnection.getConnection();

            String sql =

                    "SELECT appointment_id, patient_name, doctor_name, health_issue, appointment_date, status FROM appointment";


            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();



            model.setRowCount(0);


            while(rs.next()){

                model.addRow(new Object[]{

                        rs.getInt("appointment_id"),

                        rs.getString("patient_name"),

                        rs.getString("doctor_name"),

                        rs.getString("health_issue"),

                        rs.getString("appointment_date"),

                        rs.getString("status")

                });

            }

        }

        catch(Exception e){

            e.printStackTrace();

        }

    }

}