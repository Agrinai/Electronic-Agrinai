package com.example.nanda.newagri.Agriculture;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.nanda.newagri.BuyorSell.BuyorSell;
import com.example.nanda.newagri.Home.HomeScreen;
import com.example.nanda.newagri.R;

public class Menu extends AppCompatActivity {

    private TextView mTextMessage;
    Fragment fragment;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //mTextMessage.setText("Home");
                    Intent i=new Intent(Menu.this,HomeScreen.class);
                    startActivity(i);
                    return true;

                case R.id.agriculture_home:
                    //mTextMessage.setText("Home");
                    fragment = new AgricultureScreen();
                    loadFragment(fragment);
                    return true;

                case R.id.navigation_dashboard:
                    //mTextMessage.setText("AgricultureScreen");
                    fragment = new NewsFeed();
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        loadFragment(new AgricultureScreen());
    }
    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(Menu.this, HomeScreen.class);
        startActivity(i);
    }

}
