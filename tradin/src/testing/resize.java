package testing;

import oracle.jvm.hotspot.jfr.JFR;

import javax.swing.*;
import java.awt.*;

/**
 * Created by klongric on 3/20/17.
 */
public class resize extends JPanel {

    resize()
    {
        setSize(500, 500);
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(50, 50,  50, 50);
    }

    public static void main (String [] args)
    {
        JFrame frame = new JFrame();
        frame.setSize(500, 500);
        frame.add(new resize());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
