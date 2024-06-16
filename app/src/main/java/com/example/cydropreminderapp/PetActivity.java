package com.example.cydropreminderapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class PetActivity extends AppCompatActivity {

    public static ArrayList<Pet> petList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet);

        // Check if an intent is available with the new pet data
        Intent intent = getIntent();
        Pet newPet = intent.getParcelableExtra("newPet");
        if (newPet != null) {
            petList.add(newPet);
        }

        // Get the LinearLayout where pet details will be displayed
        LinearLayout petContainer = findViewById(R.id.pet_container);

        // Dynamically create TextViews for each pet and add them to the container
        for (Pet pet : petList) {
            TextView petDetails = new TextView(this);
            petDetails.setText("Name: " + pet.getName() + "\nDOB: " + pet.getDob() + "\nDiagnosis: " + pet.getDiagnosis() + "\nType: " + pet.getType() + "\nSex: " + pet.getSex() + "\nBreed: " + pet.getBreed());
            petDetails.setPadding(0, 0, 0, 30); // Add padding to create space between entries

            petContainer.addView(petDetails); // Add the TextView to the LinearLayout
        }

        // Set click listener to the add button
        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Navigate to NewPetActivity
                Intent newPetIntent = new Intent(PetActivity.this, NewPetActivity.class);
                startActivity(newPetIntent);
            }
        });

        ImageView settings = findViewById(R.id.gear);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(PetActivity.this, SettingsActivity.class);
                startActivity(intent2);
            }
        });

        Button delete = findViewById(R.id.delete_pet);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent deleteintent = new Intent(PetActivity.this, DeletePetActivity.class);
                deleteintent.putParcelableArrayListExtra("petList",petList); //this is used to pass petList arraylist as a prop to DeletePetActivity
                startActivity(deleteintent);
            }
        });

        ImageView alert = findViewById(R.id.alert);
        alert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent drugs_page = new Intent(PetActivity.this, AlertActivity.class);
                startActivity(drugs_page);
            }
        });


    }
}

//                                        LinearLayout:
//Use when you need to arrange views in a single direction (either horizontally or vertically).
//Suitable for simple UI designs where views need to be aligned in a linear fashion.
//                                        RelativeLayout:
//Use when you need to position views relative to each other or relative to the parent layout.
//Suitable for UI designs where the positioning of views needs to be flexible and responsive to changes.
//                                        ConstraintLayout:
//Use when you need to create complex UI designs with flexible positioning and sizing of views.
//Suitable for designing responsive layouts that adapt to different screen sizes and orientations.
//Ideal for reducing nested layouts and improving performance in complex UI designs.
//Provides powerful features like constraints, guidelines, barriers, and chains for precise control over view positioning and sizing.