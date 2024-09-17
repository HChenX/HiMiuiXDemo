package com.hchen.himiuixdemo;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.hchen.himiuix.DialogInterface;
import com.hchen.himiuix.MiuiAlertDialog;
import com.hchen.himiuix.MiuiCardPreference;
import com.hchen.himiuix.MiuiPreference;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class BasePreferenceFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(id(), rootKey);
        initPrefs();
    }

    abstract public int id();

    public void initPrefs() {
    }

    public static class PrefsHome extends BasePreferenceFragment {

        @Override
        public int id() {
            return R.xml.prefs_home;
        }

        @Override
        public void initPrefs() {
            MiuiPreference preference = findPreference("pref_intent");
            preference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(@NonNull Preference preference) {
                    Toast.makeText(getContext(), "你点击了我！", Toast.LENGTH_SHORT).show();
                    return true;
                }
            });
        }
    }

    public static class PrefsSettings extends BasePreferenceFragment implements Preference.OnPreferenceClickListener {

        @Override
        public int id() {
            return R.xml.prefs_settings;
        }

        @Override
        public void initPrefs() {
            MiuiPreference preference = findPreference("prefs_dialog");
            MiuiPreference preference2 = findPreference("prefs_edit_dialog");
            MiuiPreference preference3 = findPreference("prefs_list_dialog");
            MiuiPreference preference4 = findPreference("prefs_view_dialog");
            preference.setOnPreferenceClickListener(this);
            preference2.setOnPreferenceClickListener(this);
            preference3.setOnPreferenceClickListener(this);
            preference4.setOnPreferenceClickListener(this);
        }

        @Override
        public boolean onPreferenceClick(@NonNull Preference preference) {
            MiuiAlertDialog miuiAlertDialog = new MiuiAlertDialog(getContext())
                    .setTitle("焕晨")
                    .setMessage("小焕晨")
                    .setHapticFeedbackEnabled(true)
                    .setCanceledOnTouchOutside(false)
                    .setTextTypeface(new MiuiAlertDialog.MakeTypeface() {
                        @Override
                        public void onMakeTypeface(HashMap<MiuiAlertDialog.TypefaceObject, Typeface> typefaceHashMap) {
                            typefaceHashMap.put(MiuiAlertDialog.TypefaceObject.TYPEFACE_ALERT_TITLE, Typeface.DEFAULT_BOLD);
                        }
                    })
                    .setPositiveButton("确定", null)
                    .setNegativeButton("拒绝", null);
            switch (preference.getKey()) {
                case "prefs_dialog" -> {
                    miuiAlertDialog
                            .show();
                }
                case "prefs_edit_dialog" -> {
                    miuiAlertDialog
                            .setEditText(new DialogInterface.TextWatcher() {
                                @Override
                                public void onResult(CharSequence s) {
                                    Toast.makeText(getContext(), "你输入了: " + s, Toast.LENGTH_SHORT).show();
                                }
                            })
                            .show();
                }
                case "prefs_list_dialog" -> {
                    miuiAlertDialog
                            .isMultiSelect(true)
                            .setItems(new CharSequence[]{"焕晨", "大焕晨", "小焕晨"}, new DialogInterface.OnItemsChangeListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, CharSequence item, int which) {
                                    Toast.makeText(getContext(), "你点击了: " + item, Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onResult(ArrayList<CharSequence> selectedItems, ArrayList<CharSequence> items, SparseBooleanArray booleanArray) {
                                    Toast.makeText(getContext(), "结果: " + selectedItems, Toast.LENGTH_SHORT).show();
                                }
                            })
                            .show();
                }
                case "prefs_view_dialog" -> {
                    miuiAlertDialog
                            .setCustomView(R.layout.custom_view, new MiuiAlertDialog.OnBindView() {
                                @Override
                                public void onBindView(View view) {
                                    ImageView imageView = view.findViewById(R.id.image);
                                    imageView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            v.performHapticFeedback(HapticFeedbackConstants.CLOCK_TICK);
                                            Toast.makeText(getContext(), "是焕晨！", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            })
                            .show();

                }
            }
            return true;
        }
    }

    public static class PrefsAbout extends BasePreferenceFragment {

        @Override
        public int id() {
            return R.xml.prefs_about;
        }

        @Override
        public void initPrefs() {
            MiuiCardPreference miuiCardPreference = findPreference("prefs_card");

            miuiCardPreference.setCustomViewCallBack(new MiuiCardPreference.OnBindView() {
                @Override
                public void onBindView(View view) {
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(getContext(), "你点击了我！", Toast.LENGTH_SHORT).show();
                            v.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK);
                        }
                    });
                }
            });
            miuiCardPreference.setIconClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "你点击了图标！", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}
