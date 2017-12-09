package chart;

import indicators.listone;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class Overlays extends JDialog {

    listone x;
    ArrayList <Double> data = new ArrayList <Double> ();
    static ArrayList<ArrayList<Double>> indactors = new ArrayList <ArrayList<Double>>();

    boolean sma = false;
    boolean ema = false;
    boolean bb = false;

    public Overlays(ArrayList<Double> data)
    {
        this.data = data;
        setSize(200, 200);
        //setResizable(false);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(getParent());
        setBackground(Color.black);
        setLayout(new GridLayout(2, 2));
        x = new listone();
        add(SMA(init()));
        add(EMA(init()));
        add(BB(init()));
        add(CLEAR(init()));
    }

    public void upadate(ArrayList<Double> data)
    {
        indactors.clear();
        if (sma)
            indactors.add(x.sma(data, 30));
        if (ema)
            indactors.add(x.ema(data, 21));
        if (bb)
        {
            indactors.add(x.sma(data, 20));
            indactors.add(x.bbupper(data, 20, 2));
            indactors.add(x.bblower(data, 20, 2));
        }
    }

    public JButton init()
    {
        JButton button = new JButton();
        button.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.darkGray, 2));
        button.setLocation(100, 100);
        button.setSize(100, 25);
        button.setBackground(Color.gray);
        button.setForeground(Color.white);
        button.setOpaque(true);
        return (button);
    }

    public JButton CLEAR(JButton button)
    {
        button.setText("CLEAR");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                sma = false;
                ema = false;
                bb = false;
            }
        });
        return (button);
    }

    public JButton SMA(JButton button)
    {
        button.setText("SMA");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                sma = true;
            }
        });
        return (button);
    }

    public JButton EMA(JButton button)
    {
        button.setText("EMA");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                ema = true;
            }
        });
        return (button);
    }

    public JButton BB(JButton button)
    {
        button.setText("BB");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                bb = true;
            }
        });
        return (button);
    }

    public ArrayList <ArrayList<Double>> getinda()
    {
        return(indactors);
    }
}
