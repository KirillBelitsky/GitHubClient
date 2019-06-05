package com.example.githubclient.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.githubclient.R;
import com.example.githubclient.ui.fragment.RepositoryFilesFragment;
import com.example.githubclient.ui.fragment.RepositoryInfoFragment;

import java.util.ArrayList;
import java.util.List;

import static com.example.githubclient.constants.Constants.LOGIN;
import static com.example.githubclient.constants.Constants.REPOSITORY;

public class RepositoryActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repository);

        viewPager = findViewById(R.id.viewpager_repo);
        setupViewPager(viewPager);

        tabLayout = findViewById(R.id.sliding_tabs_repo);
        tabLayout.setupWithViewPager(viewPager);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        this.setTitle(getIntent().getStringExtra(REPOSITORY));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home)
            finish();

        return true;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        Bundle bundle = new Bundle();

        RepositoryInfoFragment repoInfoFragment = new RepositoryInfoFragment();
        RepositoryFilesFragment repoFilesFragment = new RepositoryFilesFragment();

        bundle.putString(LOGIN,getIntent().getStringExtra(LOGIN));
        bundle.putString(REPOSITORY,getIntent().getStringExtra(REPOSITORY));
        repoFilesFragment.setArguments(bundle);
        repoInfoFragment.setArguments(bundle);

        adapter.addFrag(repoInfoFragment, "Info");
        adapter.addFrag(repoFilesFragment, "Files");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
