package com.example.project1;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class OwnerAdapter extends RecyclerView.Adapter<OwnerAdapter.OwnerViewHolder>
{
    private ValueEventListener mCtx;
    private ArrayList<Owner> owners;
    Context context;
    DatabaseReference reference;

    OwnerAdapter(Context context, ArrayList<Owner> itemList)
    {
        this.context = context;
        owners = itemList;
    }

    @NonNull
    @Override
    public OwnerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater=LayoutInflater.from((Context) context);
        View view=layoutInflater.inflate(R.layout.admin_accept_cardview,null);
        return new OwnerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final OwnerViewHolder holder, final int position)
    {
        holder.t1.setText(owners.get(position).getOwnerName());
        holder.t2.setText(owners.get(position).getShopName());
        holder.t3.setText(owners.get(position).getShopID());
        holder.t4.setText(owners.get(position).getOwnerMobile());
        holder.t5.setText(owners.get(position).getAddress());
        Picasso.get().load(owners.get(position).getImage1()).into(holder.imageView);
        Picasso.get().load(owners.get(position).getImage3()).into(holder.imageView2);

       // reference= FirebaseDatabase.getInstance().getReference().child("Shop_Owners");



        holder.viewOenerButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(v.getContext(),OwnerViewDetails_From_Cardview.class);
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
        TextView t1,t2,t3,t4,t5;
        ImageView imageView,imageView2;
        Button viewOenerButton;

        public OwnerViewHolder(@NonNull View ownerView) {
            super(ownerView);
            t1=(TextView) ownerView.findViewById(R.id.OwnerName);
            t2=(TextView)ownerView.findViewById(R.id.ShopNamee);
            t3=(TextView)ownerView.findViewById(R.id.ShopIDe);
            t4=(TextView)ownerView.findViewById(R.id.Owerno);
            t5=(TextView)ownerView.findViewById(R.id.Addresse);
            imageView=(ImageView) ownerView.findViewById(R.id.cimg1);
            imageView2=(ImageView) ownerView.findViewById(R.id.cimg2);
            viewOenerButton=(Button)ownerView.findViewById(R.id.viewOwnerDetails);

        }
    }
}
