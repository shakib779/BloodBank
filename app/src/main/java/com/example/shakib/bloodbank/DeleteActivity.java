package com.example.shakib.bloodbank;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DeleteActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonYes, buttonNo;
    private FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        buttonYes = (Button) findViewById(R.id.buttonYes);
        buttonNo = (Button) findViewById(R.id.buttonNo);

        buttonYes.setOnClickListener(this);
        buttonNo.setOnClickListener(this);
    }

    public void onClick(View view){
        if(view == buttonYes){
            firebaseAuth = FirebaseAuth.getInstance();
            FirebaseUser user = firebaseAuth.getInstance().getCurrentUser();

            if(user != null){
                final DatabaseReference db_node = FirebaseDatabase.getInstance().getReference().getRoot().child(user.getUid());

                user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Account Deleteted Successfully", Toast.LENGTH_LONG).show();
                            //db_node.removeValue();
                            finishAffinity();
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Account couldn't be deleted", Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        }

        if(view == buttonNo){
            finish();
            startActivity(new Intent(getApplicationContext(), UserActivity.class));
        }
    }
}
