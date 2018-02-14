package com.example.nanda.newagri.Buy;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.nanda.newagri.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class MatchForBuy extends AppCompatActivity {
    ProgressDialog progressDialog;
    String match;
    String product_name, kilo,price;
    TextView notmatch;
    String  username,phno,ppic;
    int code=0;
    RecyclerView recyclerView;
    List<BuyProduct> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matchbuy);
        notmatch=(TextView)findViewById(R.id.notmatch);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Product List
        productList = new ArrayList<>();

        SharedPreferences sp = getSharedPreferences("BuyData", Context.MODE_PRIVATE);
        match = sp.getString("match", "");
        new getSellerMatchesInBuy().execute();
    }

    public class getSellerMatchesInBuy extends AsyncTask<String,String,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MatchForBuy.this);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setIndeterminate(false);
            progressDialog.setMessage("Please Wait");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String postUrl = "https://agrinai.herokuapp.com/agri/v1/Buy/findVeg";
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");

            OkHttpClient client = new OkHttpClient();

            JSONObject json=new JSONObject();
            try {
                json.put("VegName", match);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            RequestBody body = RequestBody.create(JSON, json.toString());

            final Request request = new Request.Builder()
                    .url(postUrl)
                    .post(body)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                return response.body().string();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            final String myRes = s;
            Log.d("Response", s);
            MatchForBuy.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        JSONObject json = new JSONObject(myRes);
                        code = json.getInt("code");
                        if (code == 200) {
                            JSONArray parentArray = json.getJSONArray("data");
                            progressDialog.dismiss();
                            for (int i = 0; i < parentArray.length(); i++) {
                                JSONObject finalObject = parentArray.getJSONObject(i);
                                product_name = finalObject.getString("VegName");
                                Log.d("productname", product_name);
                                kilo = finalObject.getString("VegKG");
                                price = finalObject.getString("VegPrice");
                                JSONObject useridd = finalObject.getJSONObject("UserId");
                                username = useridd.getString("name");
                                Log.d("username", username);
                                phno = useridd.getString("emailorphone");
                                ppic = useridd.getString("profilepic");
                                Toast.makeText(getApplicationContext(), "" + username + "" + product_name, Toast.LENGTH_SHORT).show();
                                productList.add(
                                    new BuyProduct(
                                        1,
                                        username,
                                        phno,
                                        product_name,
                                        kilo,
                                        price,
                                        ppic
                                    )
                                );

                            }

                            //creating recyclerview adapter
                            BuyProductAdapter adapter = new BuyProductAdapter(getApplicationContext(), productList);

                            //setting adapter to recyclerview
                            recyclerView.setAdapter(adapter);
                        } else {
                            progressDialog.dismiss();
                            notmatch.setText("Sorry No Matches Found");

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}

