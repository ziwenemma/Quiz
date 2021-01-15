package com.example.quiz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;

public class Contactus extends AppCompatActivity {
    EditText et_subject,et_message;
    Button btn;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactus);

        et_subject=findViewById(R.id.email_subject);
        et_message=findViewById(R.id.email_message);
        btn = findViewById(R.id.email_send);





        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subject = et_subject.getText().toString().trim();
                String message = et_message.getText().toString().trim();
                String email = "contactQUIZ@gmail.com";


                if(subject.isEmpty()){
                    Toast.makeText(Contactus.this, "Please add a Subject", Toast.LENGTH_SHORT).show();

                }
                else if(message.isEmpty()){
                    Toast.makeText(Contactus.this, "Please add a Message", Toast.LENGTH_SHORT).show();
                }
                else{
                    String   mail= "mailto:" + email +
                            "?&subject=" + Uri.encode(subject) +
                            "&body=" + Uri.encode(message);


                    Intent intent =new Intent(Intent.ACTION_SENDTO);
                    intent.setData(Uri.parse(mail));

                    try{
                        startActivity(Intent.createChooser(intent, "Send Email.."));

                    }
                    catch(Exception e){
                        Toast.makeText(Contactus.this, "Exception: " +e.getMessage(),Toast.LENGTH_SHORT).show();
                    }


                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.contactus) {
            Intent myintent = new Intent(Contactus.this, Contactus.class);
            startActivity(myintent);
        }
        return super.onOptionsItemSelected(item);
    }
}