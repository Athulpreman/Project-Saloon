package com.example.project1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class AdapterCustomerHome extends RecyclerView.Adapter<AdapterCustomerHome.CustomerViewHolder>
{
    private ValueEventListener mCtx;
    private ArrayList<OwnerAdd> list;
    Context context;
    DatabaseReference reference;

    AdapterCustomerHome(Context context, ArrayList<OwnerAdd> itemList)
    {

        this.context = context;
        list = itemList;

    }

    @NonNull
    @Override
    public AdapterCustomerHome.CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.cardview_customer_home,null);
        return new AdapterCustomerHome.CustomerViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull  final CustomerViewHolder holder, final int position)
    {
        holder.t1.setText(list.get(position).getPrice());
        holder.t2.setText(list.get(position).getModelName());
        Picasso.get().load(list.get(position).getModelImg()).into(holder.imageView);
        holder.cardView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                Intent intent=new Intent(v.getContext(),Booking_shop_Customer_1st.class);
                intent.putExtra("shopID",list.get(position).getShopID());
                intent.putExtra("price",list.get(position).getPrice());
                intent.putExtra("activity",list.get(position).getActivity());
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
        TextView t1,t2;
        ImageView imageView;
        CardView cardView;


        public CustomerViewHolder(@NonNull View ownerView)
        {
            super(ownerView);

            t1=(TextView) ownerView.findViewById(R.id.price);
            t2=(TextView)ownerView.findViewById(R.id.StyleName);
            imageView=(ImageView) ownerView.findViewById(R.id.Imgg);
            cardView=(CardView)ownerView.findViewById(R.id.CustomerCard);

        }

    }

}
