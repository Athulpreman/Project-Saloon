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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class AdapterComplaint extends RecyclerView.Adapter<AdapterComplaint.CustomerViewHolder>
{
    private ValueEventListener mCtx;
    private ArrayList<CComplaint> list;
    Context context;
    DatabaseReference reference;

    AdapterComplaint(Context context, ArrayList<CComplaint> itemList)
    {
        this.context = context;
        list = itemList;
    }

    @NonNull
    @Override
    public AdapterComplaint.CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.cardview_complaints,null);
        return new AdapterComplaint.CustomerViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull  final AdapterComplaint.CustomerViewHolder holder, final int position)
    {
        holder.mobile.setText(list.get(position).getPhone());
        holder.subject.setText(list.get(position).getSubject());
        holder.description.setText(list.get(position).getDescription());

        reference= FirebaseDatabase.getInstance().getReference().child("Customer");
        Query query=reference.orderByChild("mobileNum").equalTo(list.get(position).phone);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for (DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    Customer customer=snapshot.getValue(Customer.class);
                    holder.name.setText(customer.name);
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
        TextView name,mobile,subject,description;

        public CustomerViewHolder(@NonNull View ownerView)
        {
            super(ownerView);

            name=(TextView) ownerView.findViewById(R.id.ovcsName);
            mobile=(TextView)ownerView.findViewById(R.id.ovcsMobile);
            subject=(TextView)ownerView.findViewById(R.id.ovcsSubject);
            description=(TextView)ownerView.findViewById(R.id.ovcsDescription);
        }
    }
}
