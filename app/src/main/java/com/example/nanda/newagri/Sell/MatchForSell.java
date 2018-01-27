package com.example.nanda.newagri.Sell;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Lokesh on 15-08-2017.
 */
public class MatchForSell extends AppCompatActivity {
    ProgressDialog progressDialog;
    ListView listView;
    String match;
    String product_name,kilo,userid;
    String useridd,useriddd,SendUserID;
    String  username,phno,ppic;
    TextView show;
    String[] PN,KG,Name,PHNO,Pr,propic;
    int code=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_match);
        show=(TextView)findViewById(R.id.show);
        show.setVisibility(View.INVISIBLE);

        listView=(ListView)findViewById(R.id.listView3);
        SharedPreferences sp = getSharedPreferences("loggeduser", Context.MODE_PRIVATE);
        useriddd = sp.getString("userid", "");
        SharedPreferences sp1 = getSharedPreferences("user", Context.MODE_PRIVATE);
        useridd = sp1.getString("userid", "");

        if(useriddd.length()!=0){
            SendUserID=useriddd;}
        if(useridd.length()!=0){
            SendUserID=useridd;}

        SharedPreferences sp2 = getSharedPreferences("SellData", Context.MODE_PRIVATE);
        match = sp2.getString("match", "");

        JSONObject json=new JSONObject();
        try {
            json.put("VegName", match);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            getMatches(json.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    void getMatches(String postBody) throws IOException {
        String postUrl = "https://agrinai.herokuapp.com/agri/v1/Sell/findVeg";
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(JSON, postBody);

        final Request request = new Request.Builder()
                .url(postUrl)
                .post(body)
                .build();
        progressDialog = new ProgressDialog(MatchForSell.this);
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
                //Log.d("Page :96",response.body().string());
                final String myRes = response.body().string();

                MatchForSell.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            progressDialog.dismiss();
                            JSONObject json = new JSONObject(myRes);
                            code=json.getInt("code");
                            if(code==200){
                                JSONArray parentArray = json.getJSONArray("data");
                                PN = new String[parentArray.length()];
                                KG = new String[parentArray.length()];
                                Name = new String[parentArray.length()];
                                PHNO = new String[parentArray.length()];
                                propic = new String[parentArray.length()];

                                for (int i = 0; i < parentArray.length(); i++) {
                                    JSONObject finalObject = parentArray.getJSONObject(i);

                                    product_name = finalObject.getString("VegName");
                                    PN[i] = product_name;
                                    kilo = finalObject.getString("VegKG");
                                    KG[i] = kilo;

                                    JSONObject useridd = finalObject.getJSONObject("UserId");
                                    username = useridd.getString("name");
                                    Name[i] = username;
                                    phno=useridd.getString("emailorphone");
                                    PHNO[i]=phno;
                                    ppic=useridd.getString("profilepic");
                                    propic[i]=ppic;
                                }
                                CustomListForSell customList = new CustomListForSell(MatchForSell.this,PN,KG,Name,PHNO,propic);
                                listView.setAdapter(customList);

                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", PHNO[i], null)));
                                    }
                                });
                            }
                            else{
                                Toast.makeText(getBaseContext(),"Sorry , No Matches",Toast.LENGTH_LONG).show();
                                show.setVisibility(View.VISIBLE);
                            }


                    } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
}

