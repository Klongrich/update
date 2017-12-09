package watchlist;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by klongric on 3/29/17.
 */
public class editmenubox extends JDialog {

    editmenu menu;


    public editmenubox(ArrayList<String> tickers)
    {
        editmenu menu = new editmenu(tickers);
        this.menu = menu;
        setLayout(null);
        menu.setLocation(0, 0);
        setSize(250, 500);
        add(menu);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(getParent());
    }

}
