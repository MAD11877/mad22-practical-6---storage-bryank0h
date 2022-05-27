package com.example.madpractical6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    ArrayList<UserLoginData> userLoginDataArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        readUsers();
        Button loginButton = findViewById(R.id.loginBtn);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText username = findViewById(R.id.editTextUsername);
                EditText password = findViewById(R.id.editTextPassword);

                if (ValidateUser(username.getText().toString(), password.getText().toString())) {
                    Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    Intent loginIntent = new Intent(LoginActivity.this, ListActivity.class);
                    startActivity(loginIntent);
                }
                else
                    Toast.makeText(LoginActivity.this, "Invalid Credentials! Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void readUsers() {
        DatabaseReference usersReference = FirebaseDatabase.getInstance("https://mad-practical-ff754-default-rtdb.asia-southeast1.firebasedatabase.app").getReference().child("Users");
        usersReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userLoginDataArrayList.clear();
                for (DataSnapshot user : dataSnapshot.getChildren()) {
                    UserLoginData userLoginData = user.getValue(UserLoginData.class);
                    userLoginDataArrayList.add(userLoginData);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });
    }

    public boolean ValidateUser(String username, String password) {
        for (UserLoginData user : userLoginDataArrayList) {
            if ((user.getUsername()).equals(username))
                if ((user.getPassword()).equals(password))
                    return true;
        }
        return false;
    }
}