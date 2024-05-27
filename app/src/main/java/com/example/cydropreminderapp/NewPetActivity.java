package com.example.cydropreminderapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class NewPetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_pet);

        Spinner petSpinner = findViewById(R.id.pet_spinner);
        ArrayAdapter<CharSequence> petAdapter = ArrayAdapter.createFromResource(this,
                R.array.pet_options, android.R.layout.simple_spinner_item);
        petAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        petSpinner.setAdapter(petAdapter);

        petSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String item = adapterView.getItemAtPosition(position).toString();
                Toast.makeText(NewPetActivity.this, "Selected pet: " + item, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Do nothing
            }
        });

        Spinner sexSpinner = findViewById(R.id.sex_spinner);
        ArrayAdapter<CharSequence> sexAdapter = ArrayAdapter.createFromResource(this, R.array.sex_options, android.R.layout.simple_spinner_item);
        sexAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sexSpinner.setAdapter(sexAdapter);

        sexSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String item = adapterView.getItemAtPosition(position).toString();
                Toast.makeText(NewPetActivity.this, "Selected sex: " + item, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Do nothing
            }
        });

        Spinner diagnosisSpinner = findViewById(R.id.diagnosis_spinner);
        ArrayAdapter<CharSequence> diagnosisAdapter = ArrayAdapter.createFromResource(this, R.array.diagnosis_options, android.R.layout.simple_spinner_item);
        diagnosisAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        diagnosisSpinner.setAdapter(diagnosisAdapter);

        diagnosisSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String item = adapterView.getItemAtPosition(position).toString();
                Toast.makeText(NewPetActivity.this, "Selected diagnosis: " + item, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Do nothing
            }
        });

        // Initialize EditText fields for user input
        EditText name = findViewById(R.id.name);
        EditText dob = findViewById(R.id.dob);
        EditText breed = findViewById(R.id.breed);

        Button submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get details from the input fields
                String petName = name.getText().toString();
                String petDob = dob.getText().toString();
                String petDiagnosis = diagnosisSpinner.getSelectedItem().toString();
                String petSex = sexSpinner.getSelectedItem().toString();
                String petType = petSpinner.getSelectedItem().toString();
                String petBreed = breed.getText().toString();

                Pet newPet = new Pet(petName, petType, petSex, petDob, petDiagnosis, petBreed); // Create a new pet object

                // Navigate back to PetActivity
                Intent intent = new Intent(NewPetActivity.this, PetActivity.class);
                intent.putExtra("newPet", newPet); // Pass the new pet object
                startActivity(intent);
            }
        });
    }
}
