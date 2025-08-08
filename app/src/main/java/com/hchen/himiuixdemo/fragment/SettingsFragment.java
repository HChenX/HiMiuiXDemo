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
package com.hchen.himiuixdemo.fragment;


import androidx.appcompat.app.AppCompatDelegate;

import com.hchen.himiuix.callback.OnStateChangeListener;
import com.hchen.himiuix.preference.MiuixSwitchPreference;
import com.hchen.himiuixdemo.R;

/**
 * Settings Fragment
 *
 * @author 焕晨HChen
 */
public class SettingsFragment extends BasePreferenceFragment {
    @Override
    int prefsId() {
        return R.xml.prefs_settings;
    }

    @Override
    void initPrefs() {
        MiuixSwitchPreference preference = findPreference("prefs_ui_switch");
        preference.setChecked(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES);
        preference.setOnStateChangeListener(new OnStateChangeListener() {
            @Override
            public boolean onStateChange(boolean newValue) {
                if (newValue)
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                return true;
            }
        });
    }
}
