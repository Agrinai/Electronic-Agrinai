package com.example.nanda.newagri;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appdatasearch.GetRecentContextCall;

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

public class Login extends AppCompatActivity {
    ProgressDialog progressDialog;
    TextView usertext,passtext;
    EditText idname, idpassword;
    Button login,signup;
    String name, password,userName,userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        idname = (EditText) findViewById(R.id.idname);
        idpassword = (EditText) findViewById(R.id.idpassword);
        login = (Button) findViewById(R.id.login);
        signup = (Button) findViewById(R.id.signup);
        usertext=(TextView)findViewById(R.id.usertext);
        passtext=(TextView)findViewById(R.id.passtext);
        usertext.setVisibility(View.INVISIBLE);
        passtext.setVisibility(View.INVISIBLE);

        idname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View arg0, boolean hasFocus) {
                if (hasFocus) {
                    usertext.setVisibility(View.VISIBLE);


                } else {
                    usertext.setVisibility(View.INVISIBLE);
                }
            }
        });

        idpassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View arg0, boolean hasFocus) {
                if (hasFocus) {
                    passtext.setVisibility(View.VISIBLE);
                } else {
                    passtext.setVisibility(View.INVISIBLE);

                }
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = idname.getText().toString();
                password = idpassword.getText().toString().toLowerCase();
                if(idname.getText().toString().trim().length()==0){
                    Toast.makeText(getApplicationContext(),"Please Enter Phone Number",Toast.LENGTH_SHORT).show();

                }if(idpassword.getText().toString().trim().length()==0){
                    Toast.makeText(getApplicationContext(),"Please Enter Password",Toast.LENGTH_SHORT).show();
                }
                if ( (name.length() == 10) ) {
                    JSONObject json=new JSONObject();
                    try {
                        json.put("emailorphone",name);
                        json.put("password", password);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        postRequest(json.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),"Please Enter Valid Phone Number",Toast.LENGTH_SHORT).show();
                }


            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(Login.this, Signup.class);
                startActivity(a);
            }
        });


    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        finishAffinity();
        System.exit(0);
    }

    void postRequest(String postBody) throws IOException {
        String postUrl = "https://agrinai.herokuapp.com/agri/v1/User/logIn";
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(JSON, postBody);

        final Request request = new Request.Builder()
                .url(postUrl)
                .post(body)
                .build();
        progressDialog = new ProgressDialog(Login.this);
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


                Login.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject json = new JSONObject(myRes);
                            JSONArray data = json.getJSONArray("data");
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject obj = data.getJSONObject(i);
                                userName = obj.getString("name");
                                userID = obj.getString("_id");

                            }
                            progressDialog.dismiss();
                            SharedPreferences sp = getSharedPreferences("loggeduser", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("name", userName);
                            editor.putString("userid", userID);
                            editor.apply();
                            Intent i = new Intent(Login.this, HomeScreen.class);
                            startActivity(i);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
}