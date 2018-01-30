package com.example.nanda.newagri.User;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nanda.newagri.Home.HomeScreen;
import com.example.nanda.newagri.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfile extends AppCompatActivity {
    CircleImageView cim;
    ProgressDialog progressDialog;
    private static int RESULT_LOAD_IMAGE = 1;
    EditText edname,edphno,edmailid,edaddress;
    String name,phno,emailid,address,proimg;
    String phph;
    String UserName,UserPhone,Useremail,UserAddress,Userpropic;
    byte[] img,img2;
    byte[] imageInByte;
    Button bupdate;
    String useridd,useriddd,SendUserID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        edname = (EditText) findViewById(R.id.edname);
        edmailid = (EditText) findViewById(R.id.edmailid);
        edaddress = (EditText) findViewById(R.id.edaddress);
        bupdate=(Button)findViewById(R.id.bupdate);
        cim=(CircleImageView)findViewById(R.id.cirimg);

        SharedPreferences sp = getSharedPreferences("loggeduser", Context.MODE_PRIVATE);
        useriddd = sp.getString("userid", "");
        SharedPreferences sp1 = getSharedPreferences("user", Context.MODE_PRIVATE);
        useridd = sp1.getString("userid", "");

        if(useriddd.length()!=0){
            SendUserID=useriddd;}
        if(useridd.length()!=0){
            SendUserID=useridd;}

        JSONObject json=new JSONObject();
        try {
            json.put("_id", SendUserID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            getProfilePost(json.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        cim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });
        bupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=edname.getText().toString();
                address=edaddress.getText().toString();
                emailid=edmailid.getText().toString();
                Bitmap bitmap = ((BitmapDrawable) cim.getDrawable()).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                imageInByte = baos.toByteArray();
                img2=imageInByte;
                String imageEncoded = Base64.encodeToString(img2,Base64.DEFAULT);


                JSONObject json=new JSONObject();
                try {
                    json.put("_id", SendUserID);
                    json.put("name", name);
                    json.put("emailid", emailid);
                    json.put("address", address);
                    json.put("profilepic", imageEncoded);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    updatePost(json.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }




            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri uri=data.getData();
            InputStream is= null;
            try {
                is = getContentResolver().openInputStream(uri);
                Bitmap bimg= BitmapFactory.decodeStream(is);
                cim.setImageBitmap(bimg);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getBaseContext(),"faild",Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(UserProfile.this,HomeScreen.class);
        startActivity(i);
    }
    void updatePost(String postBody) throws IOException {
        String postUrl = "https://agrinai.herokuapp.com/agri/v1/User/updateUser";
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(JSON, postBody);

        final Request request = new Request.Builder()
                .url(postUrl)
                .post(body)
                .build();
        progressDialog = new ProgressDialog(UserProfile.this);
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
                UserProfile.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                    }
                });
            }
        });
    }
    void getProfilePost(String postBody) throws IOException {
        String postUrl = "https://agrinai.herokuapp.com/agri/v1/User/getProfile";
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(JSON, postBody);

        final Request request = new Request.Builder()
                .url(postUrl)
                .post(body)
                .build();
        progressDialog = new ProgressDialog(UserProfile.this);
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
               final String myRes=response.body().string();
               // Log.d("Page :96",response.body().string());
                UserProfile.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        JSONObject json= null;
                        try {
                            json = new JSONObject(myRes);
                            JSONObject obj = json.getJSONObject("data");
                            UserName = obj.getString("name");
                            UserPhone=obj.getString("emailorphone");
                            Useremail=obj.getString("emailid");
                            UserAddress=obj.getString("address");
                            Userpropic=obj.getString("profilepic");
                            img=Userpropic.getBytes();

                            Bitmap bitmap;
                            edname.setText(UserName);
                            edmailid.setText(Useremail);
                            edaddress.setText(UserAddress);
                            byte[] decodedBytes = Base64.decode(img, 0);
                            bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);

                            cim.setImageBitmap(bitmap);
                            SharedPreferences sp = getSharedPreferences("userpropic", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("pic", Userpropic);
                            editor.putString("picname",UserName);
                            editor.apply();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                });
            }
        });
    }

}


