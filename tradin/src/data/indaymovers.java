package data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by klongrich on 3/16/17.
 */
public class indaymovers {

    ArrayList <String> tickers = new ArrayList<String>();

    public indaymovers()
    {
        try
        {
            URL url = new URL("https://www.thestreet.com/markets/gainers.html");
            URLConnection con = url.openConnection();
            InputStreamReader stream = new InputStreamReader(con.getInputStream());
            BufferedReader buff = new BufferedReader(stream);

            String line = buff.readLine();
            String temp[];
            while (line != null)
            {
                if (line.contains("market_gainers_NASDAQ")) {
                    //System.out.println(line);
                    temp = line.split("/");
                    temp = temp[2].split("\\.");
                    tickers.add(temp[0]);
                    System.out.println(temp[0]);
                }
                line = buff.readLine();
                line = buff.readLine();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public ArrayList <String> geticks()
    {
        return (tickers);
    }
}

