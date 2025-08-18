/*
 * This file is part of HiMiuixDemo.
 *
 * All rights reserved.
 *
 * Copyright (C) 2023-2025 HChenX
 *
 * This software is proprietary and confidential. Unauthorized copying,
 * distribution, modification, or any other use of this file, via any medium,
 * is strictly prohibited without prior written permission from the author.
 */
package com.hchen.himiuixdemo;

import android.os.Bundle;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.hchen.himiuixdemo.fragment.AboutFragment;
import com.hchen.himiuixdemo.fragment.HomeFragment;
import com.hchen.himiuixdemo.fragment.SettingsFragment;

import java.util.ArrayList;

/**
 * Main Activity
 *
 * @author 焕晨HChen
 */
public class MainActivity extends BasicActivity {
    private static final String TAG = "HiMiuix:Activity";
    private int radioGroupHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);

        ViewPager2 viewPager2 = (ViewPager2) content;
        RadioGroup radioGroup = findViewById(R.id.radio_group);

        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new SettingsFragment());
        fragments.add(new AboutFragment());

        viewPager2.setUserInputEnabled(false);
        viewPager2.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return fragments.get(position);
            }

            @Override
            public int getItemCount() {
                return fragments.size();
            }
        });
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0 -> {
                        xAppBar.setTitle("Home");
                        radioGroup.check(R.id.home);
                    }
                    case 1 -> {
                        xAppBar.setTitle("Settings");
                        radioGroup.check(R.id.settings);
                    }
                    case 2 -> {
                        xAppBar.setTitle("About");
                        radioGroup.check(R.id.about);
                    }
                }
            }
        });

        radioGroup.post(() -> radioGroupHeight = radioGroup.getHeight());
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull RadioGroup group, int checkedId) {
                if (checkedId == R.id.home) {
                    xAppBar.setTitle("Home");
                    viewPager2.setCurrentItem(0);
                } else if (checkedId == R.id.settings) {
                    xAppBar.setTitle("Settings");
                    viewPager2.setCurrentItem(1);
                } else if (checkedId == R.id.about) {
                    xAppBar.setTitle("About");
                    viewPager2.setCurrentItem(2);
                }
            }
        });
    }

    @Override
    int paddingLossOfLoss() {
        return radioGroupHeight;
    }
}