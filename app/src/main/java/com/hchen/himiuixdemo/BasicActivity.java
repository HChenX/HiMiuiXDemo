package com.hchen.himiuixdemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.hchen.himiuix.MiuixAppBar;
import com.hchen.himiuix.helper.ImeHelper;

public class BasicActivity extends AppCompatActivity implements PreferenceFragmentCompat.OnPreferenceStartFragmentCallback {
    private static final String TAG = "HiMiuix:Activity";
    protected View content;
    protected MiuixAppBar xAppBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        getWindow().setNavigationBarContrastEnforced(false); // Xiaomi moment, this code must be here
        content = findViewById(R.id.content);
        xAppBar = findViewById(R.id.appbar);
        setSupportActionBar(xAppBar.getToolbar());
        fixIme();
    }

    int paddingLossOfLoss() {
        return 0;
    }

    private void fixIme() {
        // 使用此方法手动控制 EditText 键盘布局顶起行为
        // Android FUCK YOU!!
        ViewCompat.setOnApplyWindowInsetsListener(getWindow().getDecorView(), new OnApplyWindowInsetsListener() {
            private int originalHeight;

            {
                content.post(() -> originalHeight = content.getHeight());
            }

            @NonNull
            @Override
            public WindowInsetsCompat onApplyWindowInsets(@NonNull View v, @NonNull WindowInsetsCompat insets) {
                ImeHelper.init().onApplyWindowInsets(v, insets);

                if (insets.isVisible(WindowInsetsCompat.Type.ime())) {
                    Insets ime = insets.getInsets(WindowInsetsCompat.Type.ime());
                    if (ime.bottom != 0) {
                        Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                        content.setPadding(0, 0, 0, ime.bottom - systemBars.bottom - paddingLossOfLoss());
                    }
                } else if (originalHeight != 0) {
                    content.setPadding(0, 0, 0, 0);
                }
                return insets;
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), new OnApplyWindowInsetsListener() {
            @NonNull @Override
            public WindowInsetsCompat onApplyWindowInsets(@NonNull View v, @NonNull WindowInsetsCompat insets) {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            }
        });
    }

    @Override
    public boolean onPreferenceStartFragment(@NonNull PreferenceFragmentCompat caller, @NonNull Preference pref) {
        Bundle arg = pref.getExtras();
        String fragment = pref.getFragment();
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.setClass(getBaseContext(), FragmentActivity.class);
        intent.putExtra("fragment:name", fragment);
        intent.putExtra("fragment:arg", arg);
        startActivity(intent);
        return true;
    }
}
