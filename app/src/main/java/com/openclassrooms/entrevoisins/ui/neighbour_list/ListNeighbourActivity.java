package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ListNeighbourActivity extends AppCompatActivity {
    private ImageButton deleteButton;
    private List<Neighbour> mNeighbours;

    private RecyclerView mRecyclerView;

    int tabPos;



    FloatingActionButton viewDetail;

    // UI Components
    @BindView(R.id.tabs)
    TabLayout mTabLayout;
//    @BindView(R.id.toolbar)
//    Toolbar mToolbar;
    @BindView(R.id.container)
    ViewPager mViewPager;

    ListNeighbourPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_neighbour);
        ButterKnife.bind(this);
        NeighbourApiService mApiService =  DI.getNeighbourApiService();
        mPagerAdapter = new ListNeighbourPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        MyNeighbourRecyclerViewAdapter mAdapter = new MyNeighbourRecyclerViewAdapter(mNeighbours);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {MyNeighbourRecyclerViewAdapter mAdapter = new MyNeighbourRecyclerViewAdapter(mNeighbours);


            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                switch (position) {
                    case 0:
                        mViewPager.setCurrentItem(0);
                        mApiService.setTabPosition(0);
                        mNeighbours = mApiService.getNeighbours();
                        mAdapter.updateData(mNeighbours);
                        mPagerAdapter.notifyDataSetChanged();
                        break;
                    case 1:
                        mViewPager.setCurrentItem(1);
                        mApiService.setTabPosition(1);
                        mNeighbours = mApiService.getAllFavorite();
                        mAdapter.updateData(mNeighbours);
                        mPagerAdapter.notifyDataSetChanged();
                        break;
                    default:
                        break;
                }

                Fragment neighbourFragmentSelector = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.container + ":" + mViewPager.getCurrentItem());
                if (neighbourFragmentSelector instanceof NeighbourFragment) {
                    ((NeighbourFragment) neighbourFragmentSelector).onResume();
                }

                Fragment favoriteNeighbourFragmentSelector = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.container + ":" + mViewPager.getCurrentItem());
                if (favoriteNeighbourFragmentSelector instanceof FavoriteNeighbourFragment) {
                    ((FavoriteNeighbourFragment) favoriteNeighbourFragmentSelector).onResume();
                }
            }




            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });




    }

    @OnClick(R.id.add_neighbour)
    void addNeighbour() {
        AddNeighbourActivity.navigate(this);
    }
}
