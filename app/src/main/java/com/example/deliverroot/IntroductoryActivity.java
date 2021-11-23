package com.example.deliverroot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.ImageView;


public class IntroductoryActivity extends AppCompatActivity {

    ImageView imageView2, imageView;
    private static final int NUM_PAGES = 3;
    private ViewPager viewPager;
    boolean authenticated;
    private ScreenSlidePagerAdapter pagerAdapter;

    Animation anim;

    SharedPreferences msharedp,msharedp2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int SPLASH_TIME_OUT=5000;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        imageView=findViewById(R.id.imageView);
        imageView2=findViewById(R.id.backbtn);

        viewPager = findViewById(R.id.pager);

        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }


        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    fragment_on_boarding1 tab1 = new fragment_on_boarding1();
                    return tab1;
                case 1:
                    fragment_on_boarding2 tab2 = new fragment_on_boarding2();
                return tab2;
                case 2:
                    fragment_on_boarding3 tab3 = new fragment_on_boarding3();
                    return tab3;
            }
            return null;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}