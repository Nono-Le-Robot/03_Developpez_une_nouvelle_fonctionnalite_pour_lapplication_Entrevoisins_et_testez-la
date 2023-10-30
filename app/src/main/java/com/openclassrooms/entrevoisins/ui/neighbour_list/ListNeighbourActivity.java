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
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //TODO: comment appeler le reload ?
                deleteButton = mViewPager.findViewById(R.id.item_list_delete_button);
                int position = tab.getPosition();
                switch (position) {
                    case 0:

                        // Onglet "Voisins" sélectionné, afficher le fragment des voisins
                        mApiService.setTabPosition(0);
                        mViewPager.setCurrentItem(0);
                        break;
                    case 1:
                        // Onglet "Favoris" sélectionné, afficher le fragment des favoris.
                        mApiService.setTabPosition(1);
                        mViewPager.setCurrentItem(1);

                        // Appelez la méthode pour mettre à jour la liste des voisins favoris
                       // if (mPagerAdapter.getItem(position) instanceof FavoriteNeighbourFragment) {
                         //   ((FavoriteNeighbourFragment) mPagerAdapter.getItem(position)).updateFavoriteNeighboursList();
                        //}
                        break;

                    default:
                        break;
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
