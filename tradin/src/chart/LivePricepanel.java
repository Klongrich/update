package chart;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by klongrich on 3/10/17.
 */
public class LivePricepanel extends JPanel implements ActionListener {

    livetickers x;
    Timer time;
    String ticker;
    String data;

    LivePricepanel(String ticker)
    {
        this.ticker = ticker;
        time = new Timer(100, this);
        setSize(100, 50);
        setBackground(Color.white);
        time.start();
    }

    public void update(String tick)
    {
        ticker = tick;
        x = new livetickers(ticker);
        data = Double.toString(x.price());
        repaint();
    }

    public void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        x = new livetickers(ticker);
        data = Double.toString(x.price());
        g2.setColor(Color.black);
        g2.fillRect(0, 0, 100, 50);
        g2.setColor(Color.white);
        g2.setFont(new Font("TimesRoman", Font.PLAIN, 15));
        g2.drawString(data, 25, 25);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //x = new livetickers(ticker);
        //data = Double.toString(x.price());
        //repaint();
    }
}

