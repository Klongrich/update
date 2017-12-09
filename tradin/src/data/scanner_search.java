package data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by klongric on 12/3/17.
 */
public class scanner_search {

    ArrayList <String> tickers = new ArrayList<String>();

    public scanner_search()
    {
        try {
            URL url = new URL("https://finance.yahoo.com/screener/unsaved/f466976a-332c-4654-9548-9fff2a99edce?offset=0&count=100");
            URLConnection con = url.openConnection();
            InputStreamReader stream = new InputStreamReader(con.getInputStream());
            BufferedReader buff = new BufferedReader(stream);

            String line = buff.readLine();
            String temp[];

            while (line != null)
            {
                if (line.contains("{symbol}"))
                {
                    temp = line.split("}");
                    for (int i = 0; i < temp.length; i++)
                    {
                        if (temp[i].contains("pageCategory"))
                        {
                            System.out.println(temp[i]);
                            temp = temp[i].split(",");
                            System.out.println(temp[1]);
                            System.out.println(temp[2]);
                            System.out.println(temp[3]);

                        }
                    }
                }
                line = buff.readLine();
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}
