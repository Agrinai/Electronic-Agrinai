package com.example.nanda.newagri;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
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

public class dialogSell extends AppCompatActivity {
    ListView veglist;
    String[] PN;
    String[] KG;
    String[] _userID;
    String[] _id;
    ProgressDialog progressDialog;
    String useridd,useriddd,SendUserID;
    String product_name,kilo,id;
    String buyorsell="Sell";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dialog_sale);
        veglist = (ListView) findViewById(R.id.veglist);



        SharedPreferences sp = getSharedPreferences("loggeduser", Context.MODE_PRIVATE);
        useriddd = sp.getString("userid", "");
        SharedPreferences sp1 = getSharedPreferences("user", Context.MODE_PRIVATE);
        useridd = sp1.getString("userid", "");

        if(useriddd.length()!=0){
            SendUserID=useriddd;}
        if(useridd.length()!=0){
            SendUserID=useridd;}

        //////Automatic ServerCall For User Already post Data///////////////////
        JSONObject json=new JSONObject();
        try {
            json.put("UserId", SendUserID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            getWarehouseData(json.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        veglist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String veg = String.valueOf(parent.getItemAtPosition(position));
                SharedPreferences sp = getSharedPreferences("SellData", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("match", veg);
                editor.apply();
                Intent a = new Intent(dialogSell.this, MatchForSell.class);
                startActivity(a);
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(dialogSell.this,Sell.class);
        startActivity(i);
    }
    void getWarehouseData(String postBody) throws IOException {
        String postUrl = "https://agrinai.herokuapp.com/agri/v1/Sell/findUserPostData";
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(JSON, postBody);

        final Request request = new Request.Builder()
                .url(postUrl)
                .post(body)
                .build();
        progressDialog = new ProgressDialog(dialogSell.this);
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
                final String myRes = response.body().string();
                // Log.d("Page :96",response.body().string());
                dialogSell.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        try {
                            JSONObject json = new JSONObject(myRes);
                            JSONArray parentArray = json.getJSONArray("data");

                            PN = new String[parentArray.length()];
                            KG = new String[parentArray.length()];
                            _id = new String[parentArray.length()];

                            for (int i = 0; i < parentArray.length(); i++) {
                                JSONObject finalObject = parentArray.getJSONObject(i);
                                product_name = finalObject.getString("VegName");
                                PN[i] = product_name;
                                kilo = finalObject.getString("VegKG");
                                KG[i] = kilo;
                                id = finalObject.getString("_id");
                                _id[i] = id;
                            }
                            BuySellWarehouse WarehouseList = new BuySellWarehouse(dialogSell.this,PN,KG,_id,buyorsell);
                            veglist.setAdapter(WarehouseList);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        });
    }
}
