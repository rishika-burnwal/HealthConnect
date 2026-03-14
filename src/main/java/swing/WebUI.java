package swing;

import javax.swing.*;
import swing.Session;
import java.awt.*;
import java.awt.event.*;

public class WebUI extends JFrame {

    Color dark = new Color(2,16,36);
    Color medium = new Color(125,160,202);
    Color light = new Color(193,216,255);
    Color deepHover = new Color(170,200,240);
    Color hover = new Color(90,130,180);

    JPanel mainPanel;
    JScrollPane scrollPane;
    JPanel sideMenu;
    JPanel heroSection;
    JPanel aboutSection;
    JPanel contactSection;

    public WebUI(){

        setTitle("HealthConnect");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(light);

        scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        add(scrollPane, BorderLayout.CENTER);

        createNavbar();
        createHero();
        createSpecialization();
        createAbout();
        createContact();
        createSideMenu();

        setVisible(true);
    }

    // NAVBAR
    void createNavbar(){

        JPanel nav = new JPanel(new BorderLayout());
        nav.setBackground(dark);
        nav.setMaximumSize(new Dimension(Integer.MAX_VALUE,70));

        //---------------- LEFT ----------------//

        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT,20,15));
        left.setBackground(dark);

        JButton hamburger = new JButton("☰");
        hamburger.setFont(new Font("Segoe UI",Font.BOLD,22));
        hamburger.setForeground(Color.white);
        hamburger.setBackground(dark);
        hamburger.setBorder(null);
        hamburger.setCursor(new Cursor(Cursor.HAND_CURSOR));

        hamburger.addActionListener(e->{
            sideMenu.setVisible(!sideMenu.isVisible());
        });

        JLabel logo = new JLabel("HealthConnect");
        logo.setFont(new Font("Segoe UI",Font.BOLD,22));
        logo.setForeground(Color.white);

        left.add(hamburger);
        left.add(logo);

        //---------------- CENTER ----------------//

        JPanel center = new JPanel(new FlowLayout(FlowLayout.CENTER,40,15));
        center.setBackground(dark);

        JButton aboutBtn = createNavButton("About");
        JButton contactBtn = createNavButton("Contact");

        aboutBtn.addActionListener(e ->
                aboutSection.scrollRectToVisible(aboutSection.getBounds())
        );

        contactBtn.addActionListener(e ->
                contactSection.scrollRectToVisible(contactSection.getBounds())
        );

        center.add(aboutBtn);
        center.add(contactBtn);

        //---------------- RIGHT ----------------//

      //---------------- RIGHT ----------------//

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT,15,18));
        right.setBackground(dark);

     // Check login status

        if(Session.userId == 0)   // NOT LOGGED IN
        {

            JButton loginBtn = new JButton("Login");

            loginBtn.setFont(new Font("Segoe UI",Font.BOLD,16));
            loginBtn.setForeground(Color.white);
            loginBtn.setBackground(dark);
            loginBtn.setBorder(null);
            loginBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

            loginBtn.addActionListener(e -> {

                new LoginUI();

                dispose();

            });



            JButton signupBtn = new JButton("Signup");

            signupBtn.setFont(new Font("Segoe UI",Font.BOLD,16));
            signupBtn.setForeground(Color.white);
            signupBtn.setBackground(dark);
            signupBtn.setBorder(null);
            signupBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

            signupBtn.addActionListener(e -> {

                new SignupUI();

                dispose();

            });



            right.add(loginBtn);

            right.add(signupBtn);

        }
        else   // LOGGED IN
        {

            JLabel nameLabel = new JLabel("Welcome, " + Session.userName);

            nameLabel.setFont(new Font("Segoe UI",Font.BOLD,16));

            nameLabel.setForeground(Color.white);



            JButton logoutBtn = new JButton("Logout");

            logoutBtn.setFont(new Font("Segoe UI",Font.BOLD,16));

            logoutBtn.setForeground(Color.white);

            logoutBtn.setBackground(dark);

            logoutBtn.setBorder(null);

            logoutBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));



            logoutBtn.addActionListener(e -> {

                Session.userId = 0;

                Session.userName = null;

                new WebUI();

                dispose();

            });



            right.add(nameLabel);

            right.add(logoutBtn);

        }
        nav.add(left, BorderLayout.WEST);

        nav.add(center, BorderLayout.CENTER);

        nav.add(right, BorderLayout.EAST);

        // ⭐ MOST IMPORTANT
        add(nav, BorderLayout.NORTH);
    } 
    
    JButton createNavButton(String text){

        JButton btn = new JButton(text);
        btn.setForeground(Color.white);
        btn.setBackground(dark);
        btn.setFont(new Font("Segoe UI",Font.BOLD,16));
        btn.setBorder(null);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return btn;
    }

    // HERO
    void createHero(){

    	heroSection = new JPanel();
    	heroSection.setBackground(light);
    	heroSection.setLayout(new BorderLayout());
    	heroSection.setMaximumSize(new Dimension(Integer.MAX_VALUE,280));

        JPanel left = new JPanel();
        left.setBackground(light);
        left.setLayout(new BoxLayout(left,BoxLayout.Y_AXIS));
        left.setBorder(BorderFactory.createEmptyBorder(40,80,40,40));

        JLabel title = new JLabel(
                "<html>Provide quality healthcare<br>from trusted doctors</html>");
        title.setFont(new Font("Segoe UI",Font.BOLD,38));

        JLabel subtitle = new JLabel(
                "<html>Book appointments easily.<br>Fast, secure healthcare.</html>");
        subtitle.setFont(new Font("Segoe UI",Font.PLAIN,20));

        left.add(title);
        left.add(Box.createVerticalStrut(15));
        left.add(subtitle);

        heroSection.add(left,BorderLayout.WEST);
        mainPanel.add(heroSection);
    }

    // SPECIALIZATION
    void createSpecialization(){

        JLabel heading = new JLabel("Choose Specialization");
        heading.setFont(new Font("Segoe UI",Font.BOLD,28));
        heading.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(Box.createVerticalStrut(30));
        mainPanel.add(heading);
        mainPanel.add(Box.createVerticalStrut(20));

        JPanel section = new JPanel(new FlowLayout(FlowLayout.CENTER,40,20));
        section.setBackground(light);

        section.add(createImageCard("Neurology","/images/neuro.jpg",1));
        section.add(createImageCard("Orthology","/images/ortho.jpg",2));
        section.add(createImageCard("Cardiology","/images/cardio.jpg",3));
        section.add(createImageCard("Physician","/images/physio.jpg",4));
        mainPanel.add(section);
    }

    // CARD DESIGN
    JPanel createImageCard(String name,String imagePath,int id){

        JPanel card = new JPanel(new BorderLayout());
        card.setPreferredSize(new Dimension(220,180));
        card.setBackground(Color.white);
        card.setBorder(BorderFactory.createLineBorder(new Color(160,190,230),1));

        // Load image from resources
        ImageIcon icon = new ImageIcon(getClass().getResource(imagePath));
        Image img = icon.getImage().getScaledInstance(220,120,Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(img));

        JLabel title = new JLabel(name, JLabel.CENTER);
        title.setFont(new Font("Segoe UI",Font.BOLD,16));
        title.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));

        card.add(imageLabel,BorderLayout.CENTER);
        card.add(title,BorderLayout.SOUTH);

        // Hover effect
        card.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                card.setBackground(new Color(220,235,255));
            }
            public void mouseExited(MouseEvent e) {
                card.setBackground(Color.white);
            }
            public void mouseClicked(MouseEvent e) {
                new DoctorUI(id,name);
            }
        });

        return card;
    }

    // ABOUT
    void createAbout(){

        aboutSection = new JPanel();
        aboutSection.setBackground(light);
        aboutSection.setLayout(new BoxLayout(aboutSection, BoxLayout.Y_AXIS));
        aboutSection.setAlignmentX(Component.CENTER_ALIGNMENT);
        aboutSection.setMaximumSize(new Dimension(600, Integer.MAX_VALUE));

        JLabel about = new JLabel("About Us");
        about.setFont(new Font("Segoe UI",Font.BOLD,26));
        about.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel aboutDesc = new JLabel(
            "<html><div style='text-align:center;'>"
            + "HealthConnect connects patients with trusted doctors "
            + "for easy and secure appointment booking."
            + "</div></html>"
        );

        aboutDesc.setFont(new Font("Segoe UI",Font.PLAIN,17));
        aboutDesc.setAlignmentX(Component.CENTER_ALIGNMENT);

        aboutSection.add(Box.createVerticalStrut(40));
        aboutSection.add(about);
        aboutSection.add(Box.createVerticalStrut(10));
        aboutSection.add(aboutDesc);

        mainPanel.add(aboutSection);
    }

    // CONTACT
    void createContact(){

        contactSection = new JPanel();
        contactSection.setBackground(light);
        contactSection.setLayout(new BoxLayout(contactSection, BoxLayout.Y_AXIS));
        contactSection.setAlignmentX(Component.CENTER_ALIGNMENT);
        contactSection.setMaximumSize(new Dimension(300, Integer.MAX_VALUE));

        JLabel contact = new JLabel("Contact");
        contact.setFont(new Font("Segoe UI",Font.BOLD,26));
        contact.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel contactDesc = new JLabel(
            "<html><div style='text-align:center;'>"
            + "Email: support@healthconnect.com<br>"
            + "Phone: +91 9876543210<br>"
            + "Location: India"
            + "</div></html>"
        );

        contactDesc.setFont(new Font("Segoe UI",Font.PLAIN,17));
        contactDesc.setAlignmentX(Component.CENTER_ALIGNMENT);

        contactSection.add(Box.createVerticalStrut(25));
        contactSection.add(contact);
        contactSection.add(Box.createVerticalStrut(10));
        contactSection.add(contactDesc);

        mainPanel.add(contactSection);
    }
    // SIDE MENU
    void createSideMenu(){

        sideMenu = new JPanel();
        sideMenu.setBackground(dark);
        sideMenu.setPreferredSize(new Dimension(250,getHeight()));
        sideMenu.setLayout(new BoxLayout(sideMenu,BoxLayout.Y_AXIS));

        JButton records = new JButton("View Medical Records");
        records.setFont(new Font("Segoe UI",Font.BOLD,18));
        records.setForeground(Color.white);
        records.setBackground(dark);
        records.setBorder(null);
        records.setAlignmentX(Component.CENTER_ALIGNMENT);
        records.setCursor(new Cursor(Cursor.HAND_CURSOR));

        records.addActionListener(e -> new MedicalRecordsUI());

        JButton articles = new JButton("Read Articles");
        articles.setFont(new Font("Segoe UI",Font.BOLD,18));
        articles.setForeground(Color.white);
        articles.setBackground(dark);
        articles.setBorder(null);
        articles.setAlignmentX(Component.CENTER_ALIGNMENT);
        articles.setCursor(new Cursor(Cursor.HAND_CURSOR));
        articles.addActionListener(e -> new HealthArticlesUI());

        sideMenu.add(Box.createVerticalStrut(100));
        sideMenu.add(records);
        sideMenu.add(Box.createVerticalStrut(20));
        sideMenu.add(articles);
        
     // ADMIN LOGIN BUTTON

        JButton adminBtn = new JButton("Admin Login");

        adminBtn.setFont(new Font("Segoe UI",Font.BOLD,18));

        adminBtn.setForeground(Color.white);

        adminBtn.setBackground(dark);

        adminBtn.setBorder(null);

        adminBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        adminBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));


        // Hover effect

        adminBtn.addMouseListener(new java.awt.event.MouseAdapter(){

            public void mouseEntered(java.awt.event.MouseEvent evt){

                adminBtn.setForeground(new Color(255,200,120));

            }

            public void mouseExited(java.awt.event.MouseEvent evt){

                adminBtn.setForeground(Color.white);

            }

        });


        // Open Admin Login

        adminBtn.addActionListener(e -> {

            new AdminLoginUI();

        });


        sideMenu.add(Box.createVerticalStrut(20));

        sideMenu.add(adminBtn);

        sideMenu.setVisible(false);
        add(sideMenu,BorderLayout.WEST);
    }

    public static void main(String[] args){
        new WebUI();
    }
}