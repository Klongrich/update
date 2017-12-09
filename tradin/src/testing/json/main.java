package testing.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by klongric on 12/2/17.
 */

class newdatagetter {

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

    private double parse_data(String str)
    {
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

        /*
        double  data;
        char    temp[];
        char    result[] = null;
        String  cut_data;
        int     j;

        temp = str.toCharArray();
        j = 0;


        System.out.println("Temp: " + temp[3]);
        for (int i = 0; i < temp.length; i++)
        {
            if (temp[i] != '\"' && temp[i] != ' ')
            {
                result[j] = temp[i];
                j++;
            }
            i++;
        }
        System.out.println("Result: " + result);

    */
        /*
        temp = str.split(":");
        System.out.println("temp:" + temp[1]);
        StringBuilder sb = new StringBuilder(temp[1]);
        sb.deleteCharAt(0);
        sb.deleteCharAt(sb.length() - 2);
        sb.deleteCharAt(sb.length() - 1);
        System.out.println("cut_string:" + sb.toString());
        data = Double.parseDouble(sb.toString());
        System.out.println("data:" + data);
        */
    }

    public newdatagetter(String tick, int intervals)
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
                if (line.contains("open"))
                    parse_data(line);
                line = buff.readLine();
            }


        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}

public class main {


    private final static String JSON_DATA =
            "{"
                    + "  \"geodata\": ["
                    + "    {"
                    + "      \"id\": \"1\","
                    + "      \"name\": \"Julie Sherman\","
                    + "      \"gender\" : \"female\","
                    + "      \"latitude\" : \"37.33774833333334\","
                    + "      \"longitude\" : \"-121.88670166666667\""
                    + "    },"
                    + "    {"
                    + "      \"id\": \"2\","
                    + "      \"name\": \"Johnny Depp\","
                    + "      \"gender\" : \"male\","
                    + "      \"latitude\" : \"37.336453\","
                    + "      \"longitude\" : \"-121.884985\""
                    + "    }"
                    + "  ]"
                    + "}";


    public static void main(final String[] argv) {

        System.out.println("Hello world");
        newdatagetter x = new newdatagetter("SPY", 5);

        /*
        try {

            final JSONObject obj = new JSONObject(JSON_DATA);
            final JSONArray geodata = obj.getJSONArray("geodata");

            final int n = geodata.length();

            for (int i = 0; i < n; ++i) {

                final JSONObject person = geodata.getJSONObject(i);
                System.out.println(person.getInt("id"));
                System.out.println(person.getString("name"));
                System.out.println(person.getString("gender"));
                System.out.println(person.getDouble("latitude"));
                System.out.println(person.getDouble("longitude"));
            }

            newdatagetter x = new newdatagetter("SPY", 5);

            System.out.println(x.data);

            final JSONObject ob = new JSONObject(x.data);
            final JSONArray data = obj.getJSONArray("Time Series (5min)");

            final int len = geodata.length();

            System.out.println(len);


            for (int i = 0; i < len; ++i) {

                final JSONObject stock_data = geodata.getJSONObject(i);
                System.out.println(stock_data.get("1. open"));
            }



        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        */
    }
}
