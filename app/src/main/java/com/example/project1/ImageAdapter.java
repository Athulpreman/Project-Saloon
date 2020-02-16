package com.example.project1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class ImageAdapter extends PagerAdapter
{
    private Context mcontext;
    private LayoutInflater layoutInflater;
    private int[] images=new int[]{R.drawable.aa1,R.drawable.aa2,R.drawable.aa3,R.drawable.aa4};

    ImageAdapter(Context context)
    {
        mcontext=context;
    }

    @Override
    public int getCount()
    {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object)
    {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position)
    {

        layoutInflater=(LayoutInflater)mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.customlayour_for_imageswipe,null);

        ImageView imageView=(ImageView)view.findViewById(R.id.ImageVieW);
        imageView.setImageResource(images[position]);
        ViewPager viewPager=(ViewPager)container;
        viewPager.addView(view);
        return  view;



        /*ImageView imageView=new ImageView(mcontext);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(mImageid[position]);
        container.addView(imageView,0);
        return imageView;*/
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object)
    {
       // container.removeView((ImageView)object);

        ViewPager viewPager=(ViewPager)container;
        View view=(View)object;
        viewPager.removeView(view);

    }

}
