package swing;

import database.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class AdminUI extends JPanel {

    JTable table;

    DefaultTableModel model;

    AdminDashboardUI dashboard;

    public AdminUI(AdminDashboardUI dashboard)
    {
        this.dashboard = dashboard;

    	setLayout(new BorderLayout());
    	setBackground(Color.WHITE);


        model = new DefaultTableModel();

        table = new JTable(model);



        model.addColumn("ID");

        model.addColumn("Patient Name");

        model.addColumn("Doctor");

        model.addColumn("Date");

        model.addColumn("Status");



        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createEmptyBorder(20,20,10,20));
        add(scroll, BorderLayout.CENTER);
        


        JButton acceptBtn = new JButton("Accept");

        JButton rejectBtn = new JButton("Reject");



        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT,20,10));
        panel.setBorder(BorderFactory.createEmptyBorder(10,20,20,20));
        panel.setBackground(Color.WHITE);

        acceptBtn.setPreferredSize(new Dimension(120,35));
        rejectBtn.setPreferredSize(new Dimension(120,35));

        panel.add(acceptBtn);
        panel.add(rejectBtn);

        add(panel, BorderLayout.SOUTH);


        acceptBtn.addActionListener(e -> updateStatus("Confirmed"));

        rejectBtn.addActionListener(e -> updateStatus("Rejected"));



        loadAppointments();
        



        setVisible(true);

    }



    void loadAppointments(){

    	try{

    	Connection con = DBConnection.getConnection();

    	String sql =
    	"SELECT appointment_id, patient_name, doctor_name, appointment_date, status FROM appointment";

    	PreparedStatement ps = con.prepareStatement(sql);

    	ResultSet rs = ps.executeQuery();

    	model.setRowCount(0);

    	while(rs.next()){

    	model.addRow(new Object[]{

    	rs.getInt("appointment_id"),
    	rs.getString("patient_name"),
    	rs.getString("doctor_name"),     // ✅ correct
    	rs.getString("appointment_date"),// ✅ correct
    	rs.getString("status")

    	});

    	}

    	}

    	catch(Exception e){

    	e.printStackTrace();

    	}

    	}

    void updateStatus(String status){

    	int row = table.getSelectedRow();

    	if(row==-1){

    	JOptionPane.showMessageDialog(this,"Select a row");

    	return;

    	}

    	int id = (int) model.getValueAt(row,0);

    	try{

    	Connection con = DBConnection.getConnection();

    	String sql =
    	"UPDATE appointment SET status=? WHERE appointment_id=?";   // ✅ FIXED

    	PreparedStatement ps = con.prepareStatement(sql);

    	ps.setString(1,status);

    	ps.setInt(2,id);

    	int rows = ps.executeUpdate();

    	if(rows>0){

    	JOptionPane.showMessageDialog(this,
    	"Appointment "+status);

    	}
    	else{

    	JOptionPane.showMessageDialog(this,
    	"Update Failed");

    	}

    	loadAppointments();
    	dashboard.loadDashboardCounts();

    	// refresh dashboard UI
    	dashboard.revalidate();
    	dashboard.repaint();

    	}

    	catch(Exception e){

    	e.printStackTrace();

    	}

    	}
}