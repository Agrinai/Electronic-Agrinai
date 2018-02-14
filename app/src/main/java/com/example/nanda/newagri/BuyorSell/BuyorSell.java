package com.example.nanda.newagri.BuyorSell;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.nanda.newagri.Home.HomeScreen;
import com.example.nanda.newagri.R;

public class BuyorSell extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buyorcell_activity);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager1(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_shopping_basket_28dp);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_shopping_cart_28dp);

    }

    private void setupViewPager1(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Buy(), "Buy");
        adapter.addFragment(new Sell(), "Sell");
        viewPager.setAdapter(adapter);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(BuyorSell.this, HomeScreen.class);
        startActivity(i);
    }



}