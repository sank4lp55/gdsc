package com.example.dsc;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class showuserlist extends AppCompatActivity {
    ArrayList<com.example.dsc.UserModel> list=new ArrayList<>();
    FirebaseAuth auth;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        userAdapter adapter=new userAdapter(getBaseContext(),list);

        setContentView(R.layout.activity_showuserlist);
        RecyclerView recyclerView= (RecyclerView) findViewById(R.id.userRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(adapter);
        database.getReference().child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    com.example.dsc.UserModel userModel=dataSnapshot.getValue(com.example.dsc.UserModel.class);
                    userModel.setUserId(dataSnapshot.getKey());
                    list.add(userModel);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}