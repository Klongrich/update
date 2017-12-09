package data;

/**
 Created by klongrich on 2/23/17.
 **/

//import static org.jsoup.Jsoup.connect;

//import com.sun.org.apache.xpath.internal.SourceTree;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;

//import com.etrade.etws.sdk.client.MarketClient;

//Test URL string https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=SPY&interval=1min&outputsize=full&apikey=N9ZKKUR4HHBNIC7V

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class qoutes {

    ArrayList <Double> Open = new ArrayList <Double>();
    ArrayList <Double> High = new ArrayList <Double>();
    ArrayList <Double> Low = new ArrayList <Double>();
    ArrayList <Double> Close = new ArrayList <Double>();
    ArrayList <Double> Volume = new ArrayList <Double>();
    ArrayList <Double> Marketcap = new ArrayList<Double>();
    String tick;

    private double parse_data(String str) {
        String cut_data;
        double data;
        String temp[];

        temp = str.split(":");
        cut_data = temp[1].replace("\"", "");
        cut_data = cut_data.replace(" ", "");
        cut_data = cut_data.replace(",", "");
        data = Double.parseDouble(cut_data);
        return (data);
    }

    public ArrayList<Double> reverse(ArrayList<Double> data) {
        if(data.size() > 1) {
            double value = data.remove(0);
            reverse(data);
            data.add(value);
        }
        return data;
    }


    //https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=" + tick + "&interval=" + intervals + "min&outputsize=full&apikey=N9ZKKUR4HHBNIC7V




    public qoutes(String tick, int intervals, int days)
    {
        this.tick = tick;

        if (intervals > 61)
            intervals = 5;

        try
        {
            URL url = new URL("https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=" + tick + "&interval=" + intervals + "min&outputsize=full&apikey=N9ZKKUR4HHBNIC7V");
            URLConnection con = url.openConnection();
            InputStreamReader stream = new InputStreamReader(con.getInputStream());
            BufferedReader buff = new BufferedReader(stream);

            String line = buff.readLine();

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
            Open = reverse(Open);
            High = reverse(High);
            Low = reverse(Low);
            Close = reverse(Close);
            Volume = reverse(Volume);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }



    public qoutes(String tick)
    {
        try
        {
            URL url = new URL("https://www.alphavantage.co/query?function=DIGITAL_CURRENCY_DAILY&symbol=" + tick + "&market=USD&apikey=N9ZKKUR4HHBNIC7V");
            URLConnection con = url.openConnection();
            InputStreamReader stream = new InputStreamReader(con.getInputStream());
            BufferedReader buff = new BufferedReader(stream);

            String line = buff.readLine();

            while (Open.size() < 754 && line != null)
            {
                //System.out.println(line);
                if (line.contains("open")) {
                    Open.add(parse_data(line));
                    line = buff.readLine();
                    line = buff.readLine();

                    High.add(parse_data(line));

                    line = buff.readLine();
                    line = buff.readLine();
                    Low.add(parse_data(line));

                    line = buff.readLine();
                    line = buff.readLine();
                    Close.add(parse_data(line));

                    line = buff.readLine();
                    line = buff.readLine();
                    Volume.add(parse_data(line));

                    line = buff.readLine();
                    Marketcap.add(parse_data(line));

                }

                line = buff.readLine();
            }
            Open = reverse(Open);
            High = reverse(High);
            Low = reverse(Low);
            Close = reverse(Close);
            Volume = reverse(Volume);

            while (line != null)
            {
                line = buff.readLine();
            }


        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    public ArrayList<Double> smoothed()
    {
        ArrayList <Double> temp = new ArrayList <Double>();
        for (int i = 0; i < Open.size(); i++)
            temp.add((Open.get(i) + Close.get(i) + High.get(i) + Low.get(i)) / 4);
        return (temp);
    }

    public void save()
    {
        int temp = 0;
        String o,h,l,c,v;
        DateFormat df = new SimpleDateFormat("MM-dd-yy");
        Date dateobj = new Date();

        if (tick != null)
        {
            try {
                FileWriter fw = new FileWriter("./src/data/pastdata/" + UpDateDatabase.index + "/" + tick + "/" + df.format(dateobj) + ".csv", true);
                fw.write("Open,High,Low,Close,Volume\n");
                for (int i = 0; i < Open.size(); i++) {
                    o = String.valueOf(Open.get(i));
                    h = String.valueOf(High.get(i));
                    l = String.valueOf(Low.get(i));
                    c = String.valueOf(Close.get(i));
                    v = String.valueOf((Volume.get(i)));

                    fw.write(o + "," + h + "," + l + "," + c + "," + v + "\n");
                    temp += 1;
                }
                fw.close();
                System.out.println(tick + " : " + temp);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public double getCurrentprice()
    {
        try
        {
            final String ticker = "[\"" + tick + "\",";
            URL url = new URL("https://www.google.com/finance?q=" + tick + "&ei=uZmvWNGABcax2AaQ1rvwDg");
            URLConnection urlConn = url.openConnection();
            InputStreamReader inStream = new InputStreamReader(urlConn.getInputStream());
            BufferedReader buff = new BufferedReader(inStream);

            String sprice = "Not found";
            String line = buff.readLine();
            while (line != null)
            {
                if(line.contains(ticker))
                {
                    int target = line.indexOf(ticker);
                    int deci = line.indexOf(".", target);
                    int start = deci;
                    while(line.charAt(start) != '\"')
                    {
                        start--;
                    }
                    sprice = line.substring(start + 1, deci + 3);
                }
                line = buff.readLine();
            }
            return (Double.parseDouble(sprice));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return (0);
    }


	/*
    public void googleSearch(String searchTerm, int numberofResults)
    {
        String URL = "https://www.google.com/search";
        String searchURL = URL + "?q="+searchTerm+"&num="+numberofResults;

        try {
            Document doc =connect(searchURL).userAgent("Mozilla/5.0").get();
            Elements results = doc.select("h3.r > a");

            for (Element result : results) {
                String linkHref = result.attr("href");
                String linkText = result.text();
                System.out.println("Text::" + linkText + ", URL::" + linkHref.substring(6, linkHref.indexOf("&")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	*/

    public ArrayList<Double> open()
    {
        return (Open);
    }

    public ArrayList<Double> high()
    {
        return (High);
    }

    public ArrayList<Double> low()
    {
        return (Low);
    }

    public ArrayList<Double> close()
    {
        return (Close);
    }

    public ArrayList<Double> volume() { return (Volume);}
}

