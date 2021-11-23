package com.example.deliverroot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private EditText user_email, user_password,user_name;
    private Button RegisterBtn;
    private TextView loginbtn1;
    private FirebaseAuth fAuth;
    private EditText user_phoneno;
    FirebaseDatabase database_root;
    DatabaseReference dRef;
    static int ID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

       fAuth = FirebaseAuth.getInstance();
       ID=0;

        user_name=findViewById(R.id.user_name);
        user_email = findViewById(R.id.user_email);
        user_password = findViewById(R.id.user_password);
//        user_password1 = findViewById(R.id.user_password1);
        RegisterBtn = findViewById(R.id.registerBtn1);
        user_phoneno=findViewById(R.id.user_phoneno);

        loginbtn1=findViewById(R.id.loginBtn1);

        loginbtn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });


        RegisterBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                registerNewUser();
            }
        });

    }

    private void registerNewUser() {
        final String Name = user_name.getText().toString();
        final String email = user_email.getText().toString();
        final String password = user_password.getText().toString();
        final String phoneno = user_phoneno.getText().toString();


        if (TextUtils.isEmpty(email)) {
            user_email.setError("Email is required");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            user_password.setError("Password is required");
            return;

        }
        if(password.length()<6){
            user_password.setError("password-error");
        }

        database_root=FirebaseDatabase.getInstance();
        dRef=database_root.getReference("users");


        final UserClass user= new UserClass(Name, email,password,phoneno);


        fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),
                            "Registration successful!",
                            Toast.LENGTH_LONG)
                            .show();

                    String  c=fAuth.getCurrentUser().getUid();
                    dRef.child(c).setValue(user);

                    Intent intent
                            = new Intent(RegisterActivity.this,
                            LoginActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(RegisterActivity.this,
                            "Error:"+task.getException().getMessage(),
                            Toast.LENGTH_LONG)
                            .show();
                }

            }
        });


    }

}
