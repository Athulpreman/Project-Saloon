package com.example.project1;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter_Search_Place extends RecyclerView.Adapter<Adapter_Search_Place.OwnerViewHolder>
{
    private ValueEventListener mCtx;
    private ArrayList<Owner> owners;
    Context context;

    Adapter_Search_Place(Context context, ArrayList<Owner> itemList)
    {
        this.context = context;
        owners = itemList;
    }

    @NonNull
    @Override
    public Adapter_Search_Place.OwnerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater=LayoutInflater.from((Context) context);
        View view=layoutInflater.inflate(R.layout.cardview_search_place,null);
        return new Adapter_Search_Place.OwnerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Adapter_Search_Place.OwnerViewHolder holder, final int position)
    {
        holder.t1.setText(owners.get(position).getOwnerName());
        holder.t2.setText(owners.get(position).getShopName());
        Picasso.get().load(owners.get(position).getImage1()).into(holder.imageView);
        Picasso.get().load(owners.get(position).getImage3()).into(holder.imageView2);

        holder.viewOenerButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(v.getContext(),Booking_shop_Customer_1st.class);
                intent.putExtra("shopID",owners.get(position).getShopID());
                v.getContext().startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount()

    {
        return owners.size();
    }

    class OwnerViewHolder extends RecyclerView.ViewHolder
    {
        TextView t1,t2;
        ImageView imageView,imageView2;
        Button viewOenerButton;

        public OwnerViewHolder(@NonNull View ownerView) {
            super(ownerView);
            t1=(TextView) ownerView.findViewById(R.id.cvShopName);
            t2=(TextView)ownerView.findViewById(R.id.cvMobNo);
            imageView=(ImageView) ownerView.findViewById(R.id.imgg1);
            imageView2=(ImageView) ownerView.findViewById(R.id.imgg2);
            viewOenerButton=(Button)ownerView.findViewById(R.id.cvViewButton);

        }
    }
}
