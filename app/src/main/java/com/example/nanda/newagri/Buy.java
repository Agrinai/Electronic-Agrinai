package com.example.nanda.newagri;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Lokesh on 16-08-2017.
 */
public class Buy extends AppCompatActivity{
    AutoCompleteTextView idproname;

    NamesList namelist=new NamesList();
    String[] Types={"Select Product Category","Vegetables","Fruits","Seeds"};
    ArrayAdapter<String> ad, spinnerData;
    Spinner typeSpinner;
    ProgressDialog progressDialog;
    String product_name,kilo;
    TextView text4;
    EditText idkilo;
    Button submit,warehouse;
    ListView listView,veglist;
    String useridd,useriddd,SendUserID;
    String[] PN;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buy_layout);
        typeSpinner=(Spinner)findViewById(R.id.typeSpinner);
        spinnerData = new ArrayAdapter<String>(Buy.this, R.layout.spinnercategory,Types);
        typeSpinner.setAdapter(spinnerData);
        idproname=(AutoCompleteTextView)findViewById(R.id.autoCompleteTextView1);

        idkilo=(EditText)findViewById(R.id.editText2);
        submit=(Button)findViewById(R.id.button);
        warehouse=(Button)findViewById(R.id.warehouse);




        SharedPreferences sp = getSharedPreferences("loggeduser", Context.MODE_PRIVATE);
        useriddd = sp.getString("userid", "");
        SharedPreferences sp1 = getSharedPreferences("user", Context.MODE_PRIVATE);
        useridd = sp1.getString("userid", "");

        if(useriddd.length()!=0){
            SendUserID=useriddd;}
        if(useridd.length()!=0){
            SendUserID=useridd;}

        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:
                        idproname.setText("");
                        break;
                    case 1:
                        idproname.setText("");
                        String[] vlist=namelist.VegList();
                        ad=new ArrayAdapter<String>(Buy.this, R.layout.spinnercategory,vlist);
                        idproname.setAdapter(ad);
                        idproname.setThreshold(1);

                        break;
                    case 2:
                        idproname.setText("");
                        String[] flist=namelist.FruitList();
                        ad=new ArrayAdapter<String>(Buy.this, R.layout.spinnercategory,flist);
                        idproname.setAdapter(ad);
                        idproname.setThreshold(1);
                        break;
                    case 3:
                        idproname.setText("");
                        String[] slist=namelist.SeedList();
                        ad=new ArrayAdapter<String>(Buy.this, R.layout.spinnercategory,slist);
                        idproname.setAdapter(ad);
                        idproname.setThreshold(1);
                        break;


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getBaseContext(),"Please Select Category ",Toast.LENGTH_SHORT).show();
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

                if ((idproname.getText().toString().trim().length() != 0) && (idkilo.getText().toString().trim().length() != 0)) {
                    product_name = idproname.getText().toString().toLowerCase();
                    kilo = idkilo.getText().toString();
                    JSONObject json=new JSONObject();
                    try {
                        json.put("UserId", SendUserID);
                        json.put("VegName", product_name);
                        json.put("VegKG", kilo);

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
                }
            }
        });
        warehouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Buy.this,dialogBuy.class);
                startActivity(i);
                /*Dialog dialog=new Dialog(Sell.this);

                dialog.setContentView(R.layout.activity_dialog_sale);
                dialog.setCancelable(true);
                dialog.show();*/
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(Buy.this,HomeScreen.class);
        startActivity(i);
    }
    void saveProduct(String postBody) throws IOException {
        String postUrl = "https://agrinai.herokuapp.com/agri/v1/Buy/saveVeg";
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(JSON, postBody);

        final Request request = new Request.Builder()
                .url(postUrl)
                .post(body)
                .build();
        progressDialog = new ProgressDialog(Buy.this);
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
                Buy.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        Toast.makeText(Buy.this, "YOUR BUY PROFILE IS UPDATED", Toast.LENGTH_LONG).show();
                        idproname.setText("");
                        idkilo.setText("");

                    }
                });
            }
        });
    }

}


