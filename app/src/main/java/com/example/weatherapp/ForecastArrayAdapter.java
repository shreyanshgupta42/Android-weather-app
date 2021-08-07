package com.example.weatherapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;

import java.util.ArrayList;

public class ForecastArrayAdapter extends ArrayAdapter<Forecast> {

    private Context mcontext;
    int mresource;
    ArrayList<Forecast> forecasts;

    public ForecastArrayAdapter(Context context, int resource, ArrayList<Forecast> objects) {
        super(context, resource, objects);
        mcontext=context;
        mresource=resource;
        forecasts=objects;
        Log.i("got","started here");
    }

    @NonNull
    @Override
    public View getView(int position,View convertView, ViewGroup parent) {

//        String temp=getItem(position).getTemp();
//        String main=getItem(position).getMain();
//        String time=getItem(position).getTime();

//        Forecast forecast=new Forecast(temp,main,time);
        Log.i("got","upto here");
        LayoutInflater inflater=LayoutInflater.from(mcontext);
        convertView=inflater.inflate(mresource,parent,false);

        TextView tvTemp=convertView.findViewById(R.id.textview1);
        TextView tvmain=convertView.findViewById(R.id.textview2);
        TextView tvDate=convertView.findViewById(R.id.textview3);
        ImageView tvicon=convertView.findViewById(R.id.imageView10);

        Forecast forecast=forecasts.get(position);

        tvTemp.setText(forecast.getMain());
        tvmain.setText(forecast.getTemp());
        tvDate.setText(forecast.getTime());

        switch (forecast.getIcon().charAt(0)){
            case '2':
                tvicon.setImageResource(R.drawable.thunder);
                break;
            case '3':
                tvicon.setImageResource(R.drawable.drizzle);
                break;
            case '5':
                tvicon.setImageResource(R.drawable.rain);
                break;
            case '6':
                tvicon.setImageResource(R.drawable.snow);
                break;
            case '7':
                tvicon.setImageResource(R.drawable.mist);
                break;
            case '8':
                tvicon.setImageResource(R.drawable.clear);
                break;
        }

        Log.i("got","upto here");
        return convertView;
    }
}
