package testing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by klongric on 3/20/17.
 */
public class nastickers {

    public static void main(String [] args)
    {
        try  {
            FileReader fr = new FileReader("./src/testing/companylist.csv");
            BufferedReader buff = new BufferedReader(fr);
            FileWriter fw = new FileWriter("./src/testing/nasdaqticks.txt");

            String line;
            String temp[];

            line = buff.readLine();
            while (line != null)
            {
                temp = line.split(",");
                temp = temp[0].split("\"");
                fw.write(temp[1] + "\n");
                System.out.println(temp[1]);
                line = buff.readLine();

            }

        }catch (IOException e)
        {
            e.printStackTrace();
        }


    }
}
