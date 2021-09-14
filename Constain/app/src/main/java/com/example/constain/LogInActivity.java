package com.example.constain;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.regex.Pattern;

public class LogInActivity extends AppCompatActivity {

    private EditText email, pass;
    private ImageView back;
    private Button logIn;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        email = findViewById(R.id.LogInEmail);
        pass = findViewById(R.id.LogInPassword);
        back = findViewById(R.id.backLogIn);
        logIn = findViewById(R.id.logInLogIn);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LogInActivity.this, MainActivity.class));
                finish();
            }
        });
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    auth.signInWithEmailAndPassword(email.getText().toString(), pass.getText().toString())
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    startActivity(new Intent(LogInActivity.this, HomePage.class));
                                    finish();
                                }
                            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(LogInActivity.this, "invalid email/password", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private boolean validate() {
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
        //Password verification
        if (pass.getText().toString().isEmpty()) {
            pass.setError("Please enter a password");
            return false;
        }
        return true;
    }
}