package com.example.nanda.newagri.User;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nanda.newagri.GetPermission;
import com.example.nanda.newagri.Home.HomeScreen;
import com.example.nanda.newagri.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;


public class UserScreen extends AppCompatActivity {
    ImageView cim;
    ProgressDialog progressDialog;
    private static int RESULT_LOAD_IMAGE = 1;
    TextView edname, edphno, edmailid, edaddress;
    String UserName, UserPhone, Useremail, UserAddress, Userpropic, StringUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userscreen);

        askForPermission(Manifest.permission.READ_EXTERNAL_STORAGE,0);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(UserScreen.this,UserEditScreen.class);
                startActivity(i);
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });
        edname = (TextView) findViewById(R.id.nametext);
        edmailid = (TextView) findViewById(R.id.emailid);
        edaddress = (TextView) findViewById(R.id.address);
        edphno = (TextView) findViewById(R.id.mobilenumber);
        cim = (ImageView) findViewById(R.id.profileimgview);

        SharedPreferences user = getSharedPreferences("userpropic", Context.MODE_PRIVATE);
            String picstring = user.getString("pic", "");
            UserName = user.getString("username", "");
            Useremail = user.getString("useremail", "");
            UserAddress = user.getString("useraddress", "");
            UserPhone = user.getString("userphone", "");
        if(UserName.length()!=0){
            Picasso.with(this).load(picstring).into(cim);
            edname.setText("" + UserName);
            edphno.setText("" + UserPhone);
            edmailid.setText("" + Useremail);
            edaddress.setText("" + UserAddress);
        }



    }
    private void askForPermission(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(UserScreen.this, permission) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(UserScreen.this, permission)) {
                ActivityCompat.requestPermissions(UserScreen.this, new String[]{permission}, requestCode);
            } else {
                ActivityCompat.requestPermissions(UserScreen.this, new String[]{permission}, requestCode);

            }
        } else {

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(UserScreen.this, HomeScreen.class);
        startActivity(i);
    }
}
