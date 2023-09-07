package com.example.kwesijames;

import static androidx.core.content.ContentProviderCompat.requireContext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class HomePage extends AppCompatActivity {

    public ArrayList<category> categories = new ArrayList<>();
    dbConnect db=new dbConnect(this);

    private String user;


    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        loadCategories();

        RecyclerView rvCategories = findViewById(R.id.rvCategories);
        RecyclerViewClass rv = new RecyclerViewClass(categories, getSupportFragmentManager());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvCategories.setLayoutManager(linearLayoutManager);
        rvCategories.setAdapter(rv);



        Intent usernameGet = getIntent();
        String username = usernameGet.getStringExtra("username");

        Button btnHome, btnProfile, btnCart;
        ImageView logo, login;
        TextView txvLoginName, txvLogout;

        btnProfile=findViewById(R.id.btnProfile);
        btnHome=findViewById(R.id.btnHome);
        btnCart=findViewById(R.id.btnCart);

        logo=findViewById(R.id.logo);
        login=findViewById(R.id.login);

        txvLoginName = findViewById(R.id.txvLoginName);
        txvLogout = findViewById(R.id.txvLogout);

        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, HomeFrag.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit();
            }
        });


        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, HomeFrag.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit();
            }
        });

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                CartFrag cartFragment = (CartFrag) fragmentManager.findFragmentByTag("CartFrag");

                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, CartFrag.class, null)
                        .setReorderingAllowed(true)
                       .addToBackStack(null)
                       .commit();
            }
        });

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                ProfileFrag profileFragment = ProfileFrag.newInstance(username, null);
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, profileFragment)
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit();
            }
        });


        txvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(HomePage.this, MainActivity.class);
                startActivity(i);

            }
        });

        Cursor userLogged = db.getUserData(username);

        if (userLogged.moveToFirst()) {
           user = userLogged.getString(userLogged.getColumnIndex("Username"));
        }
        txvLoginName.setText(user);

    }




    private void loadCategories() {
        categories.clear();
        Cursor cursor = db.getCategories();

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(dbConnect.catID));
                @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex(dbConnect.catName));
                category cat = new category(id, title);
                categories.add(cat);
            } while (cursor.moveToNext());
        }

        cursor.close();
    }
}