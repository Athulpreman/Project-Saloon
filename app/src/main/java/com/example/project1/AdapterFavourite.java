package com.example.project1;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterFavourite extends RecyclerView.Adapter<AdapterFavourite.CustomerViewHolder>
{
    private ValueEventListener mCtx;
    private ArrayList<OwnerAdd> list;
    Context context;
    AdapterFavourite(Context context, ArrayList<OwnerAdd> itemList)
    {
        this.context = context;
        list = itemList;
    }

    @NonNull
    @Override
    public AdapterFavourite.CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.cardview_favourite,null);
        return new AdapterFavourite.CustomerViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull  final AdapterFavourite.CustomerViewHolder holder, final int position)
    {
        holder.activity.setText(list.get(position).getActivity());
        holder.modelname.setText(list.get(position).getModelName());
        holder.shop.setText(list.get(position).getShopID());
        holder.price.setText(list.get(position).getPrice());
        Picasso.get().load(list.get(position).ModelImg).into(holder.img);
        holder.rating.setText(String.valueOf(list.get(position).getRating()));

        holder.itemView.setOnClickListener(new View.OnClickListener()
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
        TextView activity,shop,price,modelname,rating;
        CircularImageView img;


        public CustomerViewHolder(@NonNull View ownerView)
        {
            super(ownerView);

            activity=(TextView) ownerView.findViewById(R.id.activityName);
            shop=(TextView)ownerView.findViewById(R.id.favcvshop);
            price=(TextView)ownerView.findViewById(R.id.favcvprice);
            modelname=(TextView)ownerView.findViewById(R.id.favcvmodelName);
            rating=(TextView)ownerView.findViewById(R.id.ratingTextNumberFav);
            img=(CircularImageView)ownerView.findViewById(R.id.modelimage);

        }

    }
}
