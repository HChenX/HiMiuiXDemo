package com.hchen.himiuixdemo;

import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    protected static String TAG = "MiuiPreference";

    public enum TestEnum {
        Hook,
        Hook2,
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Toolbar toolbar = findViewById(R.id.toolbar);
        ViewPager2 viewPager2 = findViewById(R.id.view_pager);
        RadioGroup radioGroup = findViewById(R.id.radio_group);

        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new BasePreferenceFragment.PrefsHome());
        fragments.add(new BasePreferenceFragment.PrefsSettings());
        fragments.add(new BasePreferenceFragment.PrefsAbout());

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
        viewPager2.setUserInputEnabled(false);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radio_button_home) {
                    viewPager2.setCurrentItem(0);
                    toolbar.setTitle("Home");

                } else if (checkedId == R.id.radio_button_settings) {
                    viewPager2.setCurrentItem(1);
                    toolbar.setTitle("Settings");
                } else if (checkedId == R.id.radio_button_about) {
                    viewPager2.setCurrentItem(2);
                    toolbar.setTitle("About");
                }
            }
        });
        test(TestEnum.Hook);
    }
    
    public void test(TestEnum testEnum){
        if (testEnum == TestEnum.Hook){
            Toast.makeText(this, "Hook", Toast.LENGTH_SHORT).show();
        } else if (testEnum == TestEnum.Hook2) {
            Toast.makeText(this, "Hook2", Toast.LENGTH_SHORT).show();
        }
    }
}