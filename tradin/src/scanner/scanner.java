package scanner;

import data.indaymovers;
import chart.livetickers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * Created by klongrich on 3/2/17.
 */
public class scanner extends JPanel implements MouseWheelListener, ActionListener {

    private int offset = 25;
    private ArrayList <String> names = new ArrayList<String>();
    private ArrayList <String> intinames = new ArrayList<String>();
    private ArrayList <Double> change = new ArrayList<Double>();
    private Timer time;
    private indaymovers x;
    public String selected = "SPY";
    int pos = 0;

    public scanner()
    {
        //x = new indaymovers();
        time = new Timer(10000, this);
        time.start();
        //intinames = x.geticks();
        inti();

        setSize(180, 300);
        setVisible(true);
                addMouseWheelListener(this);
    }

        public void putborder(int x, int y, Graphics g)
        {
            int width;

            width = 2;
            g.setColor(Color.black);
            g.fillRect(0, 0, width, y);
            g.fillRect(0,0, x, width);
            g.fillRect(0, y - width, x, width);
            g.fillRect(x - width, 0, width, y);
        }

        public void updateSelectedLeft()
        {
            if (pos > 0)
            {
                pos -= 1;
            }
            selected = names.get(pos);
        }

        public void updateSelectedRight()
        {
            if (pos < names.size() - 1)
            {
                pos += 1;
            }
            selected = names.get(pos);
        }

        public String getSelected()
        {
            return (selected);
        }

        public void clear()
        {
            names.clear();
            change.clear();
            offset = 25;
            repaint();
        }

        public void addticker(String tick)
        {
            double move;
            livetickers currentprice = new livetickers(tick);
            move = ((currentprice.price()/ currentprice.yesterdayclose) - 1) * 100;
            names.add(tick);
            change.add(move);
        }

        public void inti()
        {
            double price;

            change.clear();
            for (int i = 0; i < intinames.size(); i++) {
                livetickers currentprice = new livetickers(intinames.get(i));
                price = currentprice.price();
                if (price > 0) {
                    names.add(intinames.get(i));
                    change.add(((price / currentprice.yesterdayclose) - 1) * 100);
                }
            }
            repaint();
        }

        public void update()
        {
            change.clear();
            for (int i = 0; i < names.size(); i++)
            {
                livetickers currentprice = new livetickers(names.get(i));
                change.add(((currentprice.price() / currentprice.yesterdayclose) - 1) * 100);
            }
            repaint();
        }

        public void scan()
        {
            x = new indaymovers();
            names = x.geticks();
            for (int i = 0; i < names.size(); i++)
            {
                addticker(names.get(i));
            }
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            //update();
        }

        public void paintComponent(Graphics g2)
        {
            Graphics2D g = (Graphics2D) g2;
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            String pchange;
            Color c =new Color(0x1E1E1E);
            g.setColor(c);
            g.fillRect(0,0,200,300);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 15));
            NumberFormat formatter = new DecimalFormat("#0.00");

            for (int i = 0; i < names.size(); i++) {
                g.setFont(new Font("TimesRoman", Font.PLAIN, 15));
                g.setColor(Color.white);
                if (names.get(i) == selected)
                {
                    g.setColor(Color.yellow);
                }
                g.drawString(names.get(i), 20, offset + i * 50);
                if (change.get(i) > 0)
                    g.setColor(Color.green);
                else
                    g.setColor(Color.red);
                pchange = formatter.format(change.get(i));
                g.drawString(pchange, 120, offset + i * 50);
                g.setFont(new Font("TimesRoman", Font.PLAIN, 10));
                g.drawString(" %", 160, offset + i * 50);
                g.fillRect(0, offset + 20 + (i * 50), 200, 3);
            }
            putborder(200, 300, g);
        }

        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            double morespace;

            morespace = 25 - ((names.size() - 6) * 55);
            if (e.getWheelRotation() == 1) {
                if (names.size() > 6)
                {
                    if (offset > morespace)
                    {
                        offset -= 20;
                    }
            }
        }
        else if(e.getWheelRotation() == -1)
        {
            if (offset <= 20) {
                offset += 20;
            }
        }
        repaint();
    }
}