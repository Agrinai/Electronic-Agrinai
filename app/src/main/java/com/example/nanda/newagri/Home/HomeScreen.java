package com.example.nanda.newagri.Home;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Base64;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nanda.newagri.Buy.Buy;
import com.example.nanda.newagri.R;
import com.example.nanda.newagri.Sell.Sell;
import com.example.nanda.newagri.User.UserProfile;
import com.example.nanda.newagri.Zero;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeScreen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TextView tv;
    CircleImageView cim;
    ProgressDialog progressDialog;
    CardView BuyCard,SellCard, TransactionCard,PaymentCard;

    String useridd,useriddd,SendUserID,userName,nss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        tv = (TextView) findViewById(R.id.tv);
        cim = (CircleImageView) findViewById(R.id.cirimg);
        BuyCard=(CardView)findViewById(R.id.Buy_card);
        SellCard=(CardView)findViewById(R.id.Sell_card);
        TransactionCard=(CardView)findViewById(R.id.Transaction_card);
        PaymentCard=(CardView)findViewById(R.id.MyPayment_card);




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

        /*//new getProfile(""+SendUserID).execute();
        SharedPreferences sp3 = getSharedPreferences("checkcheck", Context.MODE_PRIVATE);
        String check1=sp3.getString("check","");
        if(check1.equals("true")){
            new getProfile(""+SendUserID).execute();

        }*/
        SharedPreferences userpic = getSharedPreferences("userpropic", Context.MODE_PRIVATE);
        String picstring = userpic.getString("pic", "");
        String picname=userpic.getString("picname","");
        if (picstring.length() != 0) {
            byte[] img = picstring.getBytes();
            byte[] decodedBytes = Base64.decode(img, 0);
            Bitmap picbitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
            cim.setImageBitmap(picbitmap);
        }

        SharedPreferences spp = getSharedPreferences("loggeduser", Context.MODE_PRIVATE);
        userName = spp.getString("name", "");
        useriddd = spp.getString("userid", "");
        SharedPreferences spp1 = getSharedPreferences("user", Context.MODE_PRIVATE);
        nss = spp1.getString("name", "");

        if(picname.toString().trim().length() !=0) {
            tv.setText(""+picname);
        }
        else{
            if (userName.toString().trim().length() == 0) {
                tv.setText("" + nss);

            }
            if (nss.toString().trim().length() == 0) {
                tv.setText("" + userName);
            }
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

        BuyCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(HomeScreen.this, Buy.class);
                startActivity(a);
            }
        });
        SellCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(HomeScreen.this, Sell.class);
                startActivity(a);
            }
        });
        TransactionCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(),"Transaction History Module is OnProcess",Toast.LENGTH_LONG).show();
            }
        });
        PaymentCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(),"My Payment Module is OnProcess",Toast.LENGTH_LONG).show();
            }
        });




        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            finishAffinity();
            System.exit(0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            return true;
        }
        if (id == R.id.Logout) {
            SharedPreferences ssp = getSharedPreferences("loggeduser", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = ssp.edit();
            editor.clear();
            editor.commit();
            SharedPreferences ssp1 = getSharedPreferences("user", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor1 = ssp1.edit();
            editor1.clear();
            editor1.commit();
            Intent i = new Intent(HomeScreen.this, Zero.class);
            startActivity(i);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {
            finishAffinity();
            System.exit(0);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
