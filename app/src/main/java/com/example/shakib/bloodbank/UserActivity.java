package com.example.shakib.bloodbank;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.jar.Attributes;

public class UserActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;

    private TextView textViewName, textViewAge, textViewBloodGroup, textViewMobile, textViewDistrict, textViewUpazilla, textViewAvailability, textViewHeight, textViewWeight, textViewEmail, textViewBmi;
    private Button buttonLogout, buttonEditProfile, buttonSearch, buttonDelete, buttonAbout;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();

        textViewName = (TextView) findViewById(R.id.textViewName);
        textViewEmail = (TextView) findViewById(R.id.textViewEmail);
        textViewAge = (TextView) findViewById(R.id.textViewAge);
        textViewBloodGroup = (TextView) findViewById(R.id.textViewBloodGroup);
        textViewMobile = (TextView) findViewById(R.id.textViewMobile);
        textViewDistrict = (TextView) findViewById(R.id.textViewDistrict);
        textViewUpazilla = (TextView) findViewById(R.id.textViewUpazilla);
        textViewHeight = (TextView) findViewById(R.id.textViewHeight);
        textViewWeight = (TextView) findViewById(R.id.textViewWeight);
        textViewBmi = (TextView) findViewById(R.id.textViewBmi);
        textViewAvailability = (TextView) findViewById(R.id.textViewAvailability);

        textViewEmail.setText("Email : " + user.getEmail());

        databaseReference = FirebaseDatabase.getInstance().getReference(user.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String nam = dataSnapshot.child("name").getValue().toString();
                textViewName.setText("Name : " + nam);

                nam = dataSnapshot.child("bloodGroup").getValue().toString();
                textViewBloodGroup.setText("Blood Group : " + nam);

                nam = dataSnapshot.child("mobile").getValue().toString();
                textViewMobile.setText("Contact No. : " + nam);

                nam = dataSnapshot.child("district").getValue().toString();
                textViewDistrict.setText("District : " + nam);

                nam = dataSnapshot.child("upazilla").getValue().toString();
                textViewUpazilla.setText("Upazilla : " + nam);

                nam = dataSnapshot.child("weight").getValue().toString();
                textViewWeight.setText("Weight : " + nam);

                int a = Integer.parseInt(dataSnapshot.child("age").getValue().toString());
                textViewAge.setText("Age : " + a);

                int feet = Integer.parseInt(dataSnapshot.child("heightFeet").getValue().toString());
                int inch = Integer.parseInt(dataSnapshot.child("heightInch").getValue().toString());
                textViewHeight.setText("Height : " + feet + "'" + inch + "''");

                double bmi = Double.parseDouble(dataSnapshot.child("bmi").getValue().toString());
                int b = (int) (bmi*100.0);
                bmi = (b*1.0)/100.0;
                textViewBmi.setText("BMI : " + bmi);

                if(a < 18 || bmi < 18.0){
                    textViewAvailability.setText("Not available for blood donation");
                }
                else{
                    textViewAvailability.setText("Available for blood donation");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        buttonAbout = (Button) findViewById(R.id.buttonAbout);
        buttonLogout = (Button) findViewById(R.id.buttonLogout);
        buttonSearch = (Button) findViewById(R.id.buttonSearch);
        buttonEditProfile = (Button) findViewById(R.id.buttonEditProfile);
        buttonDelete = (Button) findViewById(R.id.buttonDelete);

        buttonLogout.setOnClickListener(this);
        buttonAbout.setOnClickListener(this);
        buttonEditProfile.setOnClickListener(this);
        buttonSearch.setOnClickListener(this);
        buttonDelete.setOnClickListener(this);
    }

    public void onClick(View view){
        if(view == buttonLogout){
            firebaseAuth.signOut();
            finishAffinity();
            startActivity(new Intent(this, LoginActivity.class));
        }

        if(view == buttonEditProfile){
            //finish();
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        }

        if(view == buttonSearch){
            //finish();
            startActivity(new Intent(getApplicationContext(), SearchboxActivity.class));
        }

        if(view == buttonDelete){
            //finish();
            startActivity(new Intent(getApplicationContext(), DeleteActivity.class));
        }

        if(view == buttonAbout){
            //finish();
            startActivity(new Intent(getApplicationContext(), AboutActivity.class));
        }
    }
}
