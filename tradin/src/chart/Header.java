package chart;

import javax.swing.*;
import java.awt.*;

/**
 * Created by klongrich on 3/16/17.
 */
public class Header extends JPanel {

    int width = 1300;
    int height = 30;
    String ticker = "SPY";

    LivePricepanel liveprice;

    public Header()
    {
        liveprice = new LivePricepanel(ticker);
        liveprice.setLocation(190, 0);
        add(liveprice);
        setVisible(true);
        setSize(width, height);
    }

    public void update(String name)
    {
        ticker = name;
        super.repaint();
        System.out.println("Changing name");
    }

    public void paintComponent(Graphics g2)
    {
        int y = 20;

        Graphics2D g = (Graphics2D) g2;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Color c =new Color(0.5803922f, 0.6f, 0.6392157f, 0.1f);
        g.setColor(c);
        g.fillRect(0, 0, width, height);


        g.setColor(Color.lightGray);
        g.fillRect(0, 0, width, 1);
        g.fillRect(0, height - 1, width, 1);
        g.fillRect(150, 0, 1, height );
        g.fillRect(350, 0, 1, height );
        g.fillRect(460, 0, 1, height );
        g.fillRect(560, 0, 1, height );
        g.fillRect(700, 0, 1, height );
        g.fillRect(918, 0, 1, height );

        g.setColor(Color.white);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 13));
        System.out.println(ticker);
        g.drawString("Ticker: " + ticker, 40, y);
        g.drawString("Current Price: 238.76" , 180, y);
        g.drawString("Bid: x18" , 375, y);
        g.drawString("Ask: x7" , 490, y);
        g.drawString("Volume: 71444", 585, y);

    }

}

