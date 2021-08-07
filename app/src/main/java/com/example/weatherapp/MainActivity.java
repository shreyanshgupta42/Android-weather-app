package com.example.weatherapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    TextView textView,textView2,textView3,textView4,textView5,textView6,textView7,textView9,textView10;
    ImageView imageView1,imageView2,imageView3,imageView4,imageView5,imageView6,imageView7,imageView9;

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

            Log log = null;
            log.i("show weather",s);

            try {
                JSONObject jsonObject=new JSONObject(s);
                String weatherinfo=jsonObject.getString("weather");
                JSONArray arr=new JSONArray(weatherinfo);
                String message="";
                for(int i=0;i<arr.length();i++){
                    JSONObject jsonpart=arr.getJSONObject(i);
                    String id=jsonpart.getString("id");
                    String main=jsonpart.getString("main");
                    String description=jsonpart.getString("description");

                    if(!main.equals("") && !description.equals("")){
                        message=main+"\r\n"+description+"\r\n";
                        textView9.setText(main);
                        switch (id.charAt(0)){
                            case '2':
                                imageView9.setImageDrawable(getDrawable(R.drawable.thunder));
                                break;
                            case '3':
                                imageView9.setImageDrawable(getDrawable(R.drawable.drizzle));
                                break;
                            case '5':
                                imageView9.setImageDrawable(getDrawable(R.drawable.rain));
                                break;
                            case '6':
                                imageView9.setImageDrawable(getDrawable(R.drawable.snow));
                                break;
                            case '7':
                                imageView9.setImageDrawable(getDrawable(R.drawable.mist));
                                break;
                            case '8':
                                imageView9.setImageDrawable(getDrawable(R.drawable.clear));
                                break;
                        }
                    }

                    log.i("id",jsonpart.getString("id"));
                    log.i("main",jsonpart.getString("main"));
                    log.i("des",jsonpart.getString("description"));
                }
                //
                String weatherinfo2=jsonObject.getString("main");
                JSONObject jsonObject2=new JSONObject(weatherinfo2);
                String temp=jsonObject2.getString("temp");
                String pres=jsonObject2.getString("pressure");
                String humi=jsonObject2.getString("humidity");
                if(!temp.equals("") && !pres.equals("")){
//                    message+="Temperature: "+temp+"K  "+"\r\n"+"Pressure: "+pres+"hPa"+"\r\n";
                    textView.setText("Temperature: "+temp+"K  "+"\r\n");
                    textView2.setText("Pressure: "+pres+"hPa"+"\r\n");
                    imageView2.setVisibility(View.VISIBLE);
                    imageView1.setVisibility(View.VISIBLE);
                }
                if(!humi.equals("")){
//                    message+="Humidity: "+humi+"% "+"\r\n";
                    textView3.setText("Humidity: "+humi+"% "+"\r\n");
                    imageView3.setVisibility(View.VISIBLE);
                }
                String visi=jsonObject.getString("visibility");
                if(!visi.equals("")){
//                    message+="Visibility: "+visi+"m "+"\r\n";
                    textView4.setText("Visibility: "+visi+"m "+"\r\n");
                    imageView4.setVisibility(View.VISIBLE);
                }

                String weatherinfo4=jsonObject.getString("wind");
                JSONObject jsonObject4=new JSONObject(weatherinfo4);
                String speed=jsonObject4.getString("speed");
                String deg=jsonObject4.getString("deg");
                if(!speed.equals("") && !deg.equals("")){
//                    message+="wind speed: "+speed+"m/s at "+deg+" degree"+"\r\n";
                    textView5.setText("wind speed: "+speed+"m/s"+"\r\n");
                    imageView5.setVisibility(View.VISIBLE);
                }

                String weatherinfo3=jsonObject.getString("sys");
                JSONObject jsonObject3=new JSONObject(weatherinfo3);
                String ss=jsonObject3.getString("sunset");
                String sr=jsonObject3.getString("sunrise");
                if(!ss.equals("") && !sr.equals("")) {
                    long sunset=Long.parseLong(ss);
                    long sunrise=Long.parseLong(sr);
                    long unixTimestamp = 1427607706;
                    long jsunset = sunset * 1000L;
                    long jsunrise = sunrise * 1000L;
                    Date dsunset = new Date(jsunset);
                    Date dsunrise = new Date(jsunrise);
                    String rsunset = new SimpleDateFormat("hh:mm").format(dsunset);
                    String rsunrise = new SimpleDateFormat("hh:mm").format(dsunrise);
//                    message += "Sunset: " + rsunset + "\r\n" + "Sunrise: " + rsunrise +"\r\n";
                    textView7.setText("Sunset: " + rsunset + "\r\n");
                    textView6.setText("Sunrise: " + rsunrise +"\r\n");
                    imageView6.setVisibility(View.VISIBLE);
                    imageView7.setVisibility(View.VISIBLE);
                }
//                if(!message.equals("")){
//                    textView.setText(message);
//                }
                String timezone=jsonObject.getString("timezone");
                if(!visi.equals("")){
//                    message+="Visibility: "+visi+"m "+"\r\n";
                    textView4.setText("Visibility: "+visi+"m "+"\r\n");
                    imageView4.setVisibility(View.VISIBLE);
                }
                Date date=new Date();
                String day=date.toString().substring(0,3);
                int hr=Integer.parseInt(date.toString().substring(11,13))-5+((Integer.parseInt(timezone)/60)/60);
                int min=Integer.parseInt(date.toString().substring(14,16))-30+((Integer.parseInt(timezone)/60)%60);
                if(min<0){
                    hr=hr-1;
                    min=60+min;
                }
                if(hr<0){
                    hr=24+hr;
                    switch(day){
                        case "Mon":day="Sun";break;
                        case "Tue":day="Mon";break;
                        case "Wed":day="Tue";break;
                        case "Thr":day="Wed";break;
                        case "Fri":day="Thr";break;
                        case "Sat":day="Fri";break;
                        case "Sun":day="Sat";break;
                    }
                }
                int a = 1;
                DecimalFormat formatter = new DecimalFormat("00");
                String hour = formatter.format(hr);
                String minute = formatter.format(min);
                textView10.setText(day+" "+hour+":"+minute);

                Log.d("date",date.toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void weather(View view){


        InputMethodManager mgr=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(editText.getWindowToken(),0);

        downloadtask task=new downloadtask();
        try {
            String result = task.execute("https://api.openweathermap.org/data/2.5/weather?q="+editText.getText().toString()+"&appid=e5b925acf4137636af20a4e8d75146fd").get();

            //String result = task.execute("https://openweathermap.org/data/2.5/weather?q="+editText.getText().toString()+"&appid=e5b925acf4137636af20a4e8d75146fd").get();
                               //&appid=b6907d289e10d714a6e88b30761fae22       //previous api key   api.openweathermap.org/data/2.5/weather?q={city name}&appid={API key}
        }catch (Exception e){
            e.printStackTrace();

        }


    }

    public void forecast(View view){
        Intent intent=new Intent(getApplicationContext(),ForecastActivity.class);
        intent.putExtra("place", editText.getText().toString());
        startActivity(intent);
    }

    public void onclick(View view){
        editText.setText("");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText=(EditText)findViewById(R.id.editText);
        textView=(TextView)findViewById(R.id.textView1);
        textView2=(TextView)findViewById(R.id.textview2);
        textView3=(TextView)findViewById(R.id.textview3);
        textView4=(TextView)findViewById(R.id.textView4);
        textView5=(TextView)findViewById(R.id.textView5);
        textView6=(TextView)findViewById(R.id.textView6);
        textView7=(TextView)findViewById(R.id.textView7);
        textView9=(TextView)findViewById(R.id.textView9);
        textView10=(TextView)findViewById(R.id.textView10);
        imageView1=(ImageView)findViewById(R.id.imageView1);
        imageView2=(ImageView)findViewById(R.id.imageView2);
        imageView3=(ImageView)findViewById(R.id.imageView3);
        imageView4=(ImageView)findViewById(R.id.imageView5);
        imageView5=(ImageView)findViewById(R.id.imageView6);
        imageView6=(ImageView)findViewById(R.id.imageView7);
        imageView7=(ImageView)findViewById(R.id.imageView8);
        imageView9=(ImageView)findViewById(R.id.imageView9);
    }
}
