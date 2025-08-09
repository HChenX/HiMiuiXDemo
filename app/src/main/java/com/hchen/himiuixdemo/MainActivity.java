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

import android.graphics.Point;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.hchen.himiuix.utils.MiuixUtils;
import com.hchen.himiuixdemo.fragment.AboutFragment;
import com.hchen.himiuixdemo.fragment.HomeFragment;
import com.hchen.himiuixdemo.fragment.SettingsFragment;

import java.util.ArrayList;

/**
 * Main Activity
 *
 * @author 焕晨HChen
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "HiMiuix:Activity";
    private final int DELAY_IN_LIFTING = 200;
    private View content;
    private int touchX;
    private int touchY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        getWindow().setNavigationBarContrastEnforced(false); // Xiaomi moment, this code must be here

        setContentView(R.layout.activity_main);
        content = findViewById(R.id.content);

        // 使用此方法手动控制 EditText 键盘布局顶起行为
        // Android FUCK YOU!!
        ViewCompat.setOnApplyWindowInsetsListener(getWindow().getDecorView(), new OnApplyWindowInsetsListener() {
            private int originalHeight;
            private final Point windowPoint;

            {
                windowPoint = MiuixUtils.getWindowSize(getApplicationContext());
                content.post(() -> originalHeight = content.getHeight());
            }

            @NonNull
            @Override
            public WindowInsetsCompat onApplyWindowInsets(@NonNull View v, @NonNull WindowInsetsCompat insets) {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

                if (insets.isVisible(WindowInsetsCompat.Type.ime())) {
                    Insets ime = insets.getInsets(WindowInsetsCompat.Type.ime());
                    if (ime.bottom != 0) {
                        int surplus = 0;
                        if (touchY != 0 && (surplus = (windowPoint.y - touchY)) <= ime.bottom) {
                            // 因为 surplus 高度依赖点击位置
                            // 当点击位置过高时会导致 surplus 过大
                            // 这里设置容错，保证 Edit 布局能被完整顶起
                            surplus = surplus - MiuixUtils.dp2px(getApplicationContext(), 25);
                            content.animate()
                                .translationY(-(ime.bottom) + surplus)
                                .setDuration(DELAY_IN_LIFTING)
                                .start();
                        }
                    }
                } else if (originalHeight != 0) {
                    content.animate()
                        .translationY(0)
                        .setDuration(DELAY_IN_LIFTING)
                        .start();
                }
                return insets;
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
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
                        toolbar.setTitle("Home");
                        radioGroup.check(R.id.home);
                    }
                    case 1 -> {
                        toolbar.setTitle("Settings");
                        radioGroup.check(R.id.settings);
                    }
                    case 2 -> {
                        toolbar.setTitle("About");
                        radioGroup.check(R.id.about);
                    }
                }
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull RadioGroup group, int checkedId) {
                if (checkedId == R.id.home) {
                    toolbar.setTitle("Home");
                    viewPager2.setCurrentItem(0);
                } else if (checkedId == R.id.settings) {
                    toolbar.setTitle("Settings");
                    viewPager2.setCurrentItem(1);
                } else if (checkedId == R.id.about) {
                    toolbar.setTitle("About");
                    viewPager2.setCurrentItem(2);
                }
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            touchX = (int) ev.getX();
            touchY = (int) ev.getY();
        }
        return super.dispatchTouchEvent(ev);
    }
}