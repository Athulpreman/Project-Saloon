package com.example.project1;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

public class AdapterViewEachRating extends RecyclerView.Adapter<AdapterViewEachRating.CustomerViewHolder>
{
    private ValueEventListener mCtx;
    private ArrayList<CRate> list;
    Context context;
    DatabaseReference reference;
    int rating;

    AdapterViewEachRating(Context context, ArrayList<CRate> itemList)
    {
        this.context = context;
        list = itemList;
    }

    @NonNull
    @Override
    public AdapterViewEachRating.CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.cardview_service_rating_each_service,null);
        return new AdapterViewEachRating.CustomerViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull  final AdapterViewEachRating.CustomerViewHolder holder, final int position)
    {
        holder.date.setText(list.get(position).getDate());
        holder.subject.setText(list.get(position).getSubject());
        holder.feedback.setText(list.get(position).getFeedback());
        holder.mobile.setText(String.valueOf(list.get(position).getMobile()));

        rating=list.get(position).reating;
        if (rating==1)
        {
            holder.i1.setImageResource(R.drawable.star_gold_full);
        }
        else if (rating==2)
        {
            holder.i1.setImageResource(R.drawable.star_gold_full);
            holder.i2.setImageResource(R.drawable.star_gold_full);
        }
        else if (rating==3)
        {
            holder.i1.setImageResource(R.drawable.star_gold_full);
            holder.i2.setImageResource(R.drawable.star_gold_full);
            holder.i3.setImageResource(R.drawable.star_gold_full);
        }
        else if (rating==4)
        {
            holder.i1.setImageResource(R.drawable.star_gold_full);
            holder.i2.setImageResource(R.drawable.star_gold_full);
            holder.i3.setImageResource(R.drawable.star_gold_full);
            holder.i4.setImageResource(R.drawable.star_gold_full);
        }
        else
        {
            holder.i1.setImageResource(R.drawable.star_gold_full);
            holder.i2.setImageResource(R.drawable.star_gold_full);
            holder.i3.setImageResource(R.drawable.star_gold_full);
            holder.i4.setImageResource(R.drawable.star_gold_full);
            holder.i5.setImageResource(R.drawable.star_gold_full);
        }

        reference= FirebaseDatabase.getInstance().getReference().child("Customer");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for (DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    Customer customer=snapshot.getValue(Customer.class);
                    if (customer.mobileNum.equals(list.get(position).mobile))
                    {
                        Picasso.get().load(customer.profilePic).into(holder.img);
                        holder.customerName.setText(customer.name);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
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
        TextView customerName,mobile,date,subject,feedback;
        CircularImageView img;
        ImageView i1,i2,i3,i4,i5;


        public CustomerViewHolder(@NonNull View ownerView)
        {
            super(ownerView);

            customerName=(TextView) ownerView.findViewById(R.id.activityName);
            mobile=(TextView)ownerView.findViewById(R.id.favcvprice);
            date=(TextView)ownerView.findViewById(R.id.favcvdate);
            subject=(TextView)ownerView.findViewById(R.id.favcvmodelName);
            feedback=(TextView)ownerView.findViewById(R.id.favcvfeedback);
            img=(CircularImageView)ownerView.findViewById(R.id.modelimage);
            i1=(ImageView) ownerView.findViewById(R.id.imgo1);
            i2=(ImageView) ownerView.findViewById(R.id.imgo2);
            i3=(ImageView) ownerView.findViewById(R.id.imgo3);
            i4=(ImageView) ownerView.findViewById(R.id.imgo4);
            i5=(ImageView) ownerView.findViewById(R.id.imgo5);

        }

    }
}
