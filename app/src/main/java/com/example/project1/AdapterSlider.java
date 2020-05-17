package com.example.project1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterSlider extends SliderViewAdapter<AdapterSlider.SliderAdapterVH>
{
    private Context context;
    private ArrayList<Owner> mSliderItems = new ArrayList<>();

    public AdapterSlider(Context context)
    {
        this.context = context;
    }

    public void renewItems(ArrayList<Owner> sliderItems)
    {
        this.mSliderItems = sliderItems;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        this.mSliderItems.remove(position);
        notifyDataSetChanged();
    }

    public void addItem(Owner owner) {
        this.mSliderItems.add(owner);
        notifyDataSetChanged();
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent)
    {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_slider, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {

        Owner owner = mSliderItems.get(position);

        viewHolder.textViewDescription.setText(owner.getShopName());
        viewHolder.textViewDescription.setTextSize(16);
        viewHolder.textViewDescription.setTextColor(Color.WHITE);

        Glide.with(viewHolder.itemView)
                .load(owner.getImage1())
                .fitCenter()
                .into(viewHolder.imageViewBackground);


        //Picasso.get().load(owner.getImage1()).into(viewHolder.imageViewBackground);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(context,Booking_shop_Customer_1st.class);
                intent.putExtra("shopID",mSliderItems.get(position).ShopID);
                intent.putExtra("price","");
                intent.putExtra("activity","");
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return mSliderItems.size();
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder
    {

        View itemView;
        ImageView imageViewBackground;
        ImageView imageGifContainer;
        TextView textViewDescription;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider);
            //imageGifContainer = itemView.findViewById(R.id.iv_gif_container);
            textViewDescription = itemView.findViewById(R.id.tv_auto_image_slider);
            this.itemView = itemView;
        }
    }
}
