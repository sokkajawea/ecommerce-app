package com.example.kwesijames;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class adminDash extends AppCompatActivity {

    public ArrayList<category> categories = new ArrayList<>();
    dbConnect db=new dbConnect(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dash);

        loadCategories();

        RecyclerView rvCategories = findViewById(R.id.rvCategories);
        RecyclerViewClass rv = new RecyclerViewClass(categories, getSupportFragmentManager());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvCategories.setLayoutManager(linearLayoutManager);
        rvCategories.setAdapter(rv);

        Intent usernameGet = getIntent();
        String username = usernameGet.getStringExtra("username");

        Button btnProducts,btnCategories;
        TextView txvLoginName, txvLogout;

        btnProducts=findViewById(R.id.btnProducts);
        btnCategories=findViewById(R.id.btnCategories);

        txvLoginName = findViewById(R.id.txvLoginName);
        txvLogout = findViewById(R.id.txvLogout);

        btnCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, AdminCatFrag.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit();
            }
        });

        btnProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, AdminProdFrag.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit();
            }
        });

        txvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(adminDash.this, MainActivity.class);
                startActivity(i);

            }
        });

        txvLoginName.setText(username);

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