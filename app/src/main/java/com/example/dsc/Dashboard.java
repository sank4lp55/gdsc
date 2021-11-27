package com.example.dsc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Dashboard extends AppCompatActivity {
    FirebaseDatabase database;
    FirebaseAuth auth;
 ArrayList<com.example.dsc.postModel> postlist=new ArrayList<>();

    public void myprofile(View view)
    {
        Intent intent=new Intent(Dashboard.this, com.example.dsc.profiledashboard.class);
        startActivity(intent);
    }


    public void gouserlist(View view)
    {
        Intent intent=new Intent(Dashboard.this, com.example.dsc.showuserlist.class);
        startActivity(intent);
    }
    public void gotoaddpost(View view)
    {
        Intent intent=new Intent(Dashboard.this, com.example.dsc.addPost.class);
        startActivity(intent);
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        database=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();
      com.example.dsc.postAdapter adapter=new com.example.dsc.postAdapter(getBaseContext(),postlist);
        ImageView profiletooll=(ImageView)findViewById(R.id.profiletool);



        RecyclerView recyclerView= (RecyclerView) findViewById(R.id.postRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

     database.getReference().child("posts").addValueEventListener(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot snapshot) {

             for(DataSnapshot dataSnapshot:snapshot.getChildren())
             {
                 com.example.dsc.postModel post=dataSnapshot.getValue(com.example.dsc.postModel.class);
                 postlist.add(post);


             }
             adapter.notifyDataSetChanged();
         }

         @Override
         public void onCancelled(@NonNull DatabaseError error) {

         }
     });
        database.getReference().child("users").child(auth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    com.example.dsc.UserModel user =snapshot.getValue(com.example.dsc.UserModel.class);

                    Picasso.get().load(user.getProfilePhoto()).placeholder(R.drawable.empty).into(profiletooll);



                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}
/*
package com.example.dsc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }
}*/