package com.example.guto.appbaking.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.guto.appbaking.R;
import com.example.guto.appbaking.adapters.RecipeListAdapter;
import com.example.guto.appbaking.fragments.IngredientFragment;
import com.example.guto.appbaking.fragments.StepFragment;
import com.example.guto.appbaking.model.RecipeModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {
    @BindView(R.id.tabLayout)
    public TabLayout tabLayout;

    @BindView(R.id.viewPager)
    public ViewPager viewPager;

    private RecipeModel recipeParcelable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        if (intent.hasExtra(RecipeListAdapter.RECIPE_PARCELABLE)) {
            recipeParcelable = intent.getExtras().getParcelable(RecipeListAdapter.RECIPE_PARCELABLE);
        }
        getSupportActionBar().setTitle(recipeParcelable.getName());
        setUpViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setUpViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragments(new IngredientFragment(), "Ingredientes");
        adapter.addFragments(new StepFragment(), "Passos");
        viewPager.setAdapter(adapter);
    }

    public RecipeModel getRecipeParcelable() {
        return recipeParcelable;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragments(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
