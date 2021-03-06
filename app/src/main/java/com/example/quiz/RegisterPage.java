package com.example.quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;

import java.util.HashMap;
import java.util.Map;

public class RegisterPage extends AppCompatActivity {
    EditText username, mEmail, mPassword, mConfirm;
    Button signupButton;
    Button loginButton;
    FirebaseAuth fAuth;
    public static final String TAG = "TAG";
    FirebaseFirestore fStore;
    String userID;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        username = findViewById(R.id.username);
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        signupButton = (Button) findViewById(R.id.signupButton);
        mConfirm = findViewById(R.id.confirmPass);

        loginButton = (Button) findViewById(R.id.loginButton);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        mAuth=FirebaseAuth.getInstance();


        signupButton.setOnClickListener(new View.OnClickListener() {

                                            @Override
                                            public void onClick(View v) {
                                                final String email = mEmail.getText().toString().trim();
                                                String password = mPassword.getText().toString().trim();
                                                final String fullName = username.getText().toString();
                                                String comfirmPass = mConfirm.getText().toString().trim();


                                                if (TextUtils.isEmpty(email)) {
                                                    mEmail.setError("Email is Required.");
                                                    return;
                                                }
                                                if (TextUtils.isEmpty(password)) {
                                                    mPassword.setError("password is required.");
                                                    return;
                                                }
                                                if (password.length() < 6) {
                                                    mPassword.setError("password mush be more than 6 characters");
                                                    return;
                                                }
                                                if (password.compareTo(comfirmPass) != 0) {
                                                    mConfirm.setError("password are not same at two times");
                                                    return;
                                                }


                                                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                                                    @Override
                                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                                        if (task.isSuccessful()) {
                                                            Toast.makeText(RegisterPage.this, "User Created", Toast.LENGTH_LONG).show();
                                                            FirebaseUser fuser = fAuth.getCurrentUser();

                                                            fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {

                                                                @Override
                                                                public void onSuccess(Void aVoid) {

                                                                    DbQuery.loadCategories(new MyCompleteListener() {
                                                                        @Override
                                                                        public void onSuccess() {
                                                                            Intent intent = new Intent(RegisterPage.this,MainPage.class);
                                                                            startActivity(intent);
                                                                            finish();
                                                                        }

                                                                        @Override
                                                                        public void onFailure() {
                                                                            Toast.makeText(RegisterPage.this, "Error!" ,Toast.LENGTH_LONG).show();

                                                                        }
                                                                    });



                                                                    Toast.makeText(RegisterPage.this, "Verification Email Has been Sent.", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }).addOnFailureListener(new OnFailureListener() {

                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                                                    Log.d(TAG, "onFailure: Email not sent " + e.getMessage());
                                                                }
                                                            });


                                                            DbQuery.createUserData(email, fullName, new MyCompleteListener() {

                                                                @Override
                                                                public void onSuccess() {

                                                                }

                                                                @Override
                                                                public void onFailure() {

                                                                }
                                                            });


                                                            userID = fAuth.getCurrentUser().getUid();
                                                            DocumentReference documentReference = fStore.collection("users").document(userID);

                                                            Map<String, Object> user = new HashMap<>();
                                                            user.put("NAME", fullName);
                                                            user.put("EMAIL_ID", email);
                                                            user.put("password", comfirmPass);
                                                            user.put("TOTAL_SCORE",0);


                                                            DocumentReference userDo = fStore.collection("USERS").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                                            WriteBatch batch =fStore.batch();


                                                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {

                                                                @Override
                                                                public void onSuccess(Void aVoid) {
                                                                    Log.d(TAG, "onSuccess: user Profile is created for " + userID);
                                                                }
                                                            }).addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                                                    Log.d(TAG, "onFailure: " + e.toString());
                                                                }
                                                            });
                                                            startActivity(new Intent(getApplicationContext(), MainPage.class));

                                                        } else {
                                                            Toast.makeText(RegisterPage.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                                        }
                                                    }
                                                });


                                            }
                                        });





        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginPage.class));
            }
        });
    }

}
