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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AdapterBookingHistory extends RecyclerView.Adapter<AdapterBookingHistory.CustomerViewHolder>
{
    private ValueEventListener mCtx;
    private ArrayList<CBookShop> list;
    Context context;
    DatabaseReference reference;
    String currentDate,doneDate;
    float days;

    AdapterBookingHistory(Context context, ArrayList<CBookShop> itemList)
    {

        this.context = context;
        list = itemList;

    }

    @NonNull
    @Override
    public AdapterBookingHistory.CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.cardview_booking_history,null);
        return new AdapterBookingHistory.CustomerViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull  final AdapterBookingHistory.CustomerViewHolder holder, final int position)
    {
        holder.t1.setText(list.get(position).getShopID());
        holder.t2.setText(list.get(position).getDate());
        holder.t3.setText(list.get(position).getTime());
        holder.price.setText(list.get(position).getPrice());
        holder.t4.setText("Finished");

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy,MM,dd");
        currentDate = df.format(c.getTime());

        currentDate=parseDateToddMMyyyy(currentDate);
        doneDate=parseDateToddMMyyyy(list.get(position).Date);
        SimpleDateFormat myFormat = new SimpleDateFormat("dd,MMM,yyyy");

        try
        {
            Date dateBefore = myFormat.parse(doneDate);
            Date dateAfter = myFormat.parse(currentDate);
            long difference = dateAfter.getTime() - dateBefore.getTime();
            days = (difference / (1000*60*60*24));
        }
        catch (Exception exception)
        {
            Toast.makeText(context, "Unable to find interest\n Try again in a minute", Toast.LENGTH_LONG).show();
        }

        if (days<8)
        {
            holder.viewOenerButton.setVisibility(View.VISIBLE);
        }

        holder.viewOenerButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(context.getApplicationContext(),CustomerRateShop.class);
                intent.putExtra("phone",list.get(position).getCustomerMob());
                intent.putExtra("qrString",list.get(position).getQrCode());
                intent.putExtra("shopID",list.get(position).getShopID());
                intent.putExtra("activity",list.get(position).getActivity());
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
        TextView t1,t2,t3,t4,price;
        Button viewOenerButton;


        public CustomerViewHolder(@NonNull View ownerView)
        {
            super(ownerView);

            t1=(TextView) ownerView.findViewById(R.id.cvShopName);
            t2=(TextView)ownerView.findViewById(R.id.cvDate);
            t3=(TextView)ownerView.findViewById(R.id.cvTime);
            t4=(TextView)ownerView.findViewById(R.id.cvStatus);
            price=(TextView)ownerView.findViewById(R.id.cvPrice);
            viewOenerButton=(Button)ownerView.findViewById(R.id.cvViewButton);

        }

    }
    public String parseDateToddMMyyyy(String time) {
        String inputPattern = "yyyy,MM,dd";
        String outputPattern = "dd,MMM,yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }
}
