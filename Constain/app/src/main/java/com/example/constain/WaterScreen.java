package com.example.constain;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.common.collect.Maps;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class WaterScreen extends AppCompatActivity {

    TextView amount;
    CardView order;
    ImageView home, notifs, chatBot, back;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_screen);

        amount = findViewById(R.id.AmountWaterScreen);
        order = findViewById(R.id.OrderWaterScreen);
        home = findViewById(R.id.homePageWaterScreen);
        notifs = findViewById(R.id.notifsWaterScreen);
        chatBot = findViewById(R.id.chatBotWaterScreen);
        back = findViewById(R.id.backWaterScreen);
        order.setEnabled(false);

        firestore.collection("Users")
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        User user = documentSnapshot.toObject(User.class);
                        int weight = Integer.parseInt(user.getWeight());
                        if (weight < 60) {
                            amount.setText("50 litres/day");
                        } else if (weight < 80) {
                            amount.setText("60 litres/day");
                        } else if (weight < 100) {
                            amount.setText("70 litres/day");
                        } else if (weight < 120) {
                            amount.setText("80 litres/day");
                        } else if (weight < 140) {
                            amount.setText("90 litres/day");
                        } else if (weight >= 140) {
                            amount.setText("100 litres/day");
                        }
                        order.setEnabled(true);
                    }
                });
        notifs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WaterScreen.this, NotificationsScreen.class));
                finish();
            }
        });
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WaterScreen.this, MapsActivity.class));
                finish();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WaterScreen.this, HomePage.class));
                finish();
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WaterScreen.this, HomePage.class));
                finish();
            }
        });
        chatBot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(WaterScreen.this, MainActivity.class));
                finish();
            }
        });
    }
}