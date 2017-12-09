package indicators;

import java.util.ArrayList;

/**
 * Created by klongrich on 2/27/17.
 */
public class listone {

    public ArrayList<Double> sma(ArrayList<Double> data, int period)
    {
        ArrayList <Double> answer = new ArrayList <Double>();
        double temp;

        for (int i = period; i < data.size(); i++)
        {
            temp = 0;
            for (int j = 0; j < period; j++)
            {
                temp += data.get(i - j);
            }
            temp /= period;
            answer.add(temp);
        }
        return (answer);
    }

    public ArrayList<Double> ema(ArrayList <Double> data, int period)
    {
        ArrayList <Double> answer = new ArrayList <Double>();
        double mulit;
        double temp;
        double start;

        temp = 0;
        start = 0;
        mulit = (double)2 / (period + 1);
        for (int j = 0; j < period; j++)
        {
            start += data.get(j);
        }
        start /= period;
        answer.add((data.get(0) - start) * mulit + start);
        temp = start;
        for (int i = 1; i < data.size() - period; i++)
        {
            temp = (data.get(i) - temp) * mulit + temp;
            answer.add(temp);
        }
        return (answer);
    }


    public ArrayList<Double> bbupper(ArrayList <Double> data, int period, double multi)
    {
        ArrayList <Double> answer = new ArrayList<Double>();
        double stdev;
        double mean;
        double ma;

        stdev = 0;
        mean = 0;
        ma = 0;
        for (int i = period; i < data.size(); i++)
        {
            mean = 0;
            stdev = 0;
            ma = 0;
            for (int x = 0; x < period; x++)
            {
                mean += data.get(i - x);
                ma += data.get(i - x);
            }
            mean /= period;
            ma /= period;
            for (int j = 0; j < period; j++)
            {
                stdev += (data.get(i - j) - mean) * (data.get(i - j) - mean);
            }
            stdev /= period;
            stdev = Math.sqrt(stdev);
            answer.add(ma + (multi * stdev));
        }
        return (answer);
    }

    public ArrayList <Double> bblower(ArrayList <Double> data, int period, double multi)
    {
        ArrayList <Double> answer = new ArrayList<Double>();
        double stdev;
        double mean;
        double ma;

        stdev = 0;
        mean = 0;
        ma = 0;
        for (int i = period; i < data.size(); i++)
        {
            mean = 0;
            stdev = 0;
            ma = 0;
            for (int x = 0; x < period; x++)
            {
                mean += data.get(i - x);
                ma += data.get(i - x);
            }
            mean /= period;
            ma /= period;
            for (int j = 0; j < period; j++)
            {
                stdev += (data.get(i - j) - mean) * (data.get(i - j) - mean);
            }
            stdev /= period;
            stdev = Math.sqrt(stdev);
            answer.add(ma - (stdev * multi));
        }
        return (answer);
    }
}

