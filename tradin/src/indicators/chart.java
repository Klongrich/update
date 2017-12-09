package indicators;

import data.qoutes;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by klongrich on 3/7/17.
 */

public class chart extends JPanel{

    String name = "STOCH";
    String parameters = "14";
    double width;
    ImageIcon icon;
    calculations x;
    listone y;
    ArrayList <ArrayList<Double>> extras = new ArrayList<ArrayList<Double>>();

    public chart(String name)
    {
        x = new calculations();
        y = new listone();
        qoutes data = new qoutes(name, 60, 2);
        width = 857;
        icon = init(x.stoch(data.close(), 20));

        setSize(857, 170);
        setVisible(true);
    }

    public void update(String name, String parameters, qoutes info)
    {
        ArrayList <Double> data = new ArrayList <Double>();
        String temp[];
        int p1 = 0;
        int p2 = 0;
        int p3 = 0;

        data = info.open();
        if (parameters != null) {
            this.name = name;
            this.parameters = parameters;
            temp = parameters.split(",");

            //Parsing numbers
            if (temp.length == 1) {
                p1 = Integer.parseInt(temp[0]);
            } else if (temp.length == 2) {
                p1 = Integer.parseInt(temp[0]);
                p2 = Integer.parseInt(temp[1]);
            } else if (temp.length == 3) {
                p1 = Integer.parseInt(temp[0]);
                p2 = Integer.parseInt(temp[1]);
                p3 = Integer.parseInt(temp[2]);
            }

            //Redrawing chart
            if (name == "STOCH")
            {
                extras.add(y.sma(x.stoch(data, p1), p2));
                icon = init(x.stoch(data, p1));
            }
            else if (name == "MACD")
            {
                extras.add(y.ema(x.macd(data, p1, p2), p3));
                icon = init(x.macd(data, p1, p2));
            }
            else if (name == "RSI")
            {
                icon = init(x.rsi(data, p1));
            }
            else if (name == "ADX")
            {

            }
            else if (name == "OBV")
            {
                icon = init(x.obv(data, info.volume()));
            }
            else if (name =="ROC")
            {
                icon = init(x.wma(data, p1));
            }
            else if (name == "COPPOCK")
            {
                icon = init(x.cc(data, p1, p2, p3));
            }
            else if (name == "%R")
            {
                icon = init(x.w(data, p1));
            }

            repaint();
        }
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

    public ArrayList <Double> getconvertedpoints(ArrayList <Double> data)
    {
        ArrayList <Double> convert = new ArrayList <Double>();
        ArrayList <Double> points = new ArrayList <Double>();

        convert = minmax(data, minValue(data), maxValue(data));
        for (int i = 0; i < data.size(); i++)
            points.add(((100 * convert.get(i)) - 100) * -1);
        return (points);
    }

    public ImageIcon init(ArrayList<Double> data)
    {
        BufferedImage image = new BufferedImage(865, 450, BufferedImage.TYPE_3BYTE_BGR);
        ArrayList <Double> ypoints = new ArrayList<Double>();
        double xvalue;
        double yvalue;
        double price;
        double max;
        int x;

        max = maxValue(data);
        ypoints = getconvertedpoints(data);
        xvalue = width / data.size();
        yvalue = (maxValue(data) - minValue(data)) / 10;
        x = ypoints.size() - 1;
        Graphics2D g = (Graphics2D)image.getGraphics();

        //BackGround Lines
        for (int i = 0; i < 180; i += 10)
        {
            Color c =new Color(0.5803922f, 0.6f, 0.6392157f, 0.2f);
            g.setColor(c);
            g.drawLine(0, i, 800, i);
        }

        //Drawing the y-values and dashes
        g.setColor(Color.white);
        g.fillRect(807, 0, 3, 178);
        for (int i = 0; i < 180; i += 10) {
            g.fillRect(802, i, 5, 2);
            max -= yvalue;
        }

        max = maxValue(data);
        for (int i = 0; i < 160; i += 10) {
            if (i >= 35) {
                price = Math.round(max * 100.0) / 100.0;
                max -= yvalue;
                if (i % 20 == 0) {
                    g.drawString(Double.toString((price)), 818, i + 7);
                }
            }
        }

        //Drawing the acutal chart
        if (xvalue > 1)
            xvalue = 1;
        g.setColor(Color.BLUE);
        for (double i = 800; i > 800 - ypoints.size() + 1; i -= xvalue) {
            x--;
            g.draw(new Line2D.Double(i, ypoints.get(x) + 35, i, ypoints.get(x + 1) + 35));
        }

        //Drawing extras, signal lines, extc....
        if (extras.size() > 0) {
            g.setColor(Color.green);
            ArrayList<Double> temp = new ArrayList<Double>();
            temp = extras.get(0);
            temp = getconvertedpoints(temp);
            x = temp.size() - 1;
            for (double i = 800; i > 800 - temp.size() + 1; i -= xvalue) {
                x--;
                g.draw(new Line2D.Double(i, temp.get(x) + 35, i, temp.get(x + 1) + 35));
            }
            extras.clear();
        }

        //Drawing paramter values
        DecimalFormat form = new DecimalFormat("#.##");
        if (parameters == "0")
            g.drawString(name, 10, 20);
        else
            g.drawString(name + " (" + parameters + ")" + "  " + form.format(data.get(data.size() - 1)) , 10, 20);

        ImageIcon icon = new ImageIcon(image);
        return (icon);
    }

    public void paintComponent(Graphics g)
    {
        icon.paintIcon(this, g, 0, 0);
    }
}

