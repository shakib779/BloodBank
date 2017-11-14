package com.example.shakib.bloodbank;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class SearchboxActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnSearch;
    private Button buttonBack;
    private EditText searchBlood;
    private EditText searchDistrict;
    private EditText searchUpazilla;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchbox);

        buttonBack = (Button) findViewById(R.id.buttonBack);
        btnSearch = (Button) findViewById(R.id.btnSearch);

        searchBlood = (EditText) findViewById(R.id.searchBlood);
        searchDistrict = (EditText) findViewById(R.id.searchDistrict);
        searchUpazilla = (EditText) findViewById(R.id.searchUpazilla);

        buttonBack.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
    }

    public void onClick(View view){
        if(view == buttonBack){
            finish();
            startActivity(new Intent(this, UserActivity.class));
        }

        if(view == btnSearch){
            String bloodGroup = searchBlood.getText().toString().trim();
            String district = searchDistrict.getText().toString().trim();
            String upazilla = searchUpazilla.getText().toString().trim();

            if(TextUtils.isEmpty(bloodGroup)){
                bloodGroup = "NULL";
            }

            if(TextUtils.isEmpty(district)){
                district = "NULL";
            }

            if(TextUtils.isEmpty(upazilla)){
                upazilla = "NULL";
            }
            Intent intent = new Intent(SearchboxActivity.this, UserlistActivity.class);

            intent.putExtra( "bg", bloodGroup);
            intent.putExtra( "ds", district);
            intent.putExtra( "up", upazilla);

            //finish();
            startActivity(intent);
        }
    }

}
