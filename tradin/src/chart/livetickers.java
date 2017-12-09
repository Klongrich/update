package chart;

//import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by klongrich on 3/3/17.
 */
public class livetickers {

    public double yesterdayclose;
    double cp;

    String tick;
    InputStreamReader stream;

    public livetickers(String tick)
    {
        this.tick = tick;
    }

    public double price()
    {
        try {
            URL url = new URL("http://www.nasdaq.com/symbol/" + tick + "/real-time");
            URLConnection con = url.openConnection();
            InputStreamReader stream = new InputStreamReader(con.getInputStream());
            BufferedReader buff = new BufferedReader(stream);

            String line = buff.readLine();
            String temp[];
            while (line != null)
            {
                if (line.contains("quotes_content_left__LastSale")) {
                    temp = line.split(">");
                    if (temp.length > 1) {
                        temp = temp[1].split("<");
                        if (temp[0].contains("not"))
                            return  (0);
                        else
                            cp = Double.parseDouble(temp[0]);
                    }
                }

                if (line.contains("<td>"))
                {
                    if (line.contains("PreviousClose")) {
                        temp = line.split("/");
                        temp = temp[0].split(">");
                        temp = temp[2].split("<");
                        yesterdayclose = Double.parseDouble(temp[0]);
                        return (cp);
                    }
                }
                line = buff.readLine();
            }

        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return (0);
    }

    public double yesterdayClose()
    {
        return (yesterdayclose);
    }

}
