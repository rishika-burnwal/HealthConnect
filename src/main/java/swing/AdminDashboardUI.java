package swing;

import database.DBConnection;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
public class AdminDashboardUI extends JFrame {
	
	CardLayout cardLayout;
	JPanel mainPanel;

    Color sidebarColor = new Color(15, 23, 42);
    Color headerColor = new Color(241,245,249);
    Color bgColor = new Color(248,250,252);

    JLabel totalLbl, pendingLbl, confirmedLbl, rejectedLbl;

    public AdminDashboardUI()
    {
        setTitle("HealthConnect Admin Dashboard");
        setSize(1400,800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        add(createSidebar(), BorderLayout.WEST);
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Dashboard panel
        mainPanel.add(createMainPanel(), "Dashboard");

        // Add your existing panels
        mainPanel.add(new AdminUI(this), "Appointments");
        mainPanel.add(new PatientListUI(), "Patients");

        add(mainPanel, BorderLayout.CENTER);

        setVisible(true);
        loadDashboardCounts();

        // demo values
        totalLbl.setText("9");
        pendingLbl.setText("6");
        confirmedLbl.setText("0");
        rejectedLbl.setText("2");
    }

    // SIDEBAR
    JPanel createSidebar()
    {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(220,800));
        panel.setBackground(sidebarColor);
        panel.setLayout(new GridLayout(6,1,10,10));
        panel.setBorder(new EmptyBorder(30,20,30,20));

        panel.add(menu("Dashboard"));
        panel.add(menu("Appointments"));
        panel.add(menu("Patients"));
        panel.add(menu("Logout"));

        return panel;
    }
    void handleMenuClick(String menu)
    {
        if(menu.equals("Logout"))
        {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Do you want to logout?");

            if(confirm == JOptionPane.YES_OPTION)
            {
                new AdminLoginUI();
                dispose();
            }
        }
        else
        {
            cardLayout.show(mainPanel, menu);
        }
    }

    JButton menu(String text)
    {
        JButton btn = new JButton(text);

        btn.setFocusPainted(false);
        btn.setForeground(Color.WHITE);
        btn.setBackground(sidebarColor);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        btn.setBorder(null);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // ADD CLICK EVENT
        btn.addActionListener(e ->
        {
            if(text.equals("Logout"))
            {
                new AdminLoginUI();
                dispose();
            }
            else
            {
                cardLayout.show(mainPanel, text);

                // ⭐ ADD THIS LINE (MOST IMPORTANT)
                loadDashboardCounts();

                // refresh UI
                mainPanel.revalidate();
                mainPanel.repaint();
            }
        });
        return btn;
    }

    // MAIN PANEL
    JPanel createMainPanel()
    {
        JPanel panel = new JPanel();
        panel.setBackground(bgColor);
        panel.setLayout(new BorderLayout());

        panel.add(createHeader(), BorderLayout.NORTH);
        panel.add(createCards(), BorderLayout.CENTER);

        return panel;
    }

    // HEADER
    JPanel createHeader()
    {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(1000,60));
        panel.setBackground(headerColor);

        JLabel title = new JLabel("Dashboard");
        title.setFont(new Font("Segoe UI",Font.BOLD,24));
        title.setBorder(new EmptyBorder(10,20,10,10));

        JLabel admin = new JLabel("Admin");
        admin.setFont(new Font("Segoe UI",Font.BOLD,18));
        admin.setBorder(new EmptyBorder(10,10,10,20));

        panel.add(title,BorderLayout.WEST);
        panel.add(admin,BorderLayout.EAST);

        return panel;
    }

    // CARDS PANEL
    JPanel createCards()
    {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT,30,40));
        panel.setBackground(bgColor);

        totalLbl = new JLabel();
        pendingLbl = new JLabel();
        confirmedLbl = new JLabel();
        rejectedLbl = new JLabel();

        panel.add(createCard("Total", totalLbl, new Color(59,130,246)));
        panel.add(createCard("Pending", pendingLbl, new Color(234,179,8)));
        panel.add(createCard("Confirmed", confirmedLbl, new Color(34,197,94)));
        panel.add(createCard("Rejected", rejectedLbl, new Color(239,68,68)));

        return panel;
    }

    // MODERN CARD
    JPanel createCard(String title, JLabel value, Color color)
    {
        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(250,130));
        card.setLayout(new BorderLayout());
        card.setBackground(Color.WHITE);

        // rounded border + shadow
        card.setBorder(new CompoundBorder(
                new ShadowBorder(),
                new EmptyBorder(15,20,15,20)));

        JLabel titleLbl = new JLabel(title);
        titleLbl.setFont(new Font("Segoe UI",Font.PLAIN,18));

        value.setFont(new Font("Segoe UI",Font.BOLD,36));
        value.setForeground(color);

        card.add(titleLbl,BorderLayout.NORTH);
        card.add(value,BorderLayout.CENTER);

        return card;
    }
    void loadDashboardCounts()
    {
        try
        {
            Connection con = DBConnection.getConnection();

            // Total
            PreparedStatement ps1 = con.prepareStatement(
            "SELECT COUNT(*) FROM appointment");
            ResultSet rs1 = ps1.executeQuery();
            if(rs1.next())
                totalLbl.setText(rs1.getString(1));


            // Pending
            PreparedStatement ps2 = con.prepareStatement(
            "SELECT COUNT(*) FROM appointment WHERE status='Pending'");
            ResultSet rs2 = ps2.executeQuery();
            if(rs2.next())
                pendingLbl.setText(rs2.getString(1));


            // Confirmed
            PreparedStatement ps3 = con.prepareStatement(
            "SELECT COUNT(*) FROM appointment WHERE status='Confirmed'");
            ResultSet rs3 = ps3.executeQuery();
            if(rs3.next())
                confirmedLbl.setText(rs3.getString(1));


            // Rejected
            PreparedStatement ps4 = con.prepareStatement(
            "SELECT COUNT(*) FROM appointment WHERE status='Rejected'");
            ResultSet rs4 = ps4.executeQuery();
            if(rs4.next())
                rejectedLbl.setText(rs4.getString(1));

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    // SHADOW EFFECT
    class ShadowBorder extends AbstractBorder
    {
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height)
        {
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(new Color(0,0,0,30));
            g2.fillRoundRect(x+3,y+3,width-6,height-6,20,20);
        }

        public Insets getBorderInsets(Component c)
        {
            return new Insets(5,5,5,5);
        }
    }

    public static void main(String[] args)
    {
        new AdminDashboardUI();
    }

}