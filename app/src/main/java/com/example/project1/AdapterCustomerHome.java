package com.example.project1;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterCustomerHome extends RecyclerView.Adapter<AdapterCustomerHome.CustomerViewHolder>
{
    private ValueEventListener mCtx;
    private ArrayList<OwnerAdd> ownerAdds;
    Context context;
    DatabaseReference reference;

    AdapterCustomerHome(Context context, ArrayList<OwnerAdd> itemList)
    {
        this.context = context;
        ownerAdds = itemList;
    }

    @NonNull
    @Override
    public AdapterCustomerHome.CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater=LayoutInflater.from((Context) context);
        View view=layoutInflater.inflate(R.layout.cardview_customer_home,null);
        return new AdapterCustomerHome.CustomerViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final AdapterCustomerHome.CustomerViewHolder holder, final int position)
    {
        holder.t1.setText(ownerAdds.get(position).getPrice());
        holder.t2.setText(ownerAdds.get(position).getModelName());
        Picasso.with(context).load(ownerAdds.get(position).getModelImg()).into(holder.imageView);

    }

    @Override
    public int getItemCount()

    {
        return ownerAdds.size();
    }

    class CustomerViewHolder extends RecyclerView.ViewHolder
    {
        TextView t1,t2;
        ImageView imageView;


        public CustomerViewHolder(@NonNull View ownerView) {
            super(ownerView);
            t1=(TextView) ownerView.findViewById(R.id.OwnerName);
            t2=(TextView)ownerView.findViewById(R.id.ShopNamee);

            imageView=(ImageView) ownerView.findViewById(R.id.cimg1);

        }
    }

}
