package watchlist;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

/**
 * Created by klongric on 3/22/17.
 */
public class selectmenu extends JPanel {

    File[] files;

    selectmenu(String filepath)
    {
        File folder = new File(filepath);
        File[] files = folder.listFiles();
        this.files = files;

        setLayout(new GridLayout(files.length, 1));
        setSize(500, 500);

        for (int i = 0; i < files.length; i++)
            add(label(files[i].getName()));
    }

    public JLabel label(final String listname)
    {
        final String temp[];

        temp = listname.split("\\.");
        JLabel label = new JLabel("name");
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                watchlistbox.list.updatelist(temp[0]);
                watchlistbox.title = temp[0];
                super.mouseClicked(e);
            }
        });
        Border border = BorderFactory.createLineBorder(Color.white, 1);
        label.setBackground(Color.black);
        label.setForeground(Color.white);
        label.setOpaque(true);
        label.setText(listname);
        label.setBorder(border);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setPreferredSize(new Dimension(150, 50));
        return (label);
    }


    public void paintComponent(Graphics g2)
    {
        Graphics2D g = (Graphics2D)g2;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(Color.gray);
        g.fillRect(0, 0, 250, 500);
    }

}

