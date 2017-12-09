
package data;

//import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by klongrich on 2/25/17.
 */
public class UpDateDatabase {

    static String index = "Null";

    public void update()
    {
        try
        {
            FileReader fr = new FileReader("./src/data/tickers/spxticks.txt");
            FileReader fr2 = new FileReader("./src/data/tickers/dowticks.txt");
            FileReader fr3 = new FileReader("./src/data/tickers/nasdaqticks.txt");

            BufferedReader buff = new BufferedReader(fr);
            BufferedReader buff2 = new BufferedReader(fr2);
            BufferedReader buff3 = new BufferedReader(fr3);


            index = "SPX";
            for (int i = 0; i < 505; i++)
            {
                qoutes spxdata = new qoutes(buff.readLine() ,60,1);
                spxdata.save();
            }

            index = "DJI";
            for (int i = 0; i < 30; i++)
            {
                qoutes dowdata = new qoutes(buff2.readLine(), 60, 1);
                dowdata.save();
            }


            /*
            index = "NASDAQ";
            for (int i = 0; i < 3168; i++)
            {
                qoutes nasdaqdata = new qoutes(buff3.readLine(), 60, 1);
                nasdaqdata.save();
            }
            */
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }


}
