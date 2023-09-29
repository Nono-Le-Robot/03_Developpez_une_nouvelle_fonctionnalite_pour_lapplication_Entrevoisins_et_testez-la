package com.openclassrooms.entrevoisins.ui.neighbour_list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.events.DeleteNeighbourEvent;
import com.openclassrooms.entrevoisins.service.FavoriteListManager;

import org.greenrobot.eventbus.EventBus;

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

            // ici verifier si l'utilisateur est deja en favoris et metttre l'icone en jaune si c'est le cas
            // on recupére l'icone
            ImageView favIcon = findViewById(R.id.favorite_neighbour);
            // on recupére la liste des favoris
            List<String> favList = FavoriteListManager.getInstance().getFavoriteList();
            //on verifie si l'id est present
            if (favList.contains(neighbourId)) {
                // on met l'icone en jaune
                favIcon.setColorFilter(ContextCompat.getColor(favIcon.getContext(),R.color.yellow), PorterDuff.Mode.SRC_IN);
            }
            else{
                // icone gris
                favIcon.setColorFilter(ContextCompat.getColor(favIcon.getContext(),R.color.gray), PorterDuff.Mode.SRC_IN);
            }

            // listener pour le click sur le btn favoris
            FloatingActionButton favButton = findViewById(R.id.favorite_neighbour);
            favButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageView favIcon = findViewById(R.id.favorite_neighbour);
                    // Vérifier si l'ID est déjà présent dans l'array pour éviter les doublons
                    if (!FavoriteListManager.getInstance().getFavoriteList().contains(neighbourId)) {
                        FavoriteListManager.getInstance().addFavorite(neighbourId);
                        favIcon.setColorFilter(ContextCompat.getColor(favIcon.getContext(), R.color.yellow), PorterDuff.Mode.SRC_IN);
                        // Si l'ajout est réussi, afficher un Toast
                        Toast.makeText(getApplicationContext(), neighbourName + " ajoutée aux favoris !", Toast.LENGTH_SHORT).show();
                    } else {
                        // Si l'ID est déjà présent dans les favoris, supprimer l'utilisateur des favoris
                        FavoriteListManager.getInstance().removeFavorite(neighbourId);
                        favIcon.setColorFilter(ContextCompat.getColor(favIcon.getContext(), R.color.gray), PorterDuff.Mode.SRC_IN);
                        // Afficher un Toast pour indiquer que l'utilisateur a été retiré des favoris
                        Toast.makeText(getApplicationContext(), neighbourName + " retirée des favoris !", Toast.LENGTH_SHORT).show();
                    }
                }

            });

        }
    }

}