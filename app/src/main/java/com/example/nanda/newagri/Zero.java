package com.example.nanda.newagri;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.nanda.newagri.Home.HomeScreen;
import com.example.nanda.newagri.LogIn.Login;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Lokesh on 15-08-2017.
 */
public class Zero extends AppCompatActivity {
    String userName,UserName1;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zero);
        progressDialog = new ProgressDialog(Zero.this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIndeterminate(false);
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        progressDialog.dismiss();
        SharedPreferences sp1 = getSharedPreferences("loggeduser", Context.MODE_PRIVATE);
        SharedPreferences sp = getSharedPreferences("user", Context.MODE_PRIVATE);
        userName = sp.getString("newemailidorphonenumber", "");
        UserName1 = sp1.getString("name", "");

        if (userName.trim().length() == 0 || UserName1.trim().length() == 0) {

            Intent a = new Intent(Zero.this, Login.class);
            startActivity(a);
        }
        if (UserName1.trim().length() != 0) {
            Intent a = new Intent(Zero.this, HomeScreen.class);
            startActivity(a);
        }

        if (userName.trim().length() != 0) {
            Intent a = new Intent(Zero.this, HomeScreen.class);
            startActivity(a);
        }
    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        finishAffinity();
        System.exit(0);
    }
}

