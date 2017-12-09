package testing;

import oracle.jvm.hotspot.jfr.JFR;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by klongric on 4/9/17.
 */
public class labels extends JFrame {

    labels()
    {   setLayout(new GridLayout(4, 1));
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(label());
        add(label());
        add(label());
        add(label());
        pack();
        setVisible(true);
    }

    public JLabel label()
    {
        JLabel label = new JLabel("name");
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Hello");
                super.mouseClicked(e);
            }
        });
        Border border = BorderFactory.createLineBorder(Color.white, 1);
        label.setBackground(Color.black);
        label.setForeground(Color.white);
        label.setOpaque(true);
        label.setText("This is some text");
        label.setBorder(border);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setPreferredSize(new Dimension(150, 50));
        return (label);
    }

    public static void main (String [] args)
    {
        labels x = new labels();
    }

}
