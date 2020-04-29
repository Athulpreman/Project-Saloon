package com.example.project1;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdapterUpcomingBookings extends RecyclerView.Adapter<AdapterUpcomingBookings.CustomerViewHolder>
{
    private ValueEventListener mCtx;
    private ArrayList<CBookShop> list;
    Context context;
    DatabaseReference reference;

    AdapterUpcomingBookings(Context context, ArrayList<CBookShop> itemList)
    {

        this.context = context;
        list = itemList;

    }

    @NonNull
    @Override
    public AdapterUpcomingBookings.CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.cardview_upcoming_booking_owner,null);
        return new AdapterUpcomingBookings.CustomerViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull  final AdapterUpcomingBookings.CustomerViewHolder holder, final int position)
    {
        holder.t1.setText(list.get(position).getCustomerName());
        holder.t2.setText(list.get(position).getCustomerMob());
        holder.t3.setText(list.get(position).getTime());
        holder.t5.setText(list.get(position).getPrice());
        holder.t4.setText(list.get(position).getActivity());
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    class CustomerViewHolder extends RecyclerView.ViewHolder
    {
        TextView t1,t2,t3,t4,t5;

        public CustomerViewHolder(@NonNull View ownerView)
        {
            super(ownerView);

            t1=(TextView) ownerView.findViewById(R.id.ubcName);
            t2=(TextView)ownerView.findViewById(R.id.ubcMobile);
            t3=(TextView)ownerView.findViewById(R.id.ubcTime);
            t4=(TextView)ownerView.findViewById(R.id.ubcService);
            t5=(TextView)ownerView.findViewById(R.id.ubcPrice);

        }

    }
}
