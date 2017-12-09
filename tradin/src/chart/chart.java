package chart;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.io.IOException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.awt.image.*;
import java.util.Date;
import java.util.Locale;

/**
 * Created by klongrich on 2/23/17.
 */

import data.qoutes;


public class chart extends JFrame implements ActionListener, MouseListener, MouseMotionListener {

    double yline = 0;
    double xline = 0;
    boolean newchart = true;
    private int width;
    private int height;
    private double max = 0;
    double maxtemp = 0;
    private double min = 0;
    private double xvalue = 0;
    private double yvalue = 0;
    private int numberofdays = 2;
    private int intervals = 60;
    static String parameters = "14,3";
    String currentinda = "STOCH";
    public String tick;
    private ArrayList<Double> ypoints = new ArrayList <Double>();
    static ArrayList<ArrayList<Double>> indactors = new ArrayList <ArrayList<Double>>();


    ImageIcon icon;
    qoutes data;


    public chart(String tick)
    {
        width = 1300;
        height = 710;

        this.tick = tick;

        setLayout(null);
        setSize(width, height);
        setResizable(false);
        setBackground(Color.black);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Adding buttons
        int offset = 25;
        int space = 170;

        add(createButton("30 Min", offset, 1800, 50));
        offset += space;
        add(createButton("15 Min", offset, 900, 30));
        offset += space;
        add(createButton("10 Min", offset, 600, 20));
        offset += space;
        add(createButton("5 Min", offset, 300, 10));
        offset += space;
        add(createButton("1 Min", offset, 60, 2));

        addMouseListener(this);
        addMouseMotionListener(this);
        setVisible(true);
    }

