package com.example.shakib.bloodbank;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserlistActivity extends AppCompatActivity {
    private TextView tvShow;
    private DatabaseReference mDatabase;
    private ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlist);

        list = (ListView) findViewById( R.id.list);

        Bundle bundle= getIntent().getExtras();

        String bb = bundle.getString( "bg").toUpperCase();
        String dd =  bundle.getString( "ds").toUpperCase();
        String uu = bundle.getString( "up").toUpperCase();
        if(bb.equals("NULL"))
            bb = "";
        if(dd.equals("NULL")){
            dd = "";
        }
        if(uu.equals("NULL")){
            uu = "";
        }
        final String dis = dd;
        final String bld = bb;
        final String upa = uu;

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        final FirebaseUser user = firebaseAuth.getCurrentUser();
        final String cur_uid = user.getUid();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final ArrayList<String> userId = new ArrayList<String>();
                final ArrayList<String> userName = new ArrayList<String>();
                for(DataSnapshot userSnapshot: dataSnapshot.getChildren()){
                    String un = userSnapshot.getKey();
                    if(cur_uid.equals(un)){
                        continue;
                    }
                    else {
                        if (bld != "" && dis != "" && upa != "") {
                            if (userSnapshot.child("bloodGroup").getValue().toString().toUpperCase().equals(bld) && userSnapshot.child("district").getValue().toString().toUpperCase().equals(dis) && userSnapshot.child("upazilla").getValue().toString().toUpperCase().equals(upa) && userSnapshot.child("availability").getValue().toString().equals("true")) {
                                userId.add(un);
                                userName.add(userSnapshot.child("name").getValue().toString());
                            }
                        } else if (bld != "" && dis != "") {
                            if (userSnapshot.child("bloodGroup").getValue().toString().toUpperCase().equals(bld) && userSnapshot.child("district").getValue().toString().toUpperCase().equals(dis) && userSnapshot.child("availability").getValue().toString().equals("true")) {
                                userId.add(un);
                                userName.add(userSnapshot.child("name").getValue().toString());
                            }
                        } else if (dis != "" && upa != "") {
                            if (userSnapshot.child("district").getValue().toString().toUpperCase().equals(dis) && userSnapshot.child("upazilla").getValue().toString().toUpperCase().equals(upa) && userSnapshot.child("availability").getValue().toString().equals("true")) {
                                userId.add(un);
                                userName.add(userSnapshot.child("name").getValue().toString());
                            }
                        } else if (upa != "" && bld != "") {
                            if (userSnapshot.child("bloodGroup").getValue().toString().toUpperCase().equals(bld) && userSnapshot.child("upazilla").getValue().toString().toUpperCase().equals(upa) && userSnapshot.child("availability").getValue().toString().equals("true")) {
                                userId.add(un);
                                userName.add(userSnapshot.child("name").getValue().toString());
                            }
                        } else if (bld != "") {
                            if (userSnapshot.child("bloodGroup").getValue().toString().toUpperCase().equals(bld) && userSnapshot.child("availability").getValue().toString().equals("true")) {
                                userId.add(un);
                                userName.add(userSnapshot.child("name").getValue().toString());
                            }
                        } else if (dis != "") {
                            if (userSnapshot.child("district").getValue().toString().toUpperCase().equals(dis) && userSnapshot.child("availability").getValue().toString().equals("true")) {
                                userId.add(un);
                                userName.add(userSnapshot.child("name").getValue().toString());
                            }
                        } else if (upa != "") {
                            if (userSnapshot.child("upazilla").getValue().toString().toUpperCase().equals(upa) && userSnapshot.child("availability").getValue().toString().equals("true")) {
                                userId.add(un);
                                userName.add(userSnapshot.child("name").getValue().toString());
                            }
                        } else {
                            if (userSnapshot.child("availability").getValue().toString().equals("true")) {
                                userId.add(un);
                                userName.add(userSnapshot.child("name").getValue().toString());
                            }
                        }
                    }

                }

                int sz = userId.size();

                ArrayAdapter<String> adapter = new ArrayAdapter<String>( UserlistActivity.this, R.layout.sample_view, R.id.tv, userName);
                list.setAdapter( adapter );
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                        String value = userId.get(position);

                        Intent intent = new Intent(UserlistActivity.this,SearchActivity.class);
                        intent.putExtra( "uId", value);
                        //finish();
                        startActivity( intent );
                    }
                } );

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}