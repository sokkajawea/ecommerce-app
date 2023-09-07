package com.example.kwesijames;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class register extends AppCompatActivity {

    EditText edtPassword, edtFirstname, edtLastname, edtUsername;
    Button btnRegister;
    TextView txvBackToLogin;

    dbConnect db = new dbConnect(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtFirstname=findViewById(R.id.edtFirstname);
        edtLastname=findViewById(R.id.edtLastname);
        edtPassword=findViewById(R.id.edtPassword);
        edtUsername=findViewById(R.id.edtUsername);

        btnRegister=findViewById(R.id.btnRegister);

        txvBackToLogin=findViewById(R.id.txvBackToLogin);

        txvBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(register.this, MainActivity.class);
                startActivity(i);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String strFirstname = edtFirstname.getText().toString();
                String strLastname = edtLastname.getText().toString();
                String strUsername = edtUsername.getText().toString();
                String strPassword = edtPassword.getText().toString();

                if(strFirstname.isEmpty()&&strLastname.isEmpty()&&strUsername.isEmpty()&&strPassword.isEmpty()){

                    Toast.makeText(register.this, R.string.regErrormsg, Toast.LENGTH_SHORT).show();

                } else if (db.isUsernameTaken(strUsername)) {
                    Toast.makeText(register.this, "Username taken, please chose a different one!", Toast.LENGTH_SHORT).show();
                    
                } else{

                    users u1 = new users(strFirstname, strLastname, strUsername, strPassword);
                    db.addUser(u1);
                    Toast.makeText(register.this, "Congratulations, you are successfully registered!", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(register.this, MainActivity.class);
                    i.putExtra("username", strUsername);
                    i.putExtra("password", strPassword);
                    startActivity(i);
                }

            }
        });

    }
}