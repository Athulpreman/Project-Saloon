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

public class AdapterOwnerViewBookings extends RecyclerView.Adapter<AdapterOwnerViewBookings.CustomerViewHolder>
{
    private ValueEventListener mCtx;
    private ArrayList<CBookShop> list;
    Context context;

    AdapterOwnerViewBookings(Context context, ArrayList<CBookShop> itemList)
    {

        this.context = context;
        list = itemList;

    }

    @NonNull
    @Override
    public AdapterOwnerViewBookings.CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.cardview_owner_viewbookingdetails,null);
        return new AdapterOwnerViewBookings.CustomerViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull  final AdapterOwnerViewBookings.CustomerViewHolder holder, final int position)
    {
        holder.t1.setText(list.get(position).getCustomerName());
        holder.t2.setText(list.get(position).getDate());
        holder.t3.setText(list.get(position).getTime());
        holder.t4.setText(list.get(position).getPrice());
        holder.t5.setText(list.get(position).getActivity());
        holder.t6.setText(list.get(position).getCustomerMob());
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    class CustomerViewHolder extends RecyclerView.ViewHolder
    {
        TextView t1,t2,t3,t4,t5,t6;


        public CustomerViewHolder(@NonNull View ownerView)
        {
            super(ownerView);

            t1=(TextView) ownerView.findViewById(R.id.ovbcName);
            t2=(TextView)ownerView.findViewById(R.id.ovbcDate);
            t3=(TextView)ownerView.findViewById(R.id.ovbcTime);
            t4=(TextView)ownerView.findViewById(R.id.ovbcPrice);
            t5=(TextView)ownerView.findViewById(R.id.ovbcService);
            t6=(TextView)ownerView.findViewById(R.id.ovbcMobile);

        }

    }
}
