package com.example.carouseleffectslider;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;


public class MyPagerAdapter extends PagerAdapter {

    private Context context;
    private int[] listItems;
    private int increment=-1;

    public MyPagerAdapter(Context context, int[] listItems) {
        this.context = context;
        this.listItems = listItems;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cover, null);
        try {

            LinearLayout linMain = (LinearLayout) view.findViewById(R.id.linMain);
            ImageView imageCover = (ImageView) view.findViewById(R.id.imageCover);
            linMain.setTag(position);

            //avoiding ArrayIndexOutOfBoundException

            //In this example We are taking 5 images so when the adapter will reach to its end
            //meand on position 4 we will increase increment value by 1 and assign it to position
            //to avoid ArrayIndexOutOfBoundException because we are returning listItems.lenght+1000
            //from getItemCount() method to make it infinite.

            //Now when increment value will reach to 4 then we are making this 0 for the same
            //above described reason.(ArrayIndexOutOfBoundException)

            //If you want to take more than 5 images then replace 4 with noOfImagesYouAreTaking-1
           if(position>4){
               increment++;
               if(increment>4)
                   increment=0;
              position=increment;
           }

               Glide.with(context)
                       .load(listItems[position])
                       .into(imageCover);


            container.addView(view);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        //returning big value for infinite scrolling
        return listItems.length+1000;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }

}