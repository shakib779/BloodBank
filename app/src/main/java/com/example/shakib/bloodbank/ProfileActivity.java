package com.example.shakib.bloodbank;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

import static android.text.TextUtils.*;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;

    private Button buttonBack;

    private DatabaseReference databaseReference;

    private EditText editTextName, editTextBloodGroup, editTextDistrict, editTextUpazilla, editTextMobile, editTextAge, editTextHeightFeet, editTextHeightInch, editTextWeight;
    private Button buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth = FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference();


        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextBloodGroup = (EditText) findViewById(R.id.editTextBloodGroup);
        editTextDistrict = (EditText) findViewById(R.id.editTextDistrict);
        editTextUpazilla = (EditText) findViewById(R.id.editTextUpazilla);
        editTextMobile = (EditText) findViewById(R.id. editTextMobile);

        editTextAge = (EditText) findViewById(R.id.editTextAge);
        editTextHeightFeet = (EditText) findViewById(R.id.editTextHeightFeet);
        editTextHeightInch = (EditText) findViewById(R.id.editTextHeightInch );
        editTextWeight = (EditText) findViewById(R.id. editTextWeight);


        buttonSave = (Button) findViewById(R.id.buttonSave);

        FirebaseUser user = firebaseAuth.getCurrentUser();

        buttonBack = (Button) findViewById(R.id.buttonBack);

        buttonBack.setOnClickListener(this);
        buttonSave.setOnClickListener(this);
    }

    private void saveUserInfromation(){

        FirebaseUser user = firebaseAuth.getCurrentUser();

        databaseReference = FirebaseDatabase.getInstance().getReference(user.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String preName, preBloodGroup, preMobile, preDistrict, preUpazilla;
                int preWeight, preAge, preBmi, preHeightFeet, preHeightInch, heightInch, heightFeet, age, weight;

                preName = dataSnapshot.child("name").getValue().toString();
                preBloodGroup = dataSnapshot.child("bloodGroup").getValue().toString();
                preMobile = dataSnapshot.child("mobile").getValue().toString();
                preDistrict = dataSnapshot.child("district").getValue().toString();
                preUpazilla = dataSnapshot.child("upazilla").getValue().toString();
                preWeight = Integer.parseInt(dataSnapshot.child("weight").getValue().toString());
                preAge = Integer.parseInt(dataSnapshot.child("age").getValue().toString());
                preHeightFeet = Integer.parseInt(dataSnapshot.child("heightFeet").getValue().toString());
                preHeightInch = Integer.parseInt(dataSnapshot.child("heightInch").getValue().toString());

                String name = editTextName.getText().toString().trim();
                String bloodGroup = editTextBloodGroup.getText().toString().trim();
                String district = editTextDistrict.getText().toString().trim();
                String upazilla = editTextUpazilla.getText().toString().trim();
                String mobile = editTextMobile.getText().toString().trim();

                if(isEmpty(name)){
                    name = preName;
                }

                if(isEmpty(bloodGroup)){
                    bloodGroup = preBloodGroup;
                }

                if(isEmpty(district)){
                    district = preDistrict;
                }

                if(isEmpty(upazilla)){
                    upazilla = preUpazilla;
                }

                if(isEmpty(mobile)){
                   mobile = preMobile;
                }

                if(isEmpty(editTextHeightInch.getText().toString())){
                    heightInch = preHeightInch;
                }
                else{
                    heightInch = Integer.parseInt(editTextHeightInch.getText().toString());
                }

                if(isEmpty(editTextHeightFeet.getText().toString())){
                    heightFeet = preHeightFeet;
                }
                else{
                    heightFeet = Integer.parseInt(editTextHeightFeet.getText().toString());
                }

                if(isEmpty(editTextWeight.getText().toString())){
                    weight = preWeight;
                }
                else{
                    weight = Integer.parseInt(editTextWeight.getText().toString());
                }
                if(isEmpty(editTextAge.getText().toString())){
                    age = preAge;
                }
                else{
                    age = Integer.parseInt(editTextAge.getText().toString());
                }

                double bmi;
                if(heightFeet != 0 || heightInch != 0)
                    bmi = (weight*1.0)/((((heightFeet*12.0 + heightInch*1.0)*2.54)/100.0)*(((heightFeet*12.0 + heightInch*1.0)*2.54)/100.0));
                else
                    bmi = 0.0;
                boolean availability;
                if(bmi >= 18.0 && age >= 18)
                    availability = true;
                else
                    availability = false;

                UserInfromation userInfromation = new UserInfromation(name, age, bloodGroup, heightFeet, heightInch, weight, district, upazilla, mobile, bmi, availability);

                FirebaseUser user = firebaseAuth.getCurrentUser();
                databaseReference.setValue(userInfromation);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public void onClick(View view){
        if(view == buttonBack){
            finish();
            startActivity(new Intent(getApplicationContext(), UserActivity.class));
        }

        if(view == buttonSave){
            saveUserInfromation();
            finish();
            startActivity(new Intent(getApplicationContext(), UserActivity.class));
        }
    }
}
