package com.example.nanda.newagri.LogIn;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.nanda.newagri.R;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.Random;

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
    EditText sname, semail,otpEditText;
    Button snext;
    String newemailidorphonenumber,newusername;
    String MobilePattern = "[0-9]{10}";
    int i=0;
    LinearLayout layoutOtp;
    Button verifyOtp;
    int code;
    Animation slideUpAnimation;
    Random rand = new Random();
    String otpCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        sname = (EditText) findViewById(R.id.sname);
        semail = (EditText) findViewById(R.id.semailid);
        otpEditText = (EditText) findViewById(R.id.otpEditText);
        snext = (Button) findViewById(R.id.snext);
        layoutOtp=(LinearLayout)findViewById(R.id.layoutOtp);
        layoutOtp.setVisibility(View.INVISIBLE);
        verifyOtp=(Button)findViewById(R.id.verifyOtp);
//      otpCode = String.valueOf(rand.nextInt(10000));

        snext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otpCode= String.valueOf((int)(Math.random()*9000)+1000);
                //otpCode = String.valueOf(rand.nextInt((10000 - 1111) + 1) + 1);
                Log.d("otpCode", String.valueOf(otpCode));
                newemailidorphonenumber = semail.getText().toString();
                newusername = sname.getText().toString();
                if (newusername.length() > 2) {
                    if (newemailidorphonenumber.matches(MobilePattern)) {
                        JSONObject json = new JSONObject();
                        try {
                            json.put("newemailidorphonenumber", newemailidorphonenumber);
                            json.put("otp", String.valueOf(otpCode));
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
    verifyOtp.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String enteredOtpCode=otpEditText.getText().toString();
            if(enteredOtpCode.equals(otpCode)){
                Intent i = new Intent(Signup.this, Spassword.class);
                startActivity(i);
            }
        }
    });
    }

    void postRequest(String postBody) throws IOException {
        String postUrl="http://ec2-18-219-200-74.us-east-2.compute.amazonaws.com:8080/agri/v1/User/newUser";
       //String postUrl = "https://agrinai.herokuapp.com/agri/v1/User/newUser";
       //String postUrl = "http://192.168.43.140:9000/agri/v1/User/newUser";
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
            public void onFailure(okhttp3.Call call, final IOException e) {
                call.cancel();
                Signup.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),"Err"+e,Toast.LENGTH_SHORT).show();
                    }
                });
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
                                    slideUpAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up_anim);
                                    layoutOtp.startAnimation(slideUpAnimation);
                                    layoutOtp.setVisibility(View.VISIBLE);
                                    SharedPreferences sp = getSharedPreferences("Data", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sp.edit();
                                    editor.putString("newemailidorphonenumber", newemailidorphonenumber);
                                    editor.putString("newusername", newusername);
                                    editor.apply();

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Toast.makeText(getApplicationContext(),"you can't go to previous Page",Toast.LENGTH_LONG).show();
    }
}







