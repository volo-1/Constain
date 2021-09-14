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

public class FoodScreen extends AppCompatActivity {
    TextView amount;
    CardView order;
    ImageView home, notifs, chatBot, back;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_screen);

        amount = findViewById(R.id.AmountFoodScreen);
        order = findViewById(R.id.OrderFoodScreen);
        home = findViewById(R.id.homePageFoodScreen);
        notifs = findViewById(R.id.notifsFoodScreen);
        chatBot = findViewById(R.id.chatBotFoodScreen);
        back = findViewById(R.id.backFoodScreen);
        order.setEnabled(false);

        firestore.collection("Users")
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        User user = documentSnapshot.toObject(User.class);
                        int height = Integer.parseInt(user.getHeight());
                        String heightUnit = user.getHeightUnit();
                        switch (heightUnit){
                            case "centimeter":
                                height/=100;
                                break;
                            case "meters":
                                break;
                            case "Feet":
                                height/=3.281;
                                break;
                            case "inches":
                                height/=39.37;
                                break;
                        }
                        int bmi = Integer.parseInt(user.getWeight())/(height*height);
                        if(bmi<20){
                            amount.setText("2000 kcal/day");
                        }
                        else if(bmi<22){
                            amount.setText("2200 kcal/day");
                        }
                        else if(bmi<24){
                            amount.setText("2400 kcal/day");
                        }
                        else if(bmi<26){
                            amount.setText("2600 kcal/day");
                        }
                        else if(bmi>=26){
                            amount.setText("2800 kcal/day");
                        }
                        order.setEnabled(true);
                    }
                });
        notifs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FoodScreen.this, NotificationsScreen.class));
                finish();
            }
        });
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FoodScreen.this, MapsActivity.class));
                finish();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FoodScreen.this, HomePage.class));
                finish();
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FoodScreen.this, HomePage.class));
                finish();
            }
        });
        chatBot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(FoodScreen.this, MainActivity.class));
                finish();
            }
        });
    }
}