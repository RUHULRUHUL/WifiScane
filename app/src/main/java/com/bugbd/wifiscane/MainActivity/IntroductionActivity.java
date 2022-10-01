package com.bugbd.wifiscane.MainActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import com.bugbd.wifiscane.R;
import com.bugbd.wifiscane.databinding.ActivityIntroductionBinding;

public class IntroductionActivity extends AppCompatActivity {

    private ActivityIntroductionBinding binding;
    private StatusSharedPreferences sharedPreferences;
    private int[] layoutList;
    private TextView[] dots;
    private MyViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);
        binding = DataBindingUtil.setContentView(IntroductionActivity.this, R.layout.activity_introduction);

        initLayout();
        introLaunchRemember();


        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current = getItem(+1);
                if (current < layoutList.length) {
                    binding.viewPager.setCurrentItem(current);
                } else {
                    launchHomeScreen();
                }
            }
        });

        binding.btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current = getItem(+1);
                if (current < layoutList.length) {
                    // move to next screen
                    binding.viewPager.setCurrentItem(current + 2);
                } else {
                    launchHomeScreen();
                }
            }
        });

        viewPagerAdapter = new MyViewPagerAdapter();
        binding.viewPager.setAdapter(viewPagerAdapter);
        binding.viewPager.addOnPageChangeListener(onPageChangeListener);

        addBottomDots(0);
        // changeStatusBarColor();
    }

    private void introLaunchRemember() {
        sharedPreferences = StatusSharedPreferences.getStatusSharedPreferences(this);
        if (sharedPreferences.UserVisitStatus()) {
            launchHomeScreen();
        } else {
            sharedPreferences.insertFirstTime(true);
        }

    }

    private void initLayout() {
        layoutList = new int[]{

                R.layout.intro1,
                R.layout.intro2,
                R.layout.intro3
        };

/*        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                    View.SYSTEM_UI_FLAG_FULLSCREEN);
        }*/
    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);
            if (position == layoutList.length - 1) {
                binding.btnNext.setText("START");
            } else {
                binding.btnNext.setText("NEXT");
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };

    private void addBottomDots(int currentPage) {
        dots = new TextView[layoutList.length];
        int[] activeColors = getResources().getIntArray(R.array.array_dot_active);
        int[] inActiveColors = getResources().getIntArray(R.array.array_dot_inactive);
        binding.layoutDots.removeAllViews();

        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(30);
            dots[i].setTextColor(inActiveColors[currentPage]);
            binding.layoutDots.addView(dots[i]);
        }
        if (dots.length > 0) {
            dots[currentPage].setTextColor(activeColors[currentPage]);
        }
    }

    public class MyViewPagerAdapter extends PagerAdapter {
        LayoutInflater layoutInflater;

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(layoutList[position], container, false);
            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return layoutList.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }

    private int getItem(int i) {
        return binding.viewPager.getCurrentItem() + 1;
    }

    private void launchHomeScreen() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}