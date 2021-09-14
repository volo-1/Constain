package com.example.constain;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.motion.utils.StopLogic;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.interpolator.view.animation.FastOutLinearInInterpolator;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;
import androidx.vectordrawable.graphics.drawable.PathInterpolatorCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.PathInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private TextView amountWater, amountFood;
    private CardView order;
    private ImageView minimize, basket, back;
    private boolean isOrg = true;
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    ConstraintSet org = new ConstraintSet();
    ConstraintSet update = new ConstraintSet();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        org.clone((ConstraintLayout) findViewById(R.id.mapOrg));
        update.clone(this, R.layout.map_activity_alt);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        back = findViewById(R.id.backMapScreen);
        mapFragment.getMapAsync(this);
        amountWater = findViewById(R.id.AmountWaterMap);
        minimize = findViewById(R.id.minimizeMap);
        basket = findViewById(R.id.basket);
        amountFood = findViewById(R.id.AmountFoodMap);
        order = findViewById(R.id.OrderMap);
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
                            amountWater.setText("50 litres/day");
                        } else if (weight < 80) {
                            amountWater.setText("60 litres/day");
                        } else if (weight < 100) {
                            amountWater.setText("70 litres/day");
                        } else if (weight < 120) {
                            amountWater.setText("80 litres/day");
                        } else if (weight < 140) {
                            amountWater.setText("90 litres/day");
                        } else if (weight >= 140) {
                            amountWater.setText("100 litres/day");
                        }
                        order.setEnabled(true);
                    }
                });
        firestore.collection("Users")
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        User user = documentSnapshot.toObject(User.class);
                        int height = Integer.parseInt(user.getHeight());
                        String heightUnit = user.getHeightUnit();
                        switch (heightUnit) {
                            case "centimeter":
                                height /= 100;
                                break;
                            case "meters":
                                break;
                            case "Feet":
                                height /= 3.281;
                                break;
                            case "inches":
                                height /= 39.37;
                                break;
                        }
                        int bmi = Integer.parseInt(user.getWeight()) / (height * height);
                        if (bmi < 20) {
                            amountFood.setText("2000 kcal/day");
                        } else if (bmi < 22) {
                            amountFood.setText("2200 kcal/day");
                        } else if (bmi < 24) {
                            amountFood.setText("2400 kcal/day");
                        } else if (bmi < 26) {
                            amountFood.setText("2600 kcal/day");
                        } else if (bmi >= 26) {
                            amountFood.setText("2800 kcal/day");
                        }
                        order.setEnabled(true);
                    }
                });
        minimize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Transition transition = new ChangeBounds();
                transition.setInterpolator(new OvershootInterpolator());
                transition.setDuration(1000);

                //AnticipateOvershoot, Overshoot, accelerate decelerate, accelerate, anticipate, base, bounce, cycle, declerate, interpolator, linear, path
                //time, fastoutlinear, fastoutslow, linearoutslow, motion, pathcompat, stoplogic

                TransitionManager.beginDelayedTransition(findViewById(R.id.mapOrg), transition);
                if (isOrg) {
                    update.applyTo(findViewById(R.id.mapOrg));
                    isOrg = false;
                } else {
                    org.applyTo(findViewById(R.id.mapOrg));
                    isOrg = true;
                }
            }
        });
        basket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Transition transition = new ChangeBounds();
                transition.setInterpolator(new OvershootInterpolator());
                transition.setDuration(1000);


                TransitionManager.beginDelayedTransition(findViewById(R.id.mapOrg), transition);
                if (isOrg) {
                    update.applyTo(findViewById(R.id.mapOrg));
                    isOrg = false;
                } else {
                    org.applyTo(findViewById(R.id.mapOrg));
                    isOrg = true;
                }
            }
        });
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                order.setEnabled(false);
                firestore.collection("Users")
                        .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .update("lastOrder", System.currentTimeMillis());
                startActivity(new Intent(MapsActivity.this, HomePage.class));
                finish();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MapsActivity.this, HomePage.class));
                finish();
            }
        });

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Please grant permission for location", Toast.LENGTH_SHORT).show();
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 100, 100, new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                LatLng sydney = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.addMarker(new MarkerOptions().position(sydney).title("Your Current position").icon(BitmapFromVector(getApplicationContext(), R.drawable.map_marker)));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 12.0f));

            }
        });
//        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//        LatLng sydney = new LatLng(location.getLatitude(), location.getLongitude());
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney").icon(BitmapFromVector(getApplicationContext(), R.drawable.map_marker)));
//        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 12.0f));
    }

    private BitmapDescriptor BitmapFromVector(Context context, int vectorResId) {
        // below line is use to generate a drawable.
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);

        // below line is use to set bounds to our vector drawable.
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());

        // below line is use to create a bitmap for our
        // drawable which we have added.
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);

        // below line is use to add bitmap in our canvas.
        Canvas canvas = new Canvas(bitmap);

        // below line is use to draw our
        // vector drawable in canvas.
        vectorDrawable.draw(canvas);

        // after generating our bitmap we are returning our bitmap.
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
}