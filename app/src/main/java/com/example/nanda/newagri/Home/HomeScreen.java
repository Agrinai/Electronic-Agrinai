package com.example.nanda.newagri.Home;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.CardView;

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

import com.example.nanda.newagri.BuyorSell.BuyorSell;
import com.example.nanda.newagri.MapActivity;
import com.example.nanda.newagri.R;
import com.example.nanda.newagri.User.UserScreen;
import com.example.nanda.newagri.Zero;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HomeScreen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TextView tv;
    CircleImageView cim;
    CardView SellCard,MarketCard, TransactionCard,PaymentCard,MapCard;
    String UserName,UserPhone,Useremail,UserAddress,Userpropic;
    String useridd,useriddd,SendUserID,userName,nss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        tv = (TextView) findViewById(R.id.tv);
        cim = (CircleImageView) findViewById(R.id.cirimg);
        SellCard=(CardView)findViewById(R.id.Sell_card);
        MarketCard=(CardView)findViewById(R.id.Market_card);
        TransactionCard=(CardView)findViewById(R.id.Transaction_card);
        PaymentCard=(CardView)findViewById(R.id.MyPayment_card);
        MapCard=(CardView)findViewById(R.id.Map_card);

        //Get UserData From SharedPref//////////
        SharedPreferences userpic = getSharedPreferences("userpropic", Context.MODE_PRIVATE);
            String picstring = userpic.getString("pic", "");
            String username=userpic.getString("username", "");
            if(picstring.length() !=0){
                Picasso.with(this).load(picstring).into(cim);
            }
            if(username.length() !=0){
                tv.setText(""+username);
            }


        SharedPreferences sp = getSharedPreferences("loggeduser", Context.MODE_PRIVATE);
            useriddd = sp.getString("userid", "");
            userName = sp.getString("name", "");
        SharedPreferences sp1 = getSharedPreferences("user", Context.MODE_PRIVATE);
            useridd = sp1.getString("userid", "");
            nss = sp1.getString("name", "");

        if (useriddd.length() != 0) {
            SendUserID = useriddd;
        }
        if (useridd.length() != 0) {
            SendUserID = useridd;
        }

        if(username.toString().trim().length() !=0) {
            tv.setText(""+username);
        }
        else{
            if (userName.toString().trim().length() == 0) {
                tv.setText("" + nss);

            }
            if (nss.toString().trim().length() == 0) {
                tv.setText("" + userName);
            }
        }
        //Get UserData From SharedPref//////////

        //Get User Details Server Call//////////
        JSONObject json=new JSONObject();
        try {
            json.put("_id", SendUserID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            getProfilePost(json.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Get User Details Server Call///////////////////

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeScreen.this, UserScreen.class);
                startActivity(i);
            }
        });
        cim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeScreen.this, UserScreen.class);
                startActivity(i);
            }
        });

        SellCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(HomeScreen.this, BuyorSell.class);
                startActivity(a);
            }
        });
        MarketCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(),"ModuleModule is OnProcess",Toast.LENGTH_LONG).show();
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
        MapCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(HomeScreen.this, MapActivity.class);
                startActivity(i);
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

        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle("agrinai");
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbarLayout.setTitle(" ");//carefull there should a space between double quote otherwise it wont work
                    isShow = false;
                }
            }
        });
    }
    void getProfilePost(String postBody) throws IOException {
        String postUrl = "https://agrinai.herokuapp.com/agri/v1/User/getProfile";
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(JSON, postBody);

        final Request request = new Request.Builder()
                .url(postUrl)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(okhttp3.Call call, final Response response) throws IOException {
                final String myRes=response.body().string();
                // Log.d("Page :96",response.body().string());
                HomeScreen.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject json= null;
                        try {
                            json = new JSONObject(myRes);
                            JSONObject obj = json.getJSONObject("data");
                            UserName = obj.getString("name");
                            UserPhone=obj.getString("emailorphone");
                            Useremail=obj.getString("emailid");
                            UserAddress=obj.getString("address");
                            Userpropic=obj.getString("profilepic");

                            SharedPreferences sp = getSharedPreferences("userpropic", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("pic", Userpropic);
                            editor.putString("userphone",UserPhone);
                            editor.putString("useremail",Useremail);
                            editor.putString("useraddress",UserAddress);
                            editor.putString("username",UserName);
                            Toast.makeText(getBaseContext(),""+userName,Toast.LENGTH_LONG).show();
                            editor.apply();

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if(Userpropic.length() !=0){
                                        Picasso.with(HomeScreen.this).load(Userpropic).into(cim);
                                    }
                                    if(UserName.length() !=0){
                                        tv.setText(""+UserName);
                                    }
                                }
                            });


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
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
            SharedPreferences ssp2 = getSharedPreferences("userpropic", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor2 = ssp2.edit();
            editor2.clear();
            editor2.commit();
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
