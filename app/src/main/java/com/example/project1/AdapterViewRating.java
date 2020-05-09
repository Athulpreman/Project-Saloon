package com.example.project1;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterViewRating extends RecyclerView.Adapter<AdapterViewRating.CustomerViewHolder>
{
    private ValueEventListener mCtx;
    private ArrayList<OwnerAdd> list;
    Context context;
    DatabaseReference reference;
    int count;

    AdapterViewRating(Context context, ArrayList<OwnerAdd> itemList)
    {
        this.context = context;
        list = itemList;
    }

    @NonNull
    @Override
    public AdapterViewRating.CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.cardview_service_rating,null);
        return new AdapterViewRating.CustomerViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull  final AdapterViewRating.CustomerViewHolder holder, final int position)
    {
        holder.activity.setText(list.get(position).getActivity());
        holder.modelname.setText(list.get(position).getModelName());
        holder.price.setText(list.get(position).getPrice());
        Picasso.get().load(list.get(position).ModelImg).into(holder.img);
        holder.rating.setText(String.valueOf(list.get(position).getRating()));

        reference= FirebaseDatabase.getInstance().getReference().child("ShopOwners").child(list.get(position).ShopID).child("Activity").child(list.get(position).Activity).child("Rating");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                holder.noOfRating.setText(String.valueOf((int)dataSnapshot.getChildrenCount()));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                Intent intent=new Intent(v.getContext(),OwnerViewEachRating.class);
                intent.putExtra("shopID",list.get(position).getShopID());
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
        TextView activity,noOfRating,price,modelname,rating;
        CircularImageView img;


        public CustomerViewHolder(@NonNull View ownerView)
        {
            super(ownerView);

            activity=(TextView) ownerView.findViewById(R.id.activityName);
            price=(TextView)ownerView.findViewById(R.id.favcvprice);
            modelname=(TextView)ownerView.findViewById(R.id.favcvmodelName);
            rating=(TextView)ownerView.findViewById(R.id.ratingTextNumberFav);
            noOfRating=(TextView)ownerView.findViewById(R.id.ratingNumberOfRating);
            img=(CircularImageView)ownerView.findViewById(R.id.modelimage);

        }

    }
}
