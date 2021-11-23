package com.example.deliverroot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.WindowManager;

import com.google.firebase.FirebaseApiNotAvailableException;
import com.google.firebase.auth.FirebaseAuth;

public class SplashScreenActivity extends AppCompatActivity {
    private boolean authenticated=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        int SPLASH_TIME_OUT=5000;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences msharedp =getSharedPreferences("SharePref",MODE_PRIVATE);
                boolean isfirstTime=msharedp.getBoolean("firstime",true);
                if(isfirstTime){
                    startActivity(new Intent(SplashScreenActivity.this,IntroductoryActivity.class));
                    SharedPreferences.Editor editor = msharedp.edit();
                    editor.putBoolean("firstime",true);
                    editor.commit();
                }
                else{
                    if(FirebaseAuth.getInstance().getCurrentUser()!=null){
                        authenticated=true;
                    }
                    if(authenticated){
                        Intent intent = new Intent(SplashScreenActivity.this, HomeActivity.class);
                        startActivity(intent);
                        authenticated=true;
                    }
                    else{
                        Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                }
                finish();
            }
        },SPLASH_TIME_OUT);
    }


}
