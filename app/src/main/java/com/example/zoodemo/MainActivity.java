package com.example.zoodemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    public static final String TAG_ZOO_HOME_FRAGMENT = "zoo_home_fragment";
    public static final String TAG_CATEGORY_FRAGMENT = "zoo_category_fragment";
    public static final String TAG_PLANT_FRAGMENT = "zoo_plant_fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Fragment fragment = new ZooHomeFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction tx = fm.beginTransaction();
        tx.add(R.id.id_content, fragment, TAG_ZOO_HOME_FRAGMENT);
        tx.commit();
    }
}
