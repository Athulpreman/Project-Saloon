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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

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
        reference=FirebaseDatabase.getInstance().getReference().child("Customer").child(holder.MobNoo).child("Favourite");
        Query query1=reference.orderByChild("shopID").equalTo(list.get(position).getShopID());
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                Log.d("aaaaa","before for");
                for (DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    Log.d("aaaaa","for");
                    CFav cFav=new CFav();
                    cFav=snapshot.getValue(CFav.class);
                    if (cFav.activity.equals(list.get(position).Activity))
                    {
                        holder.fav.setVisibility(View.VISIBLE);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        holder.t1.setText(list.get(position).getPrice());
        holder.t2.setText(list.get(position).getModelName());
        if (list.get(position).rating==0.0)
        {
            holder.rating.setText("Unrated");
            holder.rating.setTextSize(1,15);
        }
        else
        {
            holder.rating.setText(String.format("%.1f", list.get(position).getRating()));
        }
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
        TextView t1,t2,rating;
        ImageView imageView,fav;
        CardView cardView;
        String MobNoo;


        public CustomerViewHolder(@NonNull View ownerView)
        {
            super(ownerView);

            t1=(TextView) ownerView.findViewById(R.id.price);
            t2=(TextView)ownerView.findViewById(R.id.StyleName);
            rating=(TextView)ownerView.findViewById(R.id.ratingTextNumber);
            imageView=(ImageView) ownerView.findViewById(R.id.Imgg);
            fav=(ImageView) ownerView.findViewById(R.id.favouriteimage);
            cardView=(CardView)ownerView.findViewById(R.id.CustomerCard);

            SharedPreferences sharedPreferences=context.getSharedPreferences("UserLogin",MODE_PRIVATE);
            MobNoo=sharedPreferences.getString("MobNo",null);

        }

    }

}
