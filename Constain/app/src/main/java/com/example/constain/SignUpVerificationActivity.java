package com.example.constain;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class SignUpVerificationActivity extends AppCompatActivity {

    private final int PICK_IMAGE_REQUEST = 0;
    private Button done;
    private ImageView upload, choice;
    private String name, mob, pass, email, height, weight, age, heightUnit, gender;
    private TextView helloText;
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
    boolean chosen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_verification);
        //Get the name pass email and mob from prev screen
        email = getIntent().getStringExtra(getString(R.string.intentEmail));
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        name = getIntent().getStringExtra(getString(R.string.intentName));
        mob = getIntent().getStringExtra(getString(R.string.intentMobileNumber));
        pass = getIntent().getStringExtra(getString(R.string.intentPassword));
        height = getIntent().getStringExtra(getString(R.string.intentHeight));
        weight = getIntent().getStringExtra(getString(R.string.intentWeight));
        age = getIntent().getStringExtra(getString(R.string.intentAge));
        heightUnit = getIntent().getStringExtra(getString(R.string.intentHeightUnit));
        gender = getIntent().getStringExtra(getString(R.string.intentGender));
        //Initialize variables
        helloText = findViewById(R.id.NameVerification);
        choice = findViewById(R.id.choice);
        done = findViewById(R.id.doneVerification);
        upload = findViewById(R.id.chooseProofVerification);
        helloText.setText("Hi, " + name);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choose();
            }
        });
        choice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choose();
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chosen) {
                    createAccount();
                } else
                    Toast.makeText(SignUpVerificationActivity.this, "Please choose proof for validation", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createAccount() {
        User user = new User(name, mob, pass, email, height, weight, age, heightUnit, gender);
        auth.createUserWithEmailAndPassword(email, pass)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        firestore.collection("Users")
                                .document(Objects.requireNonNull(auth.getCurrentUser()).getUid())
                                .set(user);
                        System.out.println("nvailrenvauir");
                        Intent intent = new Intent(SignUpVerificationActivity.this, HomePage.class);
                        done.setEnabled(true);
                        upload.setEnabled(true);
                        choice.setEnabled(true);
                        helloText.setEnabled(true);
                        startActivity(intent);
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("320849i");
                        Toast.makeText(SignUpVerificationActivity.this,"invalid email/password\nPlease restart the app", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void choose() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            Uri u = data.getData();
            Picasso.with(this).load(u).into(choice);
            chosen = true;
            upload.setVisibility(View.INVISIBLE);
        }
    }
}