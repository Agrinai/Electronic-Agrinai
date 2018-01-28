package com.example.nanda.newagri.Home;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.nanda.newagri.Buy.Buy;
import com.example.nanda.newagri.MapActivity;
import com.example.nanda.newagri.R;
import com.example.nanda.newagri.Sell.Sell;
import com.example.nanda.newagri.User.UserProfile;
import com.example.nanda.newagri.Weather;
import com.example.nanda.newagri.Zero;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeScreen extends AppCompatActivity {
    TextView tv;
    CircleImageView cim;
    ProgressDialog progressDialog;
    Button bbuy,bsell,blogout,weather,map;

    String useridd,useriddd,SendUserID,userName,nss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);
        tv = (TextView) findViewById(R.id.tv);
        cim = (CircleImageView) findViewById(R.id.cirimg);
        bbuy = (Button) findViewById(R.id.bbuy);
        bsell = (Button) findViewById(R.id.bsell);
        blogout=(Button)findViewById(R.id.blogout);
        weather= (Button) findViewById(R.id.weather);
        map=(Button) findViewById(R.id.map);

        SharedPreferences userpic = getSharedPreferences("userpropic", Context.MODE_PRIVATE);
        String check = userpic.getString("check", "");



            SharedPreferences sp = getSharedPreferences("loggeduser", Context.MODE_PRIVATE);
            useriddd = sp.getString("userid", "");
            SharedPreferences sp1 = getSharedPreferences("user", Context.MODE_PRIVATE);
            useridd = sp1.getString("userid", "");

            if (useriddd.length() != 0) {
                SendUserID = useriddd;
            }
            if (useridd.length() != 0) {
                SendUserID = useridd;
            }

            //new getProfile(""+SendUserID).execute();
       /* SharedPreferences sp3 = getSharedPreferences("checkcheck", Context.MODE_PRIVATE);
        String check1=sp3.getString("check","");
        if(check1.equals("true")){
            new getProfile(""+SendUserID).execute();

        }*/
            String picstring = userpic.getString("pic", "");
            if (picstring.length() != 0) {
                byte[] img = picstring.getBytes();
                byte[] decodedBytes = Base64.decode(img, 0);
                Bitmap picbitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
                cim.setImageBitmap(picbitmap);
            }

            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(HomeScreen.this, UserProfile.class);
                    startActivity(i);
                }
            });
            cim.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(HomeScreen.this, UserProfile.class);
                    startActivity(i);
                }
            });


            SharedPreferences spp = getSharedPreferences("loggeduser", Context.MODE_PRIVATE);
            userName = spp.getString("name", "");
            useriddd = spp.getString("userid", "");
            SharedPreferences spp1 = getSharedPreferences("user", Context.MODE_PRIVATE);
            nss = spp1.getString("name", "");
            if (userName.toString().trim().length() == 0) {
                tv.setText("" + nss);

            }
            if (nss.toString().trim().length() == 0) {
                tv.setText("" + userName);
            }


            bbuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent a = new Intent(HomeScreen.this, Buy.class);
                    startActivity(a);
                }
            });
            bsell.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent a = new Intent(HomeScreen.this, Sell.class);
                    startActivity(a);
                }
            });
            weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(HomeScreen.this, Weather.class);
                startActivity(a);
                }
        });
            map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(HomeScreen.this, MapActivity.class);
                startActivity(a);
                }
        });


            blogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences sp = getSharedPreferences("loggeduser", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.clear();
                    editor.commit();
                    SharedPreferences sp1 = getSharedPreferences("user", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor1 = sp1.edit();
                    editor1.clear();
                    editor1.commit();
                    Intent i = new Intent(HomeScreen.this, Zero.class);
                    startActivity(i);
                }
            });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
        System.exit(0);
    }
}
