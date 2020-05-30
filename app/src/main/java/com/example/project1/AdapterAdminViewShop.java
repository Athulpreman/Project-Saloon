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

public class AdapterAdminViewShop extends RecyclerView.Adapter<AdapterAdminViewShop.CustomerViewHolder>
{
    private ValueEventListener mCtx;
    private ArrayList<Owner> list;
    Context context;
    DatabaseReference reference;

    AdapterAdminViewShop(Context context, ArrayList<Owner> itemList)
    {
        this.context = context;
        list = itemList;
    }

    @NonNull
    @Override
    public AdapterAdminViewShop.CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.cardview_admin_view_shop,null);
        return new AdapterAdminViewShop.CustomerViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull  final AdapterAdminViewShop.CustomerViewHolder holder, final int position)
    {
        holder.t1.setText(list.get(position).getShopName());
        holder.t2.setText(list.get(position).getOwnerName());
        holder.t3.setText(list.get(position).getOwnerMobile());
        holder.t4.setText(list.get(position).getAddress());
        holder.t5.setText(list.get(position).getEmployeeName());
        holder.t6.setText(list.get(position).getEmployeeMobile());
        holder.t7.setText(list.get(position).getShopID());

        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(v.getContext(),AdminViewEditDeleteShop.class);
                intent.putExtra("shopID",list.get(position).getShopID());
                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    class CustomerViewHolder extends RecyclerView.ViewHolder
    {
        TextView t1,t2,t3,t4,t5,t6,t7;

        public CustomerViewHolder(@NonNull View ownerView)
        {
            super(ownerView);

            t1=(TextView) ownerView.findViewById(R.id.showshopName);
            t2=(TextView)ownerView.findViewById(R.id.showOwnerName);
            t3=(TextView)ownerView.findViewById(R.id.showOwnerMobile);
            t4=(TextView)ownerView.findViewById(R.id.showAddress);
            t5=(TextView)ownerView.findViewById(R.id.showEmpName);
            t6=(TextView)ownerView.findViewById(R.id.showEmpMobile);
            t7=(TextView)ownerView.findViewById(R.id.showsshopID);

        }

    }
}
