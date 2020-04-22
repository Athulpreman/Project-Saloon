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

public class AdapterBookingHistory extends RecyclerView.Adapter<AdapterBookingHistory.CustomerViewHolder>
{
    private ValueEventListener mCtx;
    private ArrayList<CBookShop> list;
    Context context;
    DatabaseReference reference;

    AdapterBookingHistory(Context context, ArrayList<CBookShop> itemList)
    {

        this.context = context;
        list = itemList;

    }

    @NonNull
    @Override
    public AdapterBookingHistory.CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.cardview_cart,null);
        return new AdapterBookingHistory.CustomerViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull  final AdapterBookingHistory.CustomerViewHolder holder, final int position)
    {
        holder.t1.setText(list.get(position).getShopID());
        holder.t2.setText(list.get(position).getDate());
        holder.t3.setText(list.get(position).getTime());
        String status;
        status=list.get(position).statusBit;
        if (status.equals("0"))
        {
            holder.t4.setText("Upcoming");
        }
        else if ((status.equals("1")))
        {
            holder.t4.setText("Finished");
        }
        else if ((status.equals("2")))
        {
            holder.t4.setText("Declined");
        }
        holder.viewOenerButton.setVisibility(View.INVISIBLE);
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    class CustomerViewHolder extends RecyclerView.ViewHolder
    {
        TextView t1,t2,t3,t4;
        Button viewOenerButton;


        public CustomerViewHolder(@NonNull View ownerView)
        {
            super(ownerView);

            t1=(TextView) ownerView.findViewById(R.id.cvShopName);
            t2=(TextView)ownerView.findViewById(R.id.cvDate);
            t3=(TextView)ownerView.findViewById(R.id.cvTime);
            t4=(TextView)ownerView.findViewById(R.id.cvStatus);
            viewOenerButton=(Button)ownerView.findViewById(R.id.cvViewButton);

        }

    }
}