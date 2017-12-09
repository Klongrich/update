package watchlist;

import chart.livetickers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Formatter;

/**
 * Created by klongrich on 3/2/17.
 */
public class watchlist extends JPanel implements MouseWheelListener, ActionListener {

    private int offset = 25;
    private ArrayList<String> names = new ArrayList<String>();
    private ArrayList<Double> change = new ArrayList<Double>();
    private Timer time;
    public String filename;
    String filepath;


    //Watchlist must be init with a text file that has a list of tickers seprated by newlines.
    public watchlist(String name, String filepath) {
        this.filepath = filepath;
        time = new Timer(10000, this);
        time.start();
        filename = name;
        setSize(180, 300);
        setVisible(true);
        addMouseWheelListener(this);

        try {
            FileReader fr = new FileReader(filepath + name + ".txt");
            BufferedReader buff = new BufferedReader(fr);

            String line = buff.readLine();
            while (line != null) {
                names.add(line);
                livetickers currentprice = new livetickers(line);
                change.add(((currentprice.price() / currentprice.yesterdayclose) - 1) * 100);
                line = buff.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void newlist(String name)
    {
        filename  = name;
        clear();
    }

    public void updatelist(String name)
    {
        filename = name;
        names.clear();
        try {
            FileReader fr = new FileReader(filepath + name + ".txt");
            BufferedReader buff = new BufferedReader(fr);

            String line = buff.readLine();
            while (line != null) {
                System.out.println(line);
                names.add(line);
                line = buff.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(names);
        update();

    }


    //Used to remove tickers from a watchlist.
    public void removeticker(int index)
    {
        Formatter x;
        names.remove(index);
        update();
        try {
            x = new Formatter(filepath + filename + ".txt");
            for (int i = 0; i < names.size(); i++)
            {
                x.format("%s\n", names.get(i));
            }
            x.close();

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    //Updates how much the stock has changed from yesterday.
    public void update()
    {
        change.clear();
        for (int i = 0; i < names.size(); i++)
        {
            livetickers currentprice = new livetickers(names.get(i));
            change.add(((currentprice.price()/ currentprice.yesterdayclose) - 1) * 100);
        }
        repaint();
    }

    //Clears out the list of tickers and their change on the day.
    public void clear()
    {
        names.clear();
        change.clear();
        try {
            Formatter x  = new Formatter(filepath + filename + ".txt");
            x.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        offset = 25;
        repaint();
    }

    //Adds a ticker to the current watchlist that is being viewed on the screen.
    public void addticker(String tick)
    {
        try {
            FileWriter fw = new FileWriter(filepath + filename + ".txt", true);
            fw.write(tick + "\n");
            fw.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        double move;
        livetickers currentprice = new livetickers(tick);
        move = ((currentprice.price()/ currentprice.yesterdayclose) - 1) * 100;
        names.add(tick);
        change.add(move);
    }


    @Override
    public void actionPerformed(ActionEvent e)
    {
        //update();
    }

    //Painting all the feauters to the frame
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
            g.drawString(names.get(i), 20, offset + i * 50);
            if (change.get(i) > 0)
                g.setColor(Color.green);
            else
                g.setColor(Color.red);
            pchange = formatter.format(change.get(i));
            g.drawString(pchange, 115, offset + i * 50);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 10));
            g.drawString(" %", 155, offset + i * 50);
            g.fillRect(0, offset + 20 + (i * 50), 200, 3);
        }
        putborder(200, 300, g);
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


    //Allows users to scroll.
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

    public ArrayList <String> getNames() {return (names); }
}
