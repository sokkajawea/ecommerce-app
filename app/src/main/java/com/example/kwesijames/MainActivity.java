package com.example.kwesijames;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Blob;
import java.sql.Date;

public class MainActivity extends AppCompatActivity {

    EditText edtUsername, edtPassword;
    Button btnLogin;
    TextView txvRegister;
    dbConnect db = new dbConnect(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        txvRegister = findViewById(R.id.txvRegister);

        Intent received = getIntent();
        String username = received.getStringExtra("username");
        String password = received.getStringExtra("password");

        if (username != null && password != null) {
            edtUsername.setText(username);
            edtPassword.setText(password);
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strUsername = edtUsername.getText().toString();
                String strPassword = edtPassword.getText().toString();

                if (strUsername.isEmpty() && strPassword.isEmpty()) {
                    Toast.makeText(MainActivity.this, R.string.loginFieldsError, Toast.LENGTH_SHORT).show();
                } else if (strUsername.equals("admin") && strPassword.equals("admin")) {
                    Intent i = new Intent(MainActivity.this, adminDash.class);
                    i.putExtra("username", strUsername);
                    startActivity(i);
                } else {
                    users u1 = new users(strUsername, strPassword);
                    boolean results = db.checkUser(u1);
                    if (results) {
                        Toast.makeText(MainActivity.this, getString(R.string.loginWelcome) + " " + strUsername + "!", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(MainActivity.this, HomePage.class);
                        i.putExtra("username", strUsername);
                        startActivity(i);
                    } else {
                        Toast.makeText(MainActivity.this, getString(R.string.loginInputError), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        txvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, register.class);
                startActivity(i);
            }
        });
    }
}