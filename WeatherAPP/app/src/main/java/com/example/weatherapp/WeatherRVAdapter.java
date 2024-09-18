package com.example.weatherapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class WeatherRVAdapter extends RecyclerView.Adapter<WeatherRVAdapter.ViewHolder> {
    private Context context;
    private ArrayList<WeatherRVModal> weatherRVModelArrayList;

    public WeatherRVAdapter(Context context, ArrayList<WeatherRVModal> weatherRVModelArrayList) {
        this.context = context;
        this.weatherRVModelArrayList = weatherRVModelArrayList;
    }

    @NonNull
    @Override
    public WeatherRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.weather_rv_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WeatherRVModal modal=weatherRVModelArrayList.get(position);
        holder.temperatureTV.setText(modal.getTemperature()+"°F");
        SimpleDateFormat input=new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
        SimpleDateFormat output=new SimpleDateFormat("MM/dd");
        try {
            Date t=input.parse(modal.getTime());
            holder.timeTV.setText(output.format(t));
            if(position==0){
                holder.timeTV.setText("Today");
            }
        }catch (ParseException e){
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return weatherRVModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView temperatureTV,timeTV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            temperatureTV=itemView.findViewById(R.id.idTVTemperature);
            timeTV=itemView.findViewById(R.id.idTVTime);
        }
    }
}
