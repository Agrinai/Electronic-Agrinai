package com.example.nanda.newagri.Sell;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nanda.newagri.Home.HomeScreen;
import com.example.nanda.newagri.NamesList;
import com.example.nanda.newagri.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Sell extends AppCompatActivity {
    AutoCompleteTextView idproname;
    NamesList namelist = new NamesList();
    ArrayAdapter<String> ad, spinnerData;
    Spinner typeSpinner;
    String[] Types = {"Select Product Category", "Vegetables", "Fruits", "Seeds"};
    String product_name, kilo, prize;
    TextView text4;
    EditText idkilo, idprize;
    Button submit, matches, warehouse;

    String useridd, useriddd, SendUserID;
    ProgressDialog progressDialog;
    String[] PN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);
        typeSpinner = (Spinner) findViewById(R.id.typeSpinner);
        spinnerData = new ArrayAdapter<String>(Sell.this, R.layout.spinnercategory, Types);
        typeSpinner.setAdapter(spinnerData);
        idproname = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);


        idkilo = (EditText) findViewById(R.id.editText2);
        idprize = (EditText) findViewById(R.id.editText3);
        submit = (Button) findViewById(R.id.button);
        warehouse = (Button) findViewById(R.id.warehouse);


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
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        idproname.setText("");
                        break;
                    case 1:
                        idproname.setText("");
                        String[] vlist = namelist.VegList();
                        ad = new ArrayAdapter<String>(Sell.this, R.layout.spinnercategory, vlist);
                        idproname.setAdapter(ad);
                        idproname.setThreshold(1);
                        break;
                    case 2:
                        idproname.setText("");
                        String[] flist = namelist.FruitList();
                        ad = new ArrayAdapter<String>(Sell.this, R.layout.spinnercategory, flist);
                        idproname.setAdapter(ad);
                        idproname.setThreshold(1);
                        break;
                    case 3:
                        idproname.setText("");
                        String[] slist = namelist.SeedList();
                        ad = new ArrayAdapter<String>(Sell.this, R.layout.spinnercategory, slist);
                        idproname.setAdapter(ad);
                        idproname.setThreshold(1);
                        break;


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getBaseContext(), "Please Select Category ", Toast.LENGTH_SHORT).show();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

                if ((idproname.getText().toString().trim().length() != 0) && (idkilo.getText().toString().trim().length() != 0) && (idprize.getText().toString().trim().length() != 0)) {
                    product_name = idproname.getText().toString().toLowerCase();
                    kilo = idkilo.getText().toString();
                    prize = idprize.getText().toString();
                    JSONObject json=new JSONObject();
                    try {
                        json.put("UserId", SendUserID);
                        json.put("VegName", product_name);
                        json.put("VegKG", kilo);
                        json.put("VegPrice", prize);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        saveProduct(json.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    if (idproname.getText().toString().trim().length() == 0) {
                        Toast.makeText(getApplicationContext(), "Please Enter Product Name", Toast.LENGTH_SHORT).show();
                    }
                    if (idkilo.getText().toString().trim().length() == 0) {
                        Toast.makeText(getApplicationContext(), "Please Enter Product KG", Toast.LENGTH_SHORT).show();
                    }
                    if (idprize.getText().toString().trim().length() == 0) {
                        Toast.makeText(getApplicationContext(), "Please Enter Product Prize", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });
        warehouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Sell.this, dialogSell.class);
                i.putExtra("buyorsell", "Sell");
                startActivity(i);
                /*Dialog dialog=new Dialog(Sell.this);

                dialog.setContentView(R.layout.activity_dialog_sale);
                dialog.setCancelable(true);
                dialog.show();*/
            }
        });


    }

    ////////////////ServerCall FOR POST VEG//////////////////////////

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(Sell.this, HomeScreen.class);
        startActivity(i);
    }
    void saveProduct(String postBody) throws IOException {
        String postUrl = "https://agrinai.herokuapp.com/agri/v1/Sell/saveVeg";
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(JSON, postBody);

        final Request request = new Request.Builder()
                .url(postUrl)
                .post(body)
                .build();
        progressDialog = new ProgressDialog(Sell.this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIndeterminate(false);
        progressDialog.setMessage("Please Wait");
        progressDialog.show();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(okhttp3.Call call, final Response response) throws IOException {
                Log.d("Page :96", response.body().string());
                Sell.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        Toast.makeText(Sell.this, "YOUR SELL PROFILE IS UPDATED", Toast.LENGTH_LONG).show();
                        idproname.setText("");
                        idkilo.setText("");
                        idprize.setText("");
                    }
                });
            }
        });
    }

}


