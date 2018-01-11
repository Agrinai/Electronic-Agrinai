package com.example.nanda.newagri;

import android.annotation.TargetApi;
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
 * Created by Dinesh on 12-08-2017.
 */
public class Signup extends Activity {
    ProgressDialog progressDialog;
    EditText sname, semail;
    Button snext;
    String newemailidorphonenumber,newusername;
    String MobilePattern = "[0-9]{10}";
    int i=0;
    int code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        sname = (EditText) findViewById(R.id.sname);
        semail = (EditText) findViewById(R.id.semailid);
        snext = (Button) findViewById(R.id.snext);

        snext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newemailidorphonenumber = semail.getText().toString();
                newusername = sname.getText().toString();
                if (newusername.length() > 2 ) {
                    if (newemailidorphonenumber.matches(MobilePattern)) {
                        JSONObject json=new JSONObject();
                        try {
                            json.put("emailorphone",newemailidorphonenumber);
                            json.put("password", newusername);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            postRequest(json.toString());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        semail.setError("enter a valid email address or phonenumber");
                    }
                } else {
                    sname.setError("at least 3 characters");
                }
            }
        });
    }

    void postRequest(String postBody) throws IOException {
        String postUrl = "https://agrinai.herokuapp.com/agri/v1/User/newUser";
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(JSON, postBody);

        final Request request = new Request.Builder()
                .url(postUrl)
                .post(body)
                .build();
        progressDialog = new ProgressDialog(Signup.this);
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
                final String myRes=response.body().string();


                Signup.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject json=new JSONObject(myRes);

                            code=json.getInt("code");
                            //JSONArray data = json.getJSONArray("data");

                           /* for(int i=0;i<data.length();i++)
                            {
                                JSONObject obj=data.getJSONObject(i);
                                i=obj.getInt("status");
                            }*/
                            if (code==500) {
                                    progressDialog.dismiss();
                                    SharedPreferences sp = getSharedPreferences("Data", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sp.edit();
                                    editor.putString("newemailidorphonenumber", newemailidorphonenumber);
                                    editor.putString("newusername", newusername);
                                    editor.apply();
                                    Intent i = new Intent(Signup.this, Spassword.class);
                                    startActivity(i);
                            } else if (code== 1){
                                Toast.makeText(Signup.this, "This User already exists", Toast.LENGTH_LONG).show();
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







