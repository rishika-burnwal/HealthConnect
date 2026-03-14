package swing;

import javax.swing.*;
import java.awt.*;

public class RoundedPanel extends JPanel {

    int radius;

    RoundedPanel(int radius){

        this.radius = radius;
        setOpaque(false);

    }


    protected void paintComponent(Graphics g){

        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(getBackground());

        g2.fillRoundRect(0,0,getWidth(),getHeight(),radius,radius);

    }

}
