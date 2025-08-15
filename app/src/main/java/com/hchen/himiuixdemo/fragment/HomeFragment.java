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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hchen.himiuix.MiuixBasicView;
import com.hchen.himiuix.dialog.MiuixAlertDialog;
import com.hchen.himiuix.fragment.Fragment;
import com.hchen.himiuixdemo.R;

/**
 * Home Fragment
 *
 * @author 焕晨HChen
 */
public class HomeFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MiuixBasicView xBasicView = view.findViewById(R.id.miuix_test);
        xBasicView.setOnClickListener(v -> {
            new MiuixAlertDialog(getContext())
                .setTitle("Test Title")
                .setMessage("Test Message")
                .setHapticFeedbackEnabled(true)
                .setCancelable(false)
                .setCanceledOnTouchOutside(false)
                .setNegativeButton(getContext().getString(com.hchen.himiuix.R.string.dialog_negative), null)
                .setPositiveButton(getContext().getString(com.hchen.himiuix.R.string.dialog_positive), null)
                .show();
        });
    }
}
