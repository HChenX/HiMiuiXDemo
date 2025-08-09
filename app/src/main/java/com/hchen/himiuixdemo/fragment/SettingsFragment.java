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


import androidx.annotation.NonNull;
import androidx.preference.Preference;

import com.hchen.himiuix.dialog.MiuixAlertDialog;
import com.hchen.himiuix.preference.MiuixPreference;
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
        MiuixPreference preference = findPreference("prefs_test");
        preference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(@NonNull Preference preference) {
                new MiuixAlertDialog(getContext())
                    .setTitle("Test Title")
                    .setMessage("Test Message")
                    .setHapticFeedbackEnabled(true)
                    .setCancelable(false)
                    .setCanceledOnTouchOutside(false)
                    .setNegativeButton(getContext().getString(com.hchen.himiuix.R.string.dialog_negative), null)
                    .setPositiveButton(getContext().getString(com.hchen.himiuix.R.string.dialog_positive), null)
                    .show();
                return true;
            }
        });
    }
}
