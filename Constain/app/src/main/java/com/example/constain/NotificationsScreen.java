package com.example.constain;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NotificationsScreen extends AppCompatActivity {

    CardView notification;
    ImageView home, chatBot;
    TextView time;
    long timeLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications_screen);

        time = findViewById(R.id.timeNotifs);
        home = findViewById(R.id.homeNotificationsPage);
        chatBot = findViewById(R.id.chatNotificationsPage);
        notification = findViewById(R.id.notificationCardView);
        FirebaseFirestore.getInstance().collection("Users")
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.toObject(User.class).getLastOrder() > 0) {
                            notification.setVisibility(View.VISIBLE);
                        }
                        long time1 = documentSnapshot.toObject(User.class).getLastOrder();
                        Calendar calendar = Calendar.getInstance();
                        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        calendar.setTimeInMillis(time1);
                        time.setText(formatter.format(calendar.getTime()));
                    }
                });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NotificationsScreen.this, HomePage.class));
                finish();
            }
        });
    }
}