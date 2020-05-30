package com.example.project1;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterManageService extends RecyclerView.Adapter<AdapterManageService.CustomerViewHolder>
{
    private ValueEventListener mCtx;
    private ArrayList<OwnerAdd> list;
    Context context;
    DatabaseReference ref;

    AdapterManageService(Context context, ArrayList<OwnerAdd> itemList)
    {
        this.context = context;
        list = itemList;
    }

    @NonNull
    @Override
    public AdapterManageService.CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.cardview_manage_services,null);
        return new AdapterManageService.CustomerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  final AdapterManageService.CustomerViewHolder holder, final int position)
    {
        holder.activity.setText(list.get(position).getActivity());
        holder.modelname.setText(list.get(position).getModelName());
        holder.shop.setText(list.get(position).getShopID());
        holder.price.setText(list.get(position).getPrice());
        Picasso.get().load(list.get(position).ModelImg).into(holder.img);
        holder.rating.setText(String.valueOf(list.get(position).getRating()));
        holder.delete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ref= FirebaseDatabase.getInstance().getReference().child("ShopOwners").child(list.get(position).ShopID).child("Activity");
                Query query=ref.orderByChild("activity").equalTo(list.get(position).getActivity());
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        for (DataSnapshot snapshot:dataSnapshot.getChildren())
                        {
                            snapshot.getRef().removeValue().addOnSuccessListener(new OnSuccessListener<Void>()
                            {
                                @Override
                                public void onSuccess(Void aVoid)
                                {
                                    CToast cToast=new CToast();
                                    cToast.toast(context,"Deleted The Service",1);

                                    list.remove(position);
                                    notifyItemRemoved(position);
                                    notifyItemRangeChanged(position, list.size());
                                    holder.itemView.setVisibility(View.GONE);
                                }
                            });
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError)
                    {
                    }
                });
            }
        });
        holder.edit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(context, list.get(position).Activity, Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(context,OwnerEditService.class);
                intent.putExtra("Activity",list.get(position).getActivity());
                context.startActivity(intent);
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
        Button edit,delete;


        public CustomerViewHolder(@NonNull View ownerView)
        {
            super(ownerView);

            activity=(TextView) ownerView.findViewById(R.id.activityName);
            shop=(TextView)ownerView.findViewById(R.id.favcvshop);
            price=(TextView)ownerView.findViewById(R.id.favcvprice);
            modelname=(TextView)ownerView.findViewById(R.id.favcvmodelName);
            rating=(TextView)ownerView.findViewById(R.id.ratingTextNumberFav);
            img=(CircularImageView)ownerView.findViewById(R.id.modelimage);
            edit=(Button)ownerView.findViewById(R.id.manageServiceEditBitton);
            delete=(Button)ownerView.findViewById(R.id.manageServiceDeleteBitton);

        }

    }
}
