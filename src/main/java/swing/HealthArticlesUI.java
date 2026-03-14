package swing;

import javax.swing.*;
import java.awt.*;

public class HealthArticlesUI extends JFrame {

    Color light = new Color(193,216,255);
    Color dark = new Color(2,16,36);

    public HealthArticlesUI() {

        setTitle("Health Articles");
        setSize(900,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(light);
        mainPanel.setLayout(new BorderLayout());

        // Title
        JLabel heading = new JLabel("Health Articles", JLabel.CENTER);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 30));
        heading.setBorder(BorderFactory.createEmptyBorder(20,0,20,0));

        mainPanel.add(heading, BorderLayout.NORTH);

        // Articles Section
        JPanel articlesPanel = new JPanel();
        articlesPanel.setBackground(light);
        articlesPanel.setLayout(new GridLayout(0,1,20,20));
        articlesPanel.setBorder(BorderFactory.createEmptyBorder(20,100,20,100));

        articlesPanel.add(createArticleCard(
                "How to Maintain Heart Health",
                "Exercise regularly, eat balanced diet, avoid smoking and manage stress."
        ));

        articlesPanel.add(createArticleCard(
                "Tips for Strong Bones",
                "Include calcium, vitamin D and regular weight training in daily routine."
        ));

        articlesPanel.add(createArticleCard(
                "Brain Health Guide",
                "Stay mentally active, sleep well and reduce screen time."
        ));

        articlesPanel.add(createArticleCard(
                "Back Pain Prevention",
                "Maintain proper posture and avoid long sitting hours."
        ));
        
        articlesPanel.add(createArticleCard(
                "Diabetes Care Tips",
                "Monitor blood sugar regularly, avoid sugary foods and maintain healthy weight."
        ));

        articlesPanel.add(createArticleCard(
                "Healthy Diet Plan",
                "Eat fruits, vegetables, whole grains and drink plenty of water daily."
        ));

        articlesPanel.add(createArticleCard(
                "Mental Health Awareness",
                "Practice meditation, talk to loved ones and seek help when needed."
        ));

        articlesPanel.add(createArticleCard(
                "Immunity Boost Guide",
                "Sleep 7-8 hours daily and include vitamin C rich foods."
        ));

        articlesPanel.add(createArticleCard(
                "Weight Loss Strategy",
                "Regular exercise and calorie control are key to healthy weight loss."
        ));

        articlesPanel.add(createArticleCard(
                "Eye Care Essentials",
                "Limit screen time and get regular eye checkups."
        ));

        JScrollPane scrollPane = new JScrollPane(articlesPanel);
        scrollPane.setBorder(null);

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);
    }

    private JPanel createArticleCard(String title, String description) {

        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBackground(Color.white);
        card.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));

        JLabel descLabel = new JLabel("<html><p style='width:500px'>" + description + "</p></html>");
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(descLabel, BorderLayout.CENTER);

        return card;
    }
}