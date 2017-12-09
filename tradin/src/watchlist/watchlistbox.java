package watchlist;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by klongrich on 3/16/17.
 */
public class watchlistbox extends JPanel {

    static watchlist list;
    int width = 200;
    int height = 430;
    static String title = "Watchlist";
    String filepath = "./src/watchlist/lists/";

    public watchlistbox()
    {
        //Probably shouldn't use a null layout
        setLayout(null);


        DateFormat df = new SimpleDateFormat("MM-dd-yy");
        Date dateobj = new Date();
        list = new watchlist(df.format(dateobj),filepath);
        add(list);
        list.setLocation(0, 50);
        setSize(width, height);
        setVisible(true);

        //Adding the Buttons
        add(AddButton(init(), "Add"));
        add(watchlistButton(init(), "+"));
        add(EditButton(init(), "Edit"));
        add(ClearButton(init(), "Clear"));
        add(SelectButton(init(), "Select"));
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

    public JButton AddButton(JButton init, String name)
    {
        init.setText(name);
        init.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog("Ticker", "");
                if (name != null)
                    list.addticker(name);
                repaint();
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
                editmenubox x = new editmenubox(list.getNames());
            }
        });
        init.setLocation(95, 365);
        init.setSize(80, 25);
        return (init);
    }

    public JButton watchlistButton(JButton init, String name)
    {
        init.setText(name);
        init.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog("Enter Name", "");
                if (name != null) {
                    title = name;
                    list.newlist(name);
                    System.out.println("Update");
                    repaint();
                }
            }
        });
        init.setLocation(155, 8);
        init.setSize(25, 25);
        return (init);
    }

    public JButton ClearButton(JButton init, String name)
    {
        init.setText(name);
        init.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                list.clear();
            }
        });
        init.setLocation(2, 405);
        init.setSize(80, 25);
        return (init);
    }

    public JButton SelectButton(JButton init, String name)
    {
        init.setText(name);
        init.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectmenu x = new selectmenu(filepath);
                JDialog frame = new JDialog();
                frame.setSize(250, 500);
                frame.setLocationRelativeTo(getParent());
                frame.add(x);
                frame.setVisible(true);

            }
        });
        init.setLocation(95, 405);
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
        g.drawString(title, 0, 30);
    }
}

