package testing.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;


/**
 * Created by klongric on 12/2/17.
 */

class qoutes {

    ArrayList<Double> Open = new ArrayList <Double>();
    ArrayList <Double> High = new ArrayList <Double>();
    ArrayList <Double> Low = new ArrayList <Double>();
    ArrayList <Double> Close = new ArrayList <Double>();
    ArrayList <Double> Volume = new ArrayList <Double>();
    String tick;
    String data;

    private String cut_string(String str)
    {
        char result[];
        String answer;

        for (int i = 0; i < str.length(); i++)
        {
        }

        return (null);
    }

    private double parse_data(String str) {
        String cut_data;
        double data;
        String temp[];

        temp = str.split(":");
        System.out.println("temp:" + temp[1]);
        cut_data = temp[1].replace("\"", "");
        cut_data = cut_data.replace(" ", "");
        cut_data = cut_data.replace(",", "");

        System.out.println("cut_data:" + cut_data);

        data = Double.parseDouble(cut_data);

        System.out.println("data!: " + data);
        return (data);
    }

    public qoutes(String tick, int intervals, int days)
    {
        this.tick = tick;
        this.data = data;

        try
        {
            URL url = new URL("https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=" + tick + "&interval=" + intervals + "min&outputsize=full&apikey=N9ZKKUR4HHBNIC7V");
            URLConnection con = url.openConnection();
            InputStreamReader stream = new InputStreamReader(con.getInputStream());
            BufferedReader buff = new BufferedReader(stream);

            String line = buff.readLine();
            String temp[];

            while (line != null)
            {
                System.out.println(line);
                if (line.contains("open")) {
                    Open.add(parse_data(line));
                    line = buff.readLine();

                    High.add(parse_data(line));
                    line = buff.readLine();

                    Low.add(parse_data(line));
                    line = buff.readLine();

                    Close.add(parse_data(line));
                    line = buff.readLine();

                    Volume.add(parse_data(line));
                }
                line = buff.readLine();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    public static void main(final String[] argv) {

        System.out.println("Hello world");
        qoutes x = new qoutes("SPY", 5, 2);

    }
}

