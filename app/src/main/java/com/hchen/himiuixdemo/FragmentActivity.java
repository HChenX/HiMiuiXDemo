package com.hchen.himiuixdemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentActivity extends BasicActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_fargment);
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        setTitle("HiMiuix");

        Intent intent = getIntent();
        String name = intent.getStringExtra("fragment:name");
        Bundle arg = intent.getBundleExtra("fragment:arg");
        assert name != null;
        Fragment fragment = Fragment.instantiate(getBaseContext(), name, arg);
        getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.content, fragment)
            .commit();

        xAppBar.getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });
    }
}
