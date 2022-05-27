package com.example.madpractical6;

import static java.lang.Math.abs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Random;

public class ListActivity extends AppCompatActivity {
    DBHandler dbHandler = new DBHandler(this, null, null, 1);
    ArrayList<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        userList = dbHandler.getUsers();

        //Create sample data for the database
        if (userList.size() == 0) {
            for (int i = 1; i <= 20; i++){
                User user = new User();
                user.setName("Name" + String.valueOf(new Random().nextInt()));
                user.setDescription("Description " + String.valueOf(new Random().nextInt()));
                user.setId(i);
                user.setFollowed(false);

                dbHandler.addUser(user);
                userList.add(user);
            }
        }

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        Adapter myAdapter = new Adapter(dbHandler.getUsers());
        LinearLayoutManager myLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(myLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(myAdapter);
    }
}