package com.example.nanda.newagri.TransactionHistory;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nanda.newagri.Home.HomeScreen;
import com.example.nanda.newagri.R;

public class BuySellTransaction extends AppCompatActivity {
    String vegnameStr,usernameStr;
    Integer selectedKG,totalprice;
    TextView sellername,productname,productkg,productprice;
    CardView cardfortrans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buysell_transaction);

        productname = (TextView) findViewById(R.id.productname);
        productkg = (TextView) findViewById(R.id.productkg);
        productprice = (TextView) findViewById(R.id.productprice);
        cardfortrans = (CardView) findViewById(R.id.cardfortrans);


        /*vegnameStr=getIntent().getStringExtra("vegnameStr");
        usernameStr=getIntent().getStringExtra("sellername");
        selectedKG=getIntent().getIntExtra("selectedKG",0);
        totalprice=getIntent().getIntExtra("totalprice",0);*/

            /*if(vegnameStr.toString().trim().length()==0){
                Toast.makeText(getBaseContext(), ""+vegnameStr, Toast.LENGTH_SHORT).show();*/

        SharedPreferences sp = getSharedPreferences("transaction", Context.MODE_PRIVATE);
        String vegname = sp.getString("vegnameStr", "");
        if (vegname.toString().trim().length() != 0) {
            Integer Kg = sp.getInt("selectedKG", 0);
            Integer kgg = Integer.parseInt(Kg.toString());
            Integer Price = sp.getInt("totalprice", 0);
            Integer PPrice = Integer.parseInt(Price.toString());
            productname.setText("" + vegname);
            productkg.setText("" + kgg + " KG");
            productprice.setText("" + PPrice + ".0₹");
            //}

         /*   productname.setText(""+vegnameStr);
            productkg.setText(""+selectedKG+" KG");
            productprice.setText(""+totalprice+".0₹");*/
        } else {
            /*cardfortrans.setVisibility(View.VISIBLE);
            *//*productname.setText("");
            productkg.setText("");
            productprice.setText("");*/
            AlertDialog.Builder alert = new AlertDialog.Builder(BuySellTransaction.this);
            alert.setMessage("No Records");
            alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    Intent j = new Intent(BuySellTransaction.this, HomeScreen.class);
                    startActivity(j);
                }
            });
            alert.show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(BuySellTransaction.this, HomeScreen.class);
        startActivity(i);
    }
}
