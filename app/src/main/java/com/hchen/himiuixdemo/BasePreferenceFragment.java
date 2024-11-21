package com.hchen.himiuixdemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.hchen.himiuix.DialogInterface;
import com.hchen.himiuix.MiuiCardPreference;
import com.hchen.himiuix.MiuiPreference;
import com.hchen.himiuix.MiuiSwitchPreference;
import com.hchen.himiuix.MiuiAlertDialog;

import java.util.ArrayList;

public abstract class BasePreferenceFragment extends PreferenceFragmentCompat {
    private final static String TAG = "MiuiPreference";

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
            Handler handler = new Handler(Looper.getMainLooper()) {
                @Override
                public void handleMessage(@NonNull Message msg) {
                    if (msg.what == 1) {
                        MiuiSwitchPreference preference = (MiuiSwitchPreference) ((Object[]) msg.obj)[0];
                        boolean value = (boolean) ((Object[]) msg.obj)[1];
                        preference.setChecked(!value);
                        Log.i(TAG, "handleMessage: newValue: " + !value);
                    }
                }
            };
            MiuiSwitchPreference miuiSwitchPreference = findPreference("pref_child");
            MiuiPreference preference = findPreference("pref_intent");
            miuiSwitchPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(@NonNull Preference preference, Object newValue) {
                    Message message = new Message();
                    message.what = 1;
                    message.obj = new Object[]{preference, newValue};
                    handler.removeMessages(1);
                    handler.sendMessageDelayed(message, 0);
                    return true;
                }
            });
            preference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(@NonNull Preference preference) {
                    Toast.makeText(getContext(), "你点击了我！", Toast.LENGTH_SHORT).show();
                    new Handler(Looper.getMainLooper()).postDelayed(() -> {
                        ((MiuiPreference) preference).setTipText("哇卡哇卡！");
                    }, 500);
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
                    .setPositiveButton("确定", null)
                    .setNegativeButton("拒绝", null);
            switch (preference.getKey()) {
                case "prefs_dialog" -> {
                    miuiAlertDialog
                            .show();
                }
                case "prefs_edit_dialog" -> {
                    miuiAlertDialog
                            .setEnableEditTextView(true)
                            .setEditText("", new DialogInterface.TextWatcher() {
                                @Override
                                public void onResult(DialogInterface dialog, CharSequence s) {
                                    Toast.makeText(getContext(), "你输入了: " + s, Toast.LENGTH_SHORT).show();
                                }
                            })
                            .show();
                }
                case "prefs_list_dialog" -> {
                    miuiAlertDialog
                            .setEnableListSelectView(true)
                            .setEnableMultiSelect(true)
                            .setEnableListSpringBack(true)
                            .setItems(new CharSequence[]{"焕晨0", "小焕晨0", "小焕晨1", "大焕晨0", "大焕晨1", "大焕晨2", "大焕晨3", "大焕晨4", 
                                    "大焕晨5", "大焕晨6", "大焕晨7", "大焕晨8", "大焕晨9"}, new DialogInterface.OnItemsClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, CharSequence item, int which) {
                                    // Toast.makeText(getContext(), "你点击了: " + item, Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onResult(DialogInterface dialog, ArrayList<CharSequence> items, ArrayList<CharSequence> selectedItems) {
                                    Toast.makeText(getContext(), "结果: " + selectedItems, Toast.LENGTH_SHORT).show();
                                }
                            })
                            .show();
                }
                case "prefs_view_dialog" -> {
                    miuiAlertDialog
                            .setEnableCustomView(true)
                            .setCustomView(R.layout.custom_view, new DialogInterface.OnBindView() {
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
