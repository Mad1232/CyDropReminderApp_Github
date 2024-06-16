package com.example.cydropreminderapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Iterator;

public class DeletePetActivity extends AppCompatActivity {
    private ArrayList<Pet> newpetList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_delete_pet);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        newpetList = getIntent().getParcelableExtra("petList"); //Retrieve the prop petList(arraylist) from PetActivity.java

        Button submit_btn = findViewById(R.id.submit);
        EditText name = findViewById(R.id.name);
        EditText dob = findViewById(R.id.dob);

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String petName = name.getText().toString();
                String petDob = dob.getText().toString();

                boolean petFoundAndDeleted = deletePet(petName, petDob);

                if (petFoundAndDeleted) {
                    Toast.makeText(DeletePetActivity.this, "Pet deleted successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DeletePetActivity.this, "Pet not found", Toast.LENGTH_SHORT).show();
                }

                // Return to PetActivity after deletion
                finish();


            }
            // Method to delete a pet based on name and dob
            private boolean deletePet(String name, String dob) {
                // Create an iterator to iterate through the petList
                Iterator<Pet> iterator = newpetList.iterator();

                // Iterate through the list
                while (iterator.hasNext()) {
                    Pet pet = iterator.next(); // Get the next pet in the list

                    // Check if the pet's name and dob match the given values
                    if (pet.getName().equals(name) && pet.getDob().equals(dob)) {
                        iterator.remove(); // Remove the pet from the list
                        return true; // Return true indicating the pet was found and deleted
                    }
                }
                return false; // Return false indicating the pet was not found
            }
        });
    }
}