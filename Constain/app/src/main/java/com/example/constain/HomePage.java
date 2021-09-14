package com.example.constain;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.type.LatLng;

import java.util.concurrent.TimeUnit;

public class HomePage extends AppCompatActivity {

    private ConstraintLayout layout;
    private ImageView home, notifs, chatBot, waterImage, oxygenImage, foodImage;
    private CardView orderAll;
    private TextView helloText, timeLeftTextView;
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
    private DocumentReference UserReference;
    private User user;
    private ProgressBar timeTillNextOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        waterImage = findViewById(R.id.waterImage);
        oxygenImage = findViewById(R.id.oxygenImage);
        home = findViewById(R.id.homeHomePage);
        notifs = findViewById(R.id.notificationHomePage);
        timeTillNextOrder = findViewById(R.id.progressBarHomePage);
        chatBot = findViewById(R.id.chatHomePage);
        timeLeftTextView = findViewById(R.id.timeLeftHomePage);
        orderAll = findViewById(R.id.OrderAllHomePage);
        helloText = findViewById(R.id.NameHomePage);
        foodImage = findViewById(R.id.foodImage);
        layout = findViewById(R.id.HomePageId);
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        UserReference = firestore.collection("Users")
                .document(auth.getCurrentUser().getUid());
        UserReference.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        user = documentSnapshot.toObject(User.class);
                        helloText.setText("Hi, " + user.getName());
                        startTimerThread();
                    }
                });
        notifs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomePage.this, NotificationsScreen.class));
                finish();
            }
        });
        foodImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomePage.this, FoodScreen.class));
                finish();
            }
        });
        oxygenImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomePage.this, OxygenScreen.class));
                finish();
            }
        });
        waterImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomePage.this, WaterScreen.class));
                finish();
            }
        });
        orderAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomePage.this, MapsActivity.class));

                finish();
            }
        });
        chatBot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(HomePage.this, MainActivity.class));
                finish();
            }
        });
    }

    private void startTimerThread() {
        long last = user.getLastOrder();
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            private long startTime = System.currentTimeMillis();

            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handler.post(new Runnable() {
                        @SuppressLint("DefaultLocale")
                        public void run() {
                            long timeLeft = (long) (8.64e+7 - (System.currentTimeMillis() - last));
                            System.out.println(timeLeft);
                            if (0 >= timeLeft) {
                                timeLeftTextView.setText("You can order now!");
                                timeTillNextOrder.setProgress(100);
                                orderAll.setEnabled(true);
                                foodImage.setEnabled(true);
                                oxygenImage.setEnabled(true);
                                waterImage.setEnabled(true);
                                findViewById(R.id.textView4).setVisibility(View.INVISIBLE);
                            } else {
                                long min = (timeLeft / 60000);
                                long hour = (long) (timeLeft / 3.6e+6);
                                min -= hour * 60;
                                String timeLeftstr = hour
                                        + " hrs "
                                        + min
                                        + " mins";
                                timeLeftTextView.setText(timeLeftstr);
                                timeTillNextOrder.setProgress((int) (((System.currentTimeMillis() - last) / 8.64e+7) * 100));
                                orderAll.setEnabled(false);
                                foodImage.setEnabled(false);
                                oxygenImage.setEnabled(false);
                                waterImage.setEnabled(false);
                            }
                        }
                    });
                }
            }
        };
        new Thread(runnable).start();
    }
}