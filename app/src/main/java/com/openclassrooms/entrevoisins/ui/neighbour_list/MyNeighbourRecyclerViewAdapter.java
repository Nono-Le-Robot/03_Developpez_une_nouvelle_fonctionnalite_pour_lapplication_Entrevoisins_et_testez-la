package com.openclassrooms.entrevoisins.ui.neighbour_list;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.events.DeleteNeighbourEvent;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyNeighbourRecyclerViewAdapter extends RecyclerView.Adapter<MyNeighbourRecyclerViewAdapter.ViewHolder> {

    private final List<Neighbour> mNeighbours;

    public MyNeighbourRecyclerViewAdapter(List<Neighbour> items) {
        mNeighbours = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_neighbour, parent, false);

        return new ViewHolder(view);
    }

    public void updateData(List<Neighbour> neighbours) {
        notifyDataSetChanged();
    }


    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        NeighbourApiService mApiService =  DI.getNeighbourApiService();
        Neighbour neighbour = mNeighbours.get(position);
        holder.mNeighbourName.setText(neighbour.getName());
        Glide.with(holder.mNeighbourAvatar.getContext())
                .load(neighbour.getAvatarUrl())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.mNeighbourAvatar);
        int tabPos = mApiService.getTabPosition();
        // Vérifiez si l'icône de suppression doit être visible ou non
        int tabPosition = mApiService.getTabPosition();
        if (neighbour.getFavorite() && tabPosition == 1) {
            holder.mDeleteButton.setVisibility(View.GONE);
        } else{
             holder.mDeleteButton.setVisibility(View.VISIBLE);
        }




        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Neighbour selectedNeighbour = mNeighbours.get(position);

                // Créer un Intent pour ouvrir NeighbourDetailActivity
                Intent intent = new Intent(view.getContext(), NeighbourDetailActivity.class);

                // Transmettre les données du voisin à NeighbourDetailActivity
                intent.putExtra("neighbourId", String.valueOf(selectedNeighbour.getId()));
                intent.putExtra("neighbourName", selectedNeighbour.getName());
                intent.putExtra("neighbourAvatarUrl", selectedNeighbour.getAvatarUrl());
                intent.putExtra("neighbourAddress", selectedNeighbour.getAddress());
                intent.putExtra("neighbourPhoneNumber", selectedNeighbour.getPhoneNumber());
                intent.putExtra("neighbourAboutMe", selectedNeighbour.getAboutMe());
                intent.putExtra("neighbourIsFavorite", selectedNeighbour.getFavorite());

                view.getContext().startActivity(intent);
            }
        });
        holder.mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new DeleteNeighbourEvent(neighbour));
            }
        });



    }

    @Override
    public int getItemCount() {
        return mNeighbours.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_list_avatar)
        public ImageView mNeighbourAvatar;
        @BindView(R.id.item_list_name)
        public TextView mNeighbourName;
        @BindView(R.id.item_list_delete_button)
        public ImageButton mDeleteButton;

        public View mView;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);

        }
    }
}
