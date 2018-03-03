package com.example.nanda.newagri.LogIn;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nanda.newagri.Home.HomeScreen;
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

/**
 * Created by Dinesh on 12-08-2017.
 */public class Spassword extends Activity {
    ProgressDialog progressDialog;
      EditText spassword, scpassword;
      Button screate;
      String newpassword,newconfirmpassword;
      String  userName,newemailidorphonenumber;
    String responseusername,responseuserid,mLat,mLong;
    int code;

    @Override
          protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_spassword);
           spassword = (EditText) findViewById(R.id.spassword);
           scpassword = (EditText) findViewById(R.id.scpassword);
           screate = (Button) findViewById(R.id.screate);

        screate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newpassword = spassword.getText().toString().toLowerCase();
                newconfirmpassword = scpassword.getText().toString().toLowerCase();
                SharedPreferences sp = getSharedPreferences("Data", Context.MODE_PRIVATE);
                userName = sp.getString("newusername", "");
                newemailidorphonenumber = sp.getString("newemailidorphonenumber", "");
                if (newpassword.length() > 3) {
                    if (newpassword.length() < 10) {
                        if (newpassword.equals(newconfirmpassword)) {
                            JSONObject json=new JSONObject();
                            try {
                                json.put("emailorphone",newemailidorphonenumber);
                                json.put("password", newpassword);
                                json.put("username", userName);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                postRequest(json.toString());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            scpassword.setError("Password doesn't match");
                        }
                    } else {
                        spassword.setError("between 4 and 10 alphanumeric characters");
                    }
                } else {
                    spassword.setError("between 4 and 10 alphanumeric characters");
                }

            }
        });
    }
    void postRequest(String postBody) throws IOException {
        String postUrl="http://ec2-18-219-200-74.us-east-2.compute.amazonaws.com:8080/agri/v1/User/saveUser";
        //String postUrl = "https://agrinai.herokuapp.com/agri/v1/User/saveUser";
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(JSON, postBody);

        final Request request = new Request.Builder()
                .url(postUrl)
                .post(body)
                .build();
        progressDialog = new ProgressDialog(Spassword.this);
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
                //Log.d("Page:119",response.body().string());
                final String myRes = response.body().string();


                Spassword.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject resData = new JSONObject(myRes);
                            JSONObject data = resData.getJSONObject("data");
                            responseuserid = data.getString("_id");
                            responseusername = data.getString("name");
                            JSONObject locationObject=data.getJSONObject("location");
                            Log.d("locationObj",locationObject.toString());
                            mLat = locationObject.getString("lat");
                            mLong=locationObject.getString("long");
                            Log.d("mLat",mLat);

                            SharedPreferences sp = getSharedPreferences("user", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("name", responseusername);
                            editor.putString("userid", responseuserid);
                            editor.apply();
                            progressDialog.dismiss();
                            Intent i = new Intent(Spassword.this, HomeScreen.class);
                            startActivity(i);

                        } catch (JSONException e) {
                            SharedPreferences ssp2 = getSharedPreferences("user", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor2 = ssp2.edit();
                            editor2.clear();
                            editor2.commit();
                            progressDialog.dismiss();

                            Intent i=new Intent(Spassword.this, SetUserLocation.class);
                            i.putExtra("userId",responseuserid);
                            i.putExtra("userName", responseusername);
                            i.putExtra("check","s");
                            startActivity(i);
                            Toast.makeText(getApplication(),"Set Your Location",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Toast.makeText(getApplicationContext(), "you can't go to previous Page", Toast.LENGTH_LONG).show();
    }
}

