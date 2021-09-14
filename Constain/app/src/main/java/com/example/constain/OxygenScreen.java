package com.example.constain;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class OxygenScreen extends AppCompatActivity {
    TextView amount;
    CardView order;
    ImageView home, notifs, chatBot, back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oxygen_screen);
        amount = findViewById(R.id.AmountOxygenScreen);
        order = findViewById(R.id.OrderOxygenScreen);
        home = findViewById(R.id.homePageOxygenScreen);
        notifs = findViewById(R.id.notifsOxygenScreen);
        chatBot = findViewById(R.id.chatBotOxygenScreen);
        back = findViewById(R.id.backOxygenScreen);
        notifs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OxygenScreen.this, NotificationsScreen.class));
                finish();
            }
        });
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OxygenScreen.this, MapsActivity.class));
                finish();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OxygenScreen.this, HomePage.class));
                finish();
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OxygenScreen.this, HomePage.class));
                finish();
            }
        });
        chatBot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(OxygenScreen.this, MainActivity.class));
                finish();
            }
        });
    }
}