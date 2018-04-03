package com.example.nanda.newagri;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.nanda.newagri.LogIn.Login;
import com.example.nanda.newagri.LogIn.SetUserLocation;

public class GetPermission extends AppCompatActivity {
    private final int REQUEST_PERMISSION_PHONE_STATE=1;
    String userId,userName,check;
    int checkForPersmission;
    Switch location,storage,telephone;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_permission);
        location=(Switch)findViewById(R.id.locationSwitch);
        storage=(Switch)findViewById(R.id.storageSwitch);
        telephone=(Switch)findViewById(R.id.telephoneSwitch);
        save=(Button)findViewById(R.id.bsave);

        userId=getIntent().getStringExtra("userId");
        userName=getIntent().getStringExtra("userName");
        check=getIntent().getStringExtra("check");

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Intent i=new Intent(GetPermission.this, SetUserLocation.class);
                    i.putExtra("userId",userId);
                    Log.d("UserId in perm",userId);
                    i.putExtra("userName", userName);
                    i.putExtra("check","l");
                    startActivity(i);
                }catch (Exception e){
                    Log.d("Exception in moving",e.toString());
                }

            }
        });

        if (ContextCompat.checkSelfPermission(GetPermission.this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            location.setChecked(true);
        }
        if (ContextCompat.checkSelfPermission(GetPermission.this,Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            storage.setChecked(true);
        }
        if (ContextCompat.checkSelfPermission(GetPermission.this,Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            telephone.setChecked(true);
        }

        location.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    Toast.makeText(getApplicationContext(),"location On",Toast.LENGTH_SHORT).show();
                    askForPermission(Manifest.permission.ACCESS_FINE_LOCATION,0);
                }else{
                    Toast.makeText(getApplicationContext(),"location OFF",Toast.LENGTH_SHORT).show();
                    askForPermission(Manifest.permission.ACCESS_FINE_LOCATION,0);
                }

            }
        });
        storage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    Toast.makeText(getApplicationContext(),"storage On",Toast.LENGTH_SHORT).show();
                    askForPermission(Manifest.permission.READ_EXTERNAL_STORAGE,0);
                }else{
                    Toast.makeText(getApplicationContext(),"storage OFF",Toast.LENGTH_SHORT).show();
                    askForPermission(Manifest.permission.READ_EXTERNAL_STORAGE,0);
                }
            }
        });
        telephone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    Toast.makeText(getApplicationContext(),"telephone On",Toast.LENGTH_SHORT).show();
                    askForPermission(Manifest.permission.READ_PHONE_STATE,0);
                }else{
                    Toast.makeText(getApplicationContext(),"telephone OFF",Toast.LENGTH_SHORT).show();
                    askForPermission(Manifest.permission.READ_PHONE_STATE,0);
                }
            }
        });
    }
    private void askForPermission(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(GetPermission.this, permission) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(GetPermission.this, permission)) {
                ActivityCompat.requestPermissions(GetPermission.this, new String[]{permission}, requestCode);

            } else {
                ActivityCompat.requestPermissions(GetPermission.this, new String[]{permission}, requestCode);

            }
        } else {

        }
    }

    private void checkAgain(String permission){
        if (ContextCompat.checkSelfPermission(GetPermission.this, permission) != PackageManager.PERMISSION_GRANTED) {
            Log.d("Permisson Not Granted","d");
        }
        else{
            Log.d("Permisson Granted","d");
            Intent i=new Intent(GetPermission.this, SetUserLocation.class);
            i.putExtra("userId",userId);
            i.putExtra("userName", userName);
            i.putExtra("check","l");
            startActivity(i);
        }
    }
}
