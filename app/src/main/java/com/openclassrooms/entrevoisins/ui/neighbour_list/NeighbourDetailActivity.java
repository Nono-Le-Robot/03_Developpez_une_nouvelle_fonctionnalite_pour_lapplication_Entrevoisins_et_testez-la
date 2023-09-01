package com.openclassrooms.entrevoisins.ui.neighbour_list;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.events.DeleteNeighbourEvent;

import org.greenrobot.eventbus.EventBus;

public class NeighbourDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
//        ButterKnife.bind(this);
        setContentView(R.layout.activity_neighbour_detail);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // Récupérer les données transmises depuis l'Intent
        Intent intent = getIntent();
        if (intent != null) {
            String neighbourId = intent.getStringExtra("neighbourId");
            String neighbourName = intent.getStringExtra("neighbourName");
            String neighbourAvatarUrl = intent.getStringExtra("neighbourAvatarUrl");
            String neighbourAddress= intent.getStringExtra("neighbourAddress");
            String neighbourPhoneNumber = intent.getStringExtra("neighbourPhoneNumber");
            String neighbourAboutMe = intent.getStringExtra("neighbourAboutMe");

            Log.d("nId", " " + neighbourId);
            Log.d("nName", " " + neighbourName);
            Log.d("nAvatarUrl", " " + neighbourAvatarUrl);
            Log.d("nAddress", " " + neighbourAddress);
            Log.d("nPhoneNumber", " " + neighbourPhoneNumber);
            Log.d("nAboutMe", " " + neighbourAboutMe);

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




            // Utilisez les données comme bon vous semble, par exemple, pour afficher le nom du voisin.
            // Assurez-vous d'adapter cela à vos besoins spécifiques.
        }
    }
}