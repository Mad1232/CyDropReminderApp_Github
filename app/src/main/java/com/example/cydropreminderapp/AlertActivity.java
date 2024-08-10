package com.example.cydropreminderapp;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cydropreminderapp.AlarmReceiver;
import com.example.cydropreminderapp.Drugs;
import com.example.cydropreminderapp.R;

import java.util.ArrayList;
import java.util.Calendar;

public class AlertActivity extends AppCompatActivity {
    private static final String TAG = "AlertActivity"; // Tag for logging

    private Drugs selectedDrug; // The Drugs object to store the user's selections
    private TextView selectedTimeText;
    private ArrayList<Calendar> selectedTimes = new ArrayList<>(); // Store the selected times

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate called");
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_alert);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize views
        AutoCompleteTextView drugAuto = findViewById(R.id.drug_autocomplete);
        Spinner concentrationSpinner = findViewById(R.id.concentration_spinner);
        Spinner oralSpinner = findViewById(R.id.oral_spinner);
        Spinner doseSpinner = findViewById(R.id.dose_spinner);
        Spinner eyeSpinner = findViewById(R.id.eye_spinner);
        selectedTimeText = findViewById(R.id.selected_time_text);
        Button setTimeButton = findViewById(R.id.set_time_button);
        Button setAlarmButton = findViewById(R.id.set_alarm_button);

        selectedDrug = new Drugs("", "", "", 1); // Initialize with default values

        setTimeButton.setOnClickListener(v -> showTimePickerDialog());
        setAlarmButton.setOnClickListener(v -> setAlarms());

        // Set up Drug AutoCompleteTextView
        ArrayAdapter<CharSequence> drugAdapter = ArrayAdapter.createFromResource(this, R.array.drug_names, android.R.layout.simple_dropdown_item_1line);
        drugAuto.setAdapter(drugAdapter);
        drugAuto.setOnItemClickListener((adapterView, view, position, id) -> {
            String name = adapterView.getItemAtPosition(position).toString();
            Toast.makeText(AlertActivity.this, "Selected drug: " + name, Toast.LENGTH_SHORT).show();
            selectedDrug = new Drugs(name, null, null, 1); // Initialize Drugs object with name
        });

        // Set up Concentration Spinner
        ArrayAdapter<CharSequence> concentrationAdapter = ArrayAdapter.createFromResource(this, R.array.concentration_options, android.R.layout.simple_spinner_item);
        concentrationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        concentrationSpinner.setAdapter(concentrationAdapter);
        concentrationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String concentration = adapterView.getItemAtPosition(position).toString();
                if (selectedDrug != null) {
                    selectedDrug.setConcentration(concentration); // Set concentration in Drugs object
                    Toast.makeText(AlertActivity.this, "Selected concentration: " + concentration, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AlertActivity.this, "Please select a drug first", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Do nothing
            }
        });

        // Set up Oral/Topical Spinner
        ArrayAdapter<CharSequence> oralAdapter = ArrayAdapter.createFromResource(this, R.array.oral_options, android.R.layout.simple_spinner_item);
        oralAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        oralSpinner.setAdapter(oralAdapter);
        oralSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String dosageMethod = adapterView.getItemAtPosition(position).toString();
                Toast.makeText(AlertActivity.this, "Selected: " + dosageMethod, Toast.LENGTH_SHORT).show();
                selectedDrug.setDosage_method(dosageMethod); // Set dosage method in Drugs object
                if (dosageMethod.equals("Eye Drop") || dosageMethod.equals("Eye Ointment") || dosageMethod.equals("Eye Solution")) {
                    eyeSpinner.setVisibility(View.VISIBLE);
                } else {
                    eyeSpinner.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Do nothing
            }
        });

        // Set up Dose Spinner
        ArrayAdapter<CharSequence> doseAdapter = ArrayAdapter.createFromResource(this, R.array.dose_options, android.R.layout.simple_spinner_item);
        doseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        doseSpinner.setAdapter(doseAdapter);
        doseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String doseFrequency = adapterView.getItemAtPosition(position).toString();

                // Check if the selected item is numeric
                try {
                    int frequency = Integer.parseInt(doseFrequency.split(" ")[0]);
                    Toast.makeText(AlertActivity.this, "Selected frequency: " + frequency, Toast.LENGTH_SHORT).show();
                    selectedDrug.setFrequency(frequency); // Set frequency in Drugs object
                    selectedTimes.clear();
                    updateRecommendedText();
                } catch (NumberFormatException e) {
                    // Handle the case where the value is not a valid integer
                    Toast.makeText(AlertActivity.this, "Invalid frequency selected", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Do nothing
            }
        });

        // Set up Eye Spinner
        ArrayAdapter<CharSequence> eyeAdapter = ArrayAdapter.createFromResource(this, R.array.eye_options, android.R.layout.simple_spinner_item);
        eyeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        eyeSpinner.setAdapter(eyeAdapter);
        eyeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String eye = adapterView.getItemAtPosition(position).toString();
                Toast.makeText(AlertActivity.this, "Selected eye: " + eye, Toast.LENGTH_SHORT).show();
                // You can use this selection as needed
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Do nothing
            }
        });
    }

    private void showTimePickerDialog() {
        if (selectedTimes.size() < selectedDrug.getFrequency()) {
            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialog_time_picker);

            EditText hourEditText = dialog.findViewById(R.id.hour_edit_text);
            EditText minuteEditText = dialog.findViewById(R.id.minute_edit_text);
            Spinner amPmSpinner = dialog.findViewById(R.id.am_pm_spinner);
            Button setTimeButton = dialog.findViewById(R.id.set_time_button);

            // Set up AM/PM Spinner
            ArrayAdapter<CharSequence> amPmAdapter = ArrayAdapter.createFromResource(
                    this,
                    R.array.am_pm_options, // Define this in your res/values/strings.xml
                    android.R.layout.simple_spinner_item
            );
            amPmAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            amPmSpinner.setAdapter(amPmAdapter);

            setTimeButton.setOnClickListener(v -> {
                try {
                    int hour = Integer.parseInt(hourEditText.getText().toString());
                    int minute = Integer.parseInt(minuteEditText.getText().toString());
                    String amPm = amPmSpinner.getSelectedItem().toString();

                    if (amPm.equals("PM") && hour < 12) {
                        hour += 12; // Convert PM hours to 24-hour format
                    } else if (amPm.equals("AM") && hour == 12) {
                        hour = 0; // Midnight is 00:00
                    }

                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.HOUR_OF_DAY, hour);
                    calendar.set(Calendar.MINUTE, minute);
                    calendar.set(Calendar.SECOND, 0);

                    selectedTimes.add(calendar);
                    selectedTimeText.setText("Selected Time: " + hour + ":" + String.format("%02d", minute) + " " + amPm);
                    dialog.dismiss();
                } catch (NumberFormatException e) {
                    Toast.makeText(this, "Please enter valid numbers for hour and minute", Toast.LENGTH_SHORT).show();
                }
            });

            dialog.show();
        } else {
            Toast.makeText(this, "You have already selected all required times", Toast.LENGTH_SHORT).show();
        }
    }



    private void setAlarms() {
        if (selectedTimes.size() < selectedDrug.getFrequency()) {
            Toast.makeText(this, "Please select all required times", Toast.LENGTH_SHORT).show();
            return;
        }

        selectedDrug.setSelectedTimes(selectedTimes); // Set selected times in Drugs object

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        for (int i = 0; i < selectedTimes.size(); i++) {
            Calendar calendar = selectedTimes.get(i);
            Intent intent = new Intent(this, AlarmReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, i, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }

        Toast.makeText(this, "Alarms set", Toast.LENGTH_SHORT).show();
    }

    private void updateRecommendedText() {
        TextView recommendedText = findViewById(R.id.recommendation_text);
        switch (selectedDrug.getFrequency()) {
            case 1:
                recommendedText.setText("Recommended: Once daily");
                break;
            case 2:
                recommendedText.setText("Recommended: Every 12 hours");
                break;
            case 3:
                recommendedText.setText("Recommended: Every 8 hours");
                break;
            case 4:
                recommendedText.setText("Recommended: Every 6 hours");
                break;
            case 5:
                recommendedText.setText("Recommended: Every 5 hours");
                break;
            default:
                recommendedText.setText("");
        }
    }
}
