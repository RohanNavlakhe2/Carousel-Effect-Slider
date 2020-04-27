package com.example.carouseleffectslider;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.Timer;
import java.util.TimerTask;

//Before starting add below dependencies to your app level gradle
        //implementation 'com.google.android.material:material:1.1.0' (For Designing purpose)
        //implementation 'com.github.bumptech.glide:glide:4.11.0'    (For image Loading)

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    //current page of the viewpager,initially 1
    int currentPage = 1;
    //delay in milliseconds before task is to be executed
    final long DELAY_MS = 0;
    // time in milliseconds between successive task executions.
    final long PERIOD_MS = 1500;

    //for dot indicator
    LinearLayout linearLayout;
    int increment=0;

    //slider images
    int[] listItems={R.drawable.banner2, R.drawable.banner3, R.drawable.banner1,R.drawable.banner5,R.drawable.banner4};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager=findViewById(R.id.viewpagerTop);

        settingUpViewPagerTop();
    }

    private void settingUpViewPagerTop(){
        viewPager.setClipChildren(false);
        viewPager.setPageMargin(getResources().getDimensionPixelOffset(R.dimen.pager_margin));
        viewPager.setOffscreenPageLimit(3);
        // Set transformer
        viewPager.setPageTransformer(false, new CarouselEffectTransformer(this));

        setupViewPager();
    }

    private void setupViewPager() {
        // Set Top ViewPager Adapter
        MyPagerAdapter adapter = new MyPagerAdapter(this, listItems);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(currentPage);

        /*After setting the adapter use the timer */
        /*This is for moving viewpager in a particular time*/
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                //listItems.length+1000 same as value returned by getItemCount() inside MyPagerAdapter
                int NUM_PAGES=listItems.length+1000;

                if (currentPage == NUM_PAGES) {
                    currentPage = 1;
                 }

                viewPager.setCurrentItem(currentPage++, true);

            }
        };

        Timer timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                //this line is for continue scrolling from where user stopped scrolling the slider
                currentPage=position;
                // moving indicator
                movingIndicator(position);

            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //this line is for continue scrolling where the user left
                //currentPage=position;

            }

            @Override
            public void onPageScrollStateChanged(int state) {


            }
        });
    }

    private void movingIndicator(int position){
        increment=position;
        //replace 4 with your noOfImages-1
        if(increment>4)
            //replace 5 with your noOfImages
            increment=increment%5;
        drawPageSelectionIndicators(increment);
    }

    //making dots selected according given position
    private void drawPageSelectionIndicators(int mPosition){
        if(linearLayout!=null) {
            linearLayout.removeAllViews();
        }
        linearLayout=findViewById(R.id.viewPagerCountDots);
        //for dot Indicator
        //No of tabs or images
           //replace 5 with your required no of dots


        int dotsCount = 5;
        ImageView[] dots = new ImageView[dotsCount];
        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(this);
            if(i==mPosition)
                /*Handling Illegal state exception which may come when coming to MainActivity
                 from somewhere
                * not on first time loading.*/
                try {
                    dots[i].setImageDrawable(getResources().getDrawable(R.drawable.dots_selected));
                }catch (IllegalStateException e){}
            else
                try {
                    dots[i].setImageDrawable(getResources().getDrawable(R.drawable.dots_unselected));
                }catch (IllegalStateException e){}

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(8, 0, 8, 0);
            linearLayout.addView(dots[i], params);
        }
    }




}
