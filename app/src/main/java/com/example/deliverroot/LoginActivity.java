package com.example.deliverroot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText memail, mpassword;
    private Button loginbtn1;
    TextView signup;
    private FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        memail = findViewById(R.id.email);
        mpassword = findViewById(R.id.password);
        signup=findViewById(R.id.signup);
        loginbtn1=findViewById(R.id.loginBtn);

        fAuth = FirebaseAuth.getInstance();
        loginbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login_user();
            }
        });


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });

    }


    private void login_user() {

        String email = memail.getText().toString();
        String password = mpassword.getText().toString();

        if (TextUtils.isEmpty(email)) {
            memail.setError("Email is required");
            return;
        }

        if (TextUtils.isEmpty(password)) {
           mpassword.setError("Password is required");
            return;

        }
        if(password.length()<6){
            mpassword.setError("password-error");

        }
        fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "Logged in Successfully", Toast.LENGTH_LONG).show();
                    Intent intent
                            = new Intent(LoginActivity.this,
                            HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(LoginActivity.this,
                            "Error:"+task.getException().getMessage(),
                            Toast.LENGTH_LONG)
                            .show();
                }

            }
        });

    }

}



