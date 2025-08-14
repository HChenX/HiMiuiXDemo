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

import android.content.Intent;
import android.net.Uri;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.Preference;

import com.hchen.himiuix.callback.MiuixDialogInterface;
import com.hchen.himiuix.callback.OnChooseItemListener;
import com.hchen.himiuix.dialog.MiuixAlertDialog;
import com.hchen.himiuix.preference.MiuixDropDownPreference;
import com.hchen.himiuix.preference.MiuixPreference;
import com.hchen.himiuixdemo.R;

/**
 * About Fragment
 *
 * @author 焕晨HChen
 */
public class AboutFragment extends BasePreferenceFragment implements View.OnClickListener {
    @Override
    int prefsId() {
        return R.xml.prefs_about;
    }

    @Override
    void initPrefs() {
        MiuixPreference preference = findPreference("prefs_about");

        MiuixPreference preference1 = findPreference("prefs_hc");
        preference1.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(@NonNull Preference preference) {
                new MiuixAlertDialog(getContext())
                    .setTitle("提示")
                    .setMessage("是否前往 HChenX 的 Github 主页？")
                    .setHapticFeedbackEnabled(true)
                    .setNegativeButton("取消", null)
                    .setPositiveButton("前往", new MiuixDialogInterface.OnClickListener() {
                        @Override
                        public void onClick(MiuixDialogInterface dialog, int which) {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse("https://github.com/HChenX"));
                            getContext().startActivity(intent);
                        }
                    })
                    .show();
                return true;
            }
        });

        MiuixDropDownPreference dropDownPreference = findPreference("prefs_color");
        switch (AppCompatDelegate.getDefaultNightMode()) {
            case AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM,
                 AppCompatDelegate.MODE_NIGHT_UNSPECIFIED -> dropDownPreference.setValue("0");
            case AppCompatDelegate.MODE_NIGHT_NO -> dropDownPreference.setValue("1");
            case AppCompatDelegate.MODE_NIGHT_YES -> dropDownPreference.setValue("2");
        }

        dropDownPreference.setOnChooseItemListener(new OnChooseItemListener() {
            @Override
            public boolean onChooseBefore(CharSequence item, int which) {
                switch (which) {
                    case 0 ->
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                    case 1 ->
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    case 2 ->
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
                return true;
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.github_source) {
            new MiuixAlertDialog(getContext())
                .setTitle("提示")
                .setMessage("是否前往 Github 查看源码？")
                .setHapticFeedbackEnabled(true)
                .setNegativeButton("取消", null)
                .setPositiveButton("前往", new MiuixDialogInterface.OnClickListener() {
                    @Override
                    public void onClick(MiuixDialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("https://github.com/HChenX/HiMiuix"));
                        getContext().startActivity(intent);
                    }
                })
                .show();
        }
        if (v.getId() == R.id.tg_group) {
            new MiuixAlertDialog(getContext())
                .setTitle("提示")
                .setMessage("是否前往加入 TG 群组？")
                .setHapticFeedbackEnabled(true)
                .setNegativeButton("取消", null)
                .setPositiveButton("前往", new MiuixDialogInterface.OnClickListener() {
                    @Override
                    public void onClick(MiuixDialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("https://t.me/HChenX_Chat"));
                        getContext().startActivity(intent);
                    }
                })
                .show();
        }
    }
}
