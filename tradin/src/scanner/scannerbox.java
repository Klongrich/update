package scanner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by klongrich on 3/16/17.
 */
public class scannerbox extends JPanel {

    scanner list;
    int width = 200;
    int height = 400;
    String title = "Scanner";

    public scannerbox()
    {
        setLayout(null);
        list = new scanner();
        add(list);
        list.setLocation(0, 50);
        setSize(width, height);
        setVisible(true);
        add(ScanButton(init(), "Scan"));
        add(EditButton(init(), "Edit"));
    }

    public JButton init()
    {
        JButton button = new JButton();
        button.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.darkGray, 2));
        button.setBackground(Color.gray);
        button.setForeground(Color.white);
        return (button);
    }

    public JButton ScanButton(JButton init, String name)
    {
        init.setText(name);
        init.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                list.scan();
            }
        });
        init.setLocation(2, 365);
        init.setSize(80, 25);
        return (init);
    }

    public JButton EditButton(JButton init, String name)
    {
        init.setText(name);
        init.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Tickers");
                frame.setLayout(new GridLayout(5, 5));
                frame.setSize(250, 500);
                frame.setVisible(true);

            }
        });
        init.setLocation(95, 365);
        init.setSize(80, 25);
        return (init);
    }

    public void paintComponent(Graphics g2)
    {
        Graphics2D g = (Graphics2D) g2;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.setColor(Color.black);
        g.fillRect(0,0,width, height);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        g.setColor(Color.white);
        g.drawString(title, 25, 30);
    }

    public void addticker(String name)
    {
        list.addticker(name);
    }

    public void updateRight() {list.updateSelectedRight();}

    public void updateLeft() {list.updateSelectedLeft();}

    public String selected() { return (list.getSelected());}

}
