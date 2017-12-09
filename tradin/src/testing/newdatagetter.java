package testing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import jdk.nashorn.internal.parser.*;

/**
 * Created by klongric on 11/30/17.
 */


//API KEY = N9ZKKUR4HHBNIC7V


public class newdatagetter {

    ArrayList<Double> Open = new ArrayList <Double>();
    ArrayList <Double> High = new ArrayList <Double>();
    ArrayList <Double> Low = new ArrayList <Double>();
    ArrayList <Double> Close = new ArrayList <Double>();
    ArrayList <Double> Volume = new ArrayList <Double>();
    String tick;

    public newdatagetter(String tick, int intervals)
    {
        this.tick = tick;

        try
        {
            URL url = new URL("https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=" + tick + "&interval=" + intervals + "min&outputsize=full&apikey=N9ZKKUR4HHBNIC7V");
            URLConnection con = url.openConnection();
            InputStreamReader stream = new InputStreamReader(con.getInputStream());
            BufferedReader buff = new BufferedReader(stream);

            String sURL = "http://freegeoip.net/json/"; //just a string

            // Connect to the URL using java's native library;
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();

            /*
            // Convert to a JSON object to print data
            JsonParser jp = new JsonParser(buff); //from gson

            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); //Convert the input stream to a json element
            JsonObject rootobj = root.getAsJsonObject(); //May be an array, may be an object.
            zipcode = rootobj.get("zip_code").getAsString(); //just grab the zipcode
            */
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
