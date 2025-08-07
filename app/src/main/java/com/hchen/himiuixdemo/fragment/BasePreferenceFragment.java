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

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.preference.PreferenceFragmentCompat;

/**
 * Base Fragment
 *
 * @author 焕晨HChen
 */
public abstract class BasePreferenceFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        if (prefsId() != 0) {
            addPreferencesFromResource(prefsId());
            initPrefs();
        }
    }

    abstract int prefsId();

    abstract void initPrefs();
}
