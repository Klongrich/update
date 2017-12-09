package watchlist;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by klongric on 3/22/17.
 */
public class editmenu extends JPanel {

    static ArrayList <String> tickers = new ArrayList <String>();
    ArrayList <JButton> buttons = new ArrayList <JButton>();

    public editmenu(ArrayList<String> tickers)
    {
        setLayout(null);
        setSize(250, 500);
        setVisible(true);
        this.tickers = tickers;
        ArrayList <JButton> buttons = new ArrayList <JButton>();
        this.buttons = buttons;
        for (int i = 0; i < tickers.size(); i++)
            buttons.add(delete(i * 50 + 8, i));
        for (int i = 0; i < tickers.size(); i++)
            add(buttons.get(i));
    }

    public void updatebuttons(ArrayList <String> ticks)
    {
       for (int i = 0; i < buttons.size(); i++)
           remove(buttons.get(i));
        for (int i = 0; i < tickers.size(); i++)
            add(buttons.get(i));
    }

    public JButton delete(int y, final int index)
    {
        JButton button = new JButton();
        button.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        button.setFocusPainted(false);
        button.setText("X");
        button.setBorder(BorderFactory.createLineBorder(Color.darkGray, 2));
        button.setBackground(Color.black);
        button.setForeground(Color.red);
        button.setSize(25, 25);
        button.setLocation(210, y);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                    watchlistbox.list.removeticker(index);
                    updatebuttons(tickers);
                    repaint();
            }
        });
        return (button);
    }

    public void paintComponent(Graphics g2)
    {
        Graphics2D g = (Graphics2D)g2;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 250, 500);

        g.setColor(Color.white);
        int noffset;
        int loffset;

        noffset = 25;
        loffset = 45;
        g.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        for (int i = 0; i < tickers.size(); i++)
        {
            g.drawString(tickers.get(i), 30, noffset);
            g.drawLine(0, loffset, 250, loffset);
            noffset += 50;
            loffset += 50;
        }
    }
}
