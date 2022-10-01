package com.bugbd.wifiscane.Fragment_Activity;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.bugbd.wifiscane.R;
import com.bugbd.wifiscane.adapter.Tabaccesser_adapter;
import com.bugbd.wifiscane.tab_fragment.CreateFragment;
import com.bugbd.wifiscane.tab_fragment.ScancodehistoryFragment;
import com.google.android.material.tabs.TabLayout;

public class ListFragment extends Fragment implements TabLayout.OnTabSelectedListener {

    private View view;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Handler handler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list, container, false);
        tabLayout = view.findViewById(R.id.tab_id);
        viewPager = view.findViewById(R.id.viewpagerid);

        handler = new Handler();
        Tabaccesser_adapter adapter = new Tabaccesser_adapter(getFragmentManager());
        adapter.addFragment(new ScancodehistoryFragment());
        adapter.addFragment(new CreateFragment());


        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(1);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setupWithViewPager(viewPager);


        return view;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

}
