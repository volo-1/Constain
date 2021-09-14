package com.example.constain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SignUpDetailsActivity extends AppCompatActivity {

    Spinner gender, heightUnit;
    EditText age, weight, height;
    String email, name, mob, pass;
    Button continueBtn;
    TextView helloText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_details);

        //Get the name pass email and mob from prev screen
        email = getIntent().getStringExtra(getString(R.string.intentEmail));
        name = getIntent().getStringExtra(getString(R.string.intentName));
        mob = getIntent().getStringExtra(getString(R.string.intentMobileNumber));
        pass = getIntent().getStringExtra(getString(R.string.intentPassword));
        //initialize the variables
        helloText = findViewById(R.id.NameDetails);
        continueBtn = findViewById(R.id.continueDetails);
        age = findViewById(R.id.DetailsAge);
        weight = findViewById(R.id.DetailsWeight);
        height = findViewById(R.id.DetailsHeight);
        gender = findViewById(R.id.DetailsGender);
        heightUnit = findViewById(R.id.heightUnit);
        //Put the name in the hello text
        helloText.setText("Hi " + name);
        //Put values in spinners
        initializeSpinners();
        //Put a click listener on the spinners
        gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        heightUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //Select default option for spinners
        heightUnit.setSelection(0);
        gender.setSelection(0);
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    Intent intent = new Intent(SignUpDetailsActivity.this, SignUpVerificationActivity.class);
                    intent.putExtra(getString(R.string.intentName), name);
                    intent.putExtra(getString(R.string.intentMobileNumber), mob);
                    intent.putExtra(getString(R.string.intentEmail), email);
                    intent.putExtra(getString(R.string.intentPassword), pass);
                    intent.putExtra(getString(R.string.intentAge), age.getText().toString());
                    intent.putExtra(getString(R.string.intentWeight), weight.getText().toString());
                    intent.putExtra(getString(R.string.intentHeight), height.getText().toString());
                    if (gender.getSelectedItemPosition() == 1)
                        intent.putExtra(getString(R.string.intentGender), "Male");
                    else if (gender.getSelectedItemPosition() == 2)
                        intent.putExtra(getString(R.string.intentGender), "Female");
                    intent.putExtra(getString(R.string.intentHeightUnit), String.valueOf(heightUnit.getSelectedItem()));
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private boolean validate() {
        //age verification
        if (age.getText().toString().isEmpty()) {
            age.setError("Please enter your age");
            return false;
        }
        //weight verification
        if (weight.getText().toString().isEmpty()) {
            weight.setError("Please enter your weight");
            return false;
        }
        //height verification
        if (height.getText().toString().isEmpty()) {
            height.setError("Please enter your weight");
            return false;
        }
        //gender verification
        if (!(gender.getSelectedItemPosition() == 1 || gender.getSelectedItemPosition() == 2|| gender.getSelectedItemPosition() == 3)) {
            Toast.makeText(this, "Please enter your gender", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void initializeSpinners() {
        //gender spinner
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item);
        adapter1.add("Select your gender");
        adapter1.add("Male");
        adapter1.add("Female");
        adapter1.add("Rather not say");
        gender.setAdapter(adapter1);
        //height spinner
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item);
        adapter2.add("meters");
        adapter2.add("centimeter");
        adapter2.add("Feet");
        adapter2.add("inches");
        heightUnit.setAdapter(adapter2);
    }
}