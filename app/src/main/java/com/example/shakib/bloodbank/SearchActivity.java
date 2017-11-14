package com.example.shakib.bloodbank;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
   // private ListView mUserList;
   private TextView textViewName, textViewAge, textViewBloodGroup, textViewMobile, textViewDistrict, textViewUpazilla, textViewAvailability, textViewHeight, textViewWeight, textViewEmail, textViewBmi;

    private DatabaseReference databaseReference;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl("https://blood-bank-9dc57.firebaseio.com/");

        //mUserList.setAdapter(arrayAdapter);

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

        Bundle bundle= getIntent().getExtras();
        final String userId = bundle.getString( "uId");
        //textViewName.setText("Name : " + userId);

        databaseReference = FirebaseDatabase.getInstance().getReference(userId);
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

    }
}
