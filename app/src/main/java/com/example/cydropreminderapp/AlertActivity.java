package com.example.cydropreminderapp;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.lang.reflect.Array;

public class AlertActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_alert);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //Initialize all variables
        AutoCompleteTextView drugauto = findViewById(R.id.drug_autocomplete);
        Spinner concentration = findViewById(R.id.concentration_spinner);
        Spinner oral = findViewById(R.id.oral_spinner);
        Spinner dose = findViewById(R.id.dose_spinner);
        Spinner eye = findViewById(R.id.eye_spinner);
        TextView recommended_text = findViewById(R.id.recommendation_text);

        //Concentration Spinner
        ArrayAdapter<CharSequence> concentrationAdapter = ArrayAdapter.createFromResource(this, R.array.concentration_options, android.R.layout.simple_spinner_item);
        concentrationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        concentration.setAdapter(concentrationAdapter);

        concentration.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String item = adapterView.getItemAtPosition(position).toString();
                Toast.makeText(AlertActivity.this, "Selected concentration: " + item, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Do nothing
            }
        });

        //Oral/Topical Spinner
        ArrayAdapter<CharSequence> oralAdapter = ArrayAdapter.createFromResource(this, R.array.oral_options, android.R.layout.simple_spinner_item);
        oralAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        oral.setAdapter(oralAdapter);

        oral.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String item = adapterView.getItemAtPosition(position).toString();
                Toast.makeText(AlertActivity.this, "Selected: " + item, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Do nothing
            }
        });

        //Dose Spinner
        ArrayAdapter<CharSequence> doseAdapter = ArrayAdapter.createFromResource(this, R.array.dose_options, android.R.layout.simple_spinner_item);
        doseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dose.setAdapter(doseAdapter);

        dose.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String item = adapterView.getItemAtPosition(position).toString();
                Toast.makeText(AlertActivity.this, "Selected frequency: " + item, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Do nothing
            }
        });
    }



}