    public JButton createButton(String name, int x, final int interval, final int days) {
        JButton button = new JButton(name);
        button.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                newchart = true;
                ArrayList<Double> x = new ArrayList <Double>();
                qoutes newdata = new qoutes(tick, interval, days);
                data = newdata;
                x = data.smoothed();
                numberofdays = days;
                intervals = interval;
                icon = init(x);
                repaint();
            }
        });
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.darkGray, 2));
        button.setLocation(x, 509 - 20);
        button.setBackground(Color.black);
        button.setOpaque(true);
        button.setSize(110, 25);
        button.setForeground(Color.white);
        return (button);
    }


    public JButton linechart()
    {
        JButton button = new JButton("Line");
        button.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.darkGray, 2));
        button.setLocation(950, 2);
        button.setSize(100, 25);
        button.setBackground(Color.gray);
        button.setForeground(Color.white);
        return (button);

    }

    public JButton candlechart()
    {
        JButton button = new JButton("Candle Stick");
        button.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.darkGray, 2));
        button.setLocation(1100, 2);
        button.setSize(100, 25);
        button.setBackground(Color.gray);
        button.setForeground(Color.white);
        return (button);

    }


    private double maxValue(ArrayList<Double> array) {
        double max;

        max = array.get(0);
        for (int i = 0; i < array.size(); i++) {
            if (array.get(i) > max) {
                max = array.get(i);
            }
        }
        return (max);
    }

    private double minValue(ArrayList<Double> array) {
        double min;

        min = array.get(0);
        for (int i = 0; i < array.size(); i++) {
            if (array.get(i) < min) {
                min = array.get(i);
            }
        }
        return (min);
    }

    private ArrayList<Double> minmax(ArrayList<Double> data, double min, double max) {
        ArrayList<Double> x = new ArrayList <Double>();
        for (int i = 0; i < data.size(); i++)
            x.add((data.get(i) - min) / (max - min));
        return (x);
    }


    public ImageIcon init(ArrayList<Double> stockdata)
    {
        min = minValue(stockdata);
        max = maxValue(stockdata);
        ypoints = points(stockdata);
        xvalue = width / stockdata.size();
        yvalue = (maxValue(stockdata) - minValue(stockdata)) / 20;
        int x;

        x = ypoints.size() - 1;

        BufferedImage image = new BufferedImage(800, 450, BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D g = (Graphics2D)image.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


        //Drawing Volume
        if (xvalue > 1)
            xvalue = 1;
        ArrayList <Double>  volume = new ArrayList <Double>();
        volume = volumepoints(data.volume());
        g.setColor(Color.blue);
        for (double i = 800; i > 800 - ypoints.size() + 1; i -= xvalue)
        {
            x--;
            g.draw(new Line2D.Double(i ,volume.get(x) + 350, i, 450));
        }

        //Drawing back ground lines
        Color c =new Color(0.5803922f, 0.6f, 0.6392157f, 0.2f);
        g.setColor(c);
        for (int i = 0; i < 450; i += 20)
        {
            g.drawLine(0, i, 900, i);
        }

        //Putting the ticker name in the background
        g.setFont(new Font("TimesRoman", Font.PLAIN, 100));
        g.drawString(tick, 280, 230);


        //Drawing the actual chart
        x = ypoints.size() - 1;
        g.setColor(Color.red);
        for (double i = 800; i > 800 - ypoints.size() + 1 ; i -= xvalue) {
            x--;
            if (ypoints.get(x) > ypoints.get(x + 1))
                g.setColor(Color.green);
            else
                g.setColor(Color.red);
            g.draw(new Line2D.Double(i, ypoints.get(x), i , ypoints.get(x + 1)));
        }

        //Drawing the Overlays
        ArrayList <Double> convert = new ArrayList <Double> ();
        g.setColor(Color.green);
        for (int ii = 0; ii < indactors.size(); ii++)
        {
            convert = points(indactors.get(ii));
            x = convert.size() - 1;
            g.setColor(Color.blue);
            for (double i = 800; i > 780 - convert.size() + 21; i -= (width / convert.size())) {
                x--;
                g.draw(new Line2D.Double(i, convert.get(x), i , convert.get(x + 1)));
            }
        }


        //Drawing the x-axis
        //Drawing 15min
        if (numberofdays == 20) {
            String month = "03/";
            g.setFont(new Font("TimesRoman", Font.PLAIN, 12));
            int j = 11;
            String temp;
            for (int i = 800; i > 0; i--) {
                if (i % 79 == 0) {
                    g.setColor(Color.white);
                    temp = month + Integer.toString(j);
                    if (i != 790)
                        g.drawString(temp, i - 5, 440);
                    j -= 2;
                    g.fillRect(i + 10, 445, 2, 5);
                    g.setColor(c);
                    g.drawLine(i + 10, 0, i + 10, 500);
                }
                if (j == -1)
                {
                    j = 27;
                    month = "02/";
                }
            }
        }

        //Drawing 10min chart
        if (numberofdays == 10) {
            g.setFont(new Font("TimesRoman", Font.PLAIN, 12));
            int j = 11;
            String temp;
            for (int i = 800; i > 0; i--) {
                if (i % 79 == 0) {
                    g.setColor(Color.white);
                    temp = "03/" + Integer.toString(j);
                    if (i != 790)
                        g.drawString(temp, i - 5, 440);
                    j--;
                    g.fillRect(i + 10, 445, 2, 5);
                    g.setColor(c);
                    g.drawLine(i + 10, 0, i + 10, 500);
                }
            }
        }

        //Drawing min chart
        if (numberofdays == 2) {
            DateFormat df = new SimpleDateFormat("MM/dd");
            Date dateobj = new Date();

            g.setFont(new Font("TimesRoman", Font.PLAIN, 12));
            int time = 13;
            String ampm = "pm";
            String temp;
            for (int i = 800; i > 0; i--) {
                if (i % 60 == 0) {
                    g.setColor(Color.white);

                    if (time == 13) {
                        time = 1;
                        ampm = "pm";
                    }
                    temp = Integer.toString(time);
                    g.drawString(temp + ampm, i - 10, 440);
                    if (time == 7) {
                        g.drawString(df.format(dateobj), i - 15, 420);
                        time = 14;
                    }
                    if (time == 1) {
                        time = 13;
                        ampm = "am";
                    }
                    time--;
                    g.fillRect(i, 445, 2, 5);
                    g.setColor(c);
                    g.drawLine(i, 0, i, 500);
                }
            }
        }

        ImageIcon icon = new ImageIcon(image);
        return (icon);

    }

    private void putprice(Graphics g2) {
        double price;

        Graphics2D g = (Graphics2D) g2;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.setColor(Color.white);
        maxtemp = max;
        for (int i = 50; i < 460; i += 20) {
            price = max;
            price = Math.round(price * 100.0) / 100.0;
            g.drawString(Double.toString((price)), 845, i + 8);
            g.fillRect(831, i, 5, 3);
            max -= yvalue;
        }
        max = maxtemp;
    }


    public ArrayList <Double> volumepoints(ArrayList <Double> data)
    {
        double min;
        double max;
        ArrayList <Double> convert = new ArrayList <Double>();
        ArrayList <Double> points = new ArrayList <Double>();

        min = minValue(data);
        max = maxValue(data);
        convert = minmax(data, min, max);
        for (int i = 0; i < data.size(); i++)
            points.add(((100 * convert.get(i)) - 100) * -1);
        return (points);
    }

    public ArrayList <Double> points(ArrayList <Double> data)
    {
        ArrayList <Double> convert = new ArrayList <Double>();
        ArrayList <Double> points = new ArrayList <Double>();

        //max = maxValue(data);
        //min = minValue(data);
        convert = minmax(data, min, max);

        for (int i = 0; i < data.size(); i++)
            points.add(((400 * convert.get(i)) - 400) * -1);

        return (points);
    }

    public void paint(Graphics g2) {

        Graphics2D g = (Graphics2D) g2;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (newchart == true)
        {
            //livetickers currentprice = new livetickers(tick);
            int y;
            int h;

            //if (macoffset == true) {
                y = 40;
                h = 50;
            //}
            //else
            //{
            //    y = 20;
            //    h = 30;
            //}

            g.setColor(Color.black);
            g.fillRect(0, 0, width, h);

            Color c =new Color(0.5803922f, 0.6f, 0.6392157f, 0.1f);
            g.setColor(c);
            g.fillRect(0, 0, width, h);

            g.setColor(Color.lightGray);
            g.fillRect(0, 0, width, 1);
            g.fillRect(0, h - 1, width, 1);
            g.fillRect(150, 0, 1, h );
            g.fillRect(350, 0, 1, h);
            g.fillRect(460, 0, 1, h);
            g.fillRect(560, 0, 1, h);
            g.fillRect(740, 0, 1, h);
            g.fillRect(918, 0, 1, h);


            g.setColor(Color.white);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 13));

                g.drawString("Ticker: " + tick, 40, y);
               // g.drawString("Current Price: " + currentprice.price() , 180, y);
                g.drawString("Bid 714", 375, y);
                g.drawString("Ask: 714" , 490, y);
                g.drawString("Volume: 714,000" , 585, y);


            /*
            g.setColor(Color.white);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 13));
            g.drawString("Ticker: " + tick, 40, y);
            g.drawString("Current Price: " + currentprice.price() , 180, y);
            g.drawString("Bid: x18" , 375, y);
            g.drawString("Ask: x7" , 490, y);
            g.drawString("Volume: 71,417,714" , 585, y);
            */

            g.setColor(Color.black);
            g.fillRect(840, 50, 70, 450);
            //g.fillRect(50, 10, 110, 30);
            //g.fillRect(730,10,110, 30);

            //Putting the ticker name and period
            g.setColor(Color.WHITE);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
            //g.drawString("Watchlist", 960, 80);
            g.drawString("indicators", 980, 530);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 15));
            // g.drawString("Ticker: " + tick, 50, 25);
            //g.drawString("Days : " + numberofdays, 730, 25);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 12));

            //y-axis
            g.fillRect(835, 50, 3, 450);

            //x-axis
            g.fillRect(20, 500, width , 3);
            g.fillRect(20, 540, width , 3);
            putprice(g);
        }

        if (icon != null)
            icon.paintIcon(this, g, 30, 50);

        g.setColor(Color.magenta);
        if (xline > 15 && xline < 825 && yline < 500 && yline > 50)
        {
            g.draw(new Line2D.Double(32.0, yline, 825, yline));
            g.draw(new Line2D.Double(xline, 52, xline, 498));
        }
    }

    public void actionPerformed(ActionEvent e) {

    }



    @Override
    public void mouseClicked(MouseEvent e) {
        ArrayList <Double> data = new ArrayList <Double>();
        int x;
        int y;

        x = e.getX();
        y = e.getY();

        if (x > 70 && y < 500 && x < 970 && y > 50)
        {
            String name = JOptionPane.showInputDialog(this, "Ticker");
            qoutes stuff = new qoutes(name, intervals, numberofdays);
            data = stuff.smoothed();
            this.data = stuff;
            if (!data.isEmpty())
            {
                ypoints.clear();
                tick = name;
               // OverlayPanel.upadate(data);
               // indactors = OverlayPanel.getinda();
                //inda.update(currentinda, parameters, stuff);
                icon = init(data);
                newchart = true;
                repaint();
            }
        }
    }

    public void mouseMoved(MouseEvent e) {
        // System.out.println("X : " + e.getX() * xvalue);
        //System.out.println("Y : " + (((((double)e.getY() - 50) / (500 - 50)) * max) - max) * -1);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (e.getX() > 30 && e.getX() < 825 && e.getY() < 500 && e.getY() > 50) {
            yline = (double) e.getY();
            xline = (double) e.getX();
            repaint();
        }
        newchart = false;
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {
        xline = -10;
        yline = -10;
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {}
}

