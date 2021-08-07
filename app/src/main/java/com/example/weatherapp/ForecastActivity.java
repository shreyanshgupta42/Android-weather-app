package com.example.weatherapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class ForecastActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<Forecast> forecastbyday;
    ForecastArrayAdapter arrayAdapter;

    public class downloadtask extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... urls) {
            try{
                String result="";
                URL url=new URL(urls[0]);
                HttpURLConnection urlConnection=(HttpURLConnection)url.openConnection();
                InputStream in=urlConnection.getInputStream();
                InputStreamReader reader=new InputStreamReader(in);
                int data=reader.read();
                while(data!=-1){
                    char current=(char)data;
                    result+=current;
                    data=reader.read();
                }
                return result;
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.i("show weather",s);

            try{
                JSONObject jsonObject=new JSONObject(s);
                String list=jsonObject.getString("list");
                JSONArray arr=new JSONArray(list);
                for(int i=2;i<arr.length();i+=1){
                    JSONObject jsonpart=arr.getJSONObject(i);
                    String main=jsonpart.getString("main");
                    String date=jsonpart.getString("dt_txt");
                    JSONObject jsonObject2=new JSONObject(main);
                    String temp=jsonObject2.getString("temp");
                    Log.i("temp"+i,temp);
                    double t=Double.parseDouble(temp)-273.15;
                    String d=date.substring(8,10);
                    String time=date.substring(11,16);
                    String month=date.substring(5,7);
                    switch (month){
                        case "01":month="Jan";
                            break;
                        case "02":month="Feb";
                            break;
                        case "03":month="Mar";
                            break;
                        case "04":month="Apr";
                            break;
                        case "05":month="May";
                            break;
                        case "06":month="Jun";
                            break;
                        case "07":month="Jul";
                            break;
                        case "08":month="Aug";
                            break;
                        case "09":month="Sep";
                            break;
                        case "10":month="Oct";
                            break;
                        case "11":month="Nov";
                            break;
                        case "12":month="Dec";
                            break;
                    }
                    String weather=jsonpart.getString("weather");
                    JSONArray weath=new JSONArray(weather);
                    JSONObject jsonpart1=weath.getJSONObject(0);
                    String m=jsonpart1.getString("main");
                    String icon=jsonpart1.getString("id");
                    Forecast forecast=new Forecast(String.format("%.2f", t)+"°C",m,time+", "+d+" "+month,icon);
//                    forecastbyday.add(String.format("%.2f", t)+"°C"+"\n"+d+" "+month);
                    forecastbyday.add(forecast);

                }
                Log.i("forecast",forecastbyday.toString());
                listView.setAdapter(arrayAdapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        listView=findViewById(R.id.listview);
        Intent intent = getIntent();
        String place = intent.getStringExtra("place");

        forecastbyday = new ArrayList<Forecast>();
        arrayAdapter= new ForecastArrayAdapter(this, R.layout.mylist, forecastbyday);

        listView.setAdapter(arrayAdapter);

        downloadtask task=new downloadtask();
        try {
            String result = task.execute("https://api.openweathermap.org/data/2.5/forecast?q="+place+"&appid=e5b925acf4137636af20a4e8d75146fd").get();
        }catch (Exception e){
            e.printStackTrace();

        }
    }
}