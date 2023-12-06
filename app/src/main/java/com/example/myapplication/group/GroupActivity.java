package com.example.myapplication.group;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.myapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class GroupActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        viewPager = findViewById(R.id.viewPager);
        bottomNavigation = findViewById(R.id.bottomNavigation);

        viewPager.setAdapter(new ViewPagerAdapter(this));
        viewPager.setUserInputEnabled(true);

        bottomNavigation.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.group_nav_find) {
                viewPager.setCurrentItem(0, true);
                return true;
            } else if (id == R.id.group_nav_my) {
                viewPager.setCurrentItem(1, true);
                return true;
            }
            return false;
        });

        viewPager.registerOnPageChangeCallback(new ViewPagerPageChangeCallback());
    }

    private class ViewPagerPageChangeCallback extends ViewPager2.OnPageChangeCallback {
        @Override
        public void onPageSelected(int position) {
            bottomNavigation.getMenu().getItem(position).setChecked(true);
        }
    }

    private static class ViewPagerAdapter extends FragmentStateAdapter {
        public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 1:
                    return new GroupMyFragment();
                case 0:
                default:
                    return new GroupFindFragment();
            }
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }
}