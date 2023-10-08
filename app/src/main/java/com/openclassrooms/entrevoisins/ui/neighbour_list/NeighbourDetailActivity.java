package com.openclassrooms.entrevoisins.ui.neighbour_list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import java.util.List;

public class NeighbourDetailActivity extends AppCompatActivity {



    @Override


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neighbour_detail);
        Intent intent = getIntent();
        if (intent != null) {
            String neighbourId = intent.getStringExtra("neighbourId");
            String neighbourName = intent.getStringExtra("neighbourName");
            String neighbourAvatarUrl = intent.getStringExtra("neighbourAvatarUrl");
            String neighbourAddress= intent.getStringExtra("neighbourAddress");
            String neighbourPhoneNumber = intent.getStringExtra("neighbourPhoneNumber");
            String neighbourAboutMe = intent.getStringExtra("neighbourAboutMe");

            NeighbourApiService mApiService;
            mApiService = DI.getNeighbourApiService();
            String idToSave = String.valueOf(Integer.parseInt(neighbourId) - 1);
            Neighbour selectedNeighbour = mApiService.getNeighbour(idToSave);
            List<Neighbour> allFavs = mApiService.getAllFavorite();
            Boolean currentFav = selectedNeighbour.getFavorite();
            ImageView favIcon = findViewById(R.id.favorite_neighbour);
            // ici on récupére bien la liste des utilisateur ajoutés en favoris.
            System.out.println(allFavs);
            if (currentFav) {
                // on met l'icone en jaune
                favIcon.setColorFilter(ContextCompat.getColor(favIcon.getContext(),R.color.yellow), PorterDuff.Mode.SRC_IN);
            }
            else{
                // icone gris
                favIcon.setColorFilter(ContextCompat.getColor(favIcon.getContext(),R.color.gray), PorterDuff.Mode.SRC_IN);
            }


            ImageView avatarUrl;
            TextView nameField;
            TextView addressField;
            TextView phoneNumberField;
            TextView aboutMeField;
            TextView nameTitleField;

            avatarUrl = findViewById(R.id.neighbour_avatar_url);

            Glide.with(this)
                    .load(neighbourAvatarUrl)
//                    .apply(RequestOptions.circleCropTransform())
                    .into(avatarUrl);

            nameField= findViewById(R.id.neighbour_name);
            nameField.setText("www.facebook.fr/" + neighbourName.toLowerCase());

            nameTitleField = findViewById(R.id.neighbour_name_title);
            nameTitleField.setText(neighbourName);

            addressField = findViewById(R.id.neighbour_phone_number);
            addressField.setText(neighbourPhoneNumber);

            phoneNumberField = findViewById(R.id.neighbour_address);
            phoneNumberField.setText(neighbourAddress);

            aboutMeField = findViewById(R.id.neighbour_about_me);
            aboutMeField.setText(neighbourAboutMe);

            FloatingActionButton backButton = findViewById(R.id.back_button);

            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();

                }
            });

            // listener pour le click sur le btn favoris
            FloatingActionButton favButton = findViewById(R.id.favorite_neighbour);
            favButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Boolean isFavorite = selectedNeighbour.getFavorite();
                     if(isFavorite){
                         selectedNeighbour.removeFavorite();
                         favIcon.setColorFilter(ContextCompat.getColor(favIcon.getContext(), R.color.gray), PorterDuff.Mode.SRC_IN);
                         Toast.makeText(getApplicationContext(),  neighbourName + " a été retiré(e) à vos favoris", Toast.LENGTH_SHORT).show();
                     }
                     else{
                         selectedNeighbour.addFavorite();
                         favIcon.setColorFilter(ContextCompat.getColor(favIcon.getContext(), R.color.yellow), PorterDuff.Mode.SRC_IN);
                         Toast.makeText(getApplicationContext(),  neighbourName + " a été ajouté(e) de vos favoris", Toast.LENGTH_SHORT).show();
                     }
                    ImageView favIcon = findViewById(R.id.favorite_neighbour);
                }

            });

        }
    }

}