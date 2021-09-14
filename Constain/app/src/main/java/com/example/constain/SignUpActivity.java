package com.example.constain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
    ImageView back;
    Button signUp;
    EditText name, email, mob, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signUp = findViewById(R.id.signUpSignUp);
        back = findViewById(R.id.backSignUp);
        name = findViewById(R.id.SignUpName);
        email = findViewById(R.id.SignUpEmail);
        mob = findViewById(R.id.SignUpMobileNumber);
        pass = findViewById(R.id.SignUpPassword);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                finish();
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    Intent intent = new Intent(SignUpActivity.this, SignUpDetailsActivity.class);
                    intent.putExtra(getString(R.string.intentName), name.getText().toString());
                    intent.putExtra(getString(R.string.intentMobileNumber), mob.getText().toString());
                    intent.putExtra(getString(R.string.intentEmail), email.getText().toString());
                    intent.putExtra(getString(R.string.intentPassword), pass.getText().toString());
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private boolean validate() {
        //Name verification
        if (name.getText().toString().length() < 3 || name.getText().toString().length() > 25) {
            name.setError("The name must be between 3 and 25 characters");
            return false;
        }
        //Email verification
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);
        if (email.getText().toString().isEmpty())
            return false;
        if (!pat.matcher(email.getText().toString()).matches()) {
            email.setError("Email is not valid");
            return false;
        }
        //Mobile number verification
        if (mob.getText().toString().length() != 10) {
            mob.setError("The phone number must be of 10 digits");
            return false;
        }
        //Password verification
        if (pass.getText().toString().length() > 30 || pass.getText().toString().length() < 6) {
            pass.setError("The password must be between 6 and 30 characters");
            return false;
        }
        return true;
    }
}