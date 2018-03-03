package com.example.nanda.newagri.User;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.nanda.newagri.Buy.MatchForBuy;
import com.example.nanda.newagri.Home.HomeScreen;
import org.json.JSONException;
import org.json.JSONObject;
import com.example.nanda.newagri.R;
import com.squareup.picasso.Picasso;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UserEditScreen extends AppCompatActivity {
    ImageView cim,changeImage;
    ProgressDialog progressDialog;
    private static int RESULT_LOAD_IMAGE = 1;
    EditText edname, edmailid, edaddress;
    String name, emailid, address;
    String UserName, UserPhone, Useremail, UserAddress, Userpropic, StringUri,Result;
    Button bupdate;
    JSONObject imgResJson;
    JSONObject jsonForUpdate = new JSONObject();
    String useridd, useriddd, SendUserID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usereditscreen);
        edmailid = (EditText) findViewById(R.id.emailid);
        edaddress = (EditText) findViewById(R.id.address);
        edname = (EditText) findViewById(R.id.nametext);
        cim = (ImageView) findViewById(R.id.profileimgview);
        changeImage = (ImageView) findViewById(R.id.changeImage);
        bupdate = (Button) findViewById(R.id.updateProfile);

        //Get UserData From SharedPref//////////
        SharedPreferences user = getSharedPreferences("userpropic", Context.MODE_PRIVATE);
            String picstring = user.getString("pic", "");
            UserName = user.getString("username", "");
            Useremail = user.getString("useremail", "");
            UserAddress = user.getString("useraddress", "");
            Picasso.with(this).load(picstring).into(cim);
        SharedPreferences sp = getSharedPreferences("loggeduser", Context.MODE_PRIVATE);
            useriddd = sp.getString("userid", "");
        SharedPreferences sp1 = getSharedPreferences("user", Context.MODE_PRIVATE);
            useridd = sp1.getString("userid", "");


        edname.setText("" + UserName);
        edmailid.setText("" + Useremail);
        edaddress.setText("" + UserAddress);

        if (useriddd.length() != 0) {
            SendUserID = useriddd;
        }
        if (useridd.length() != 0) {
            SendUserID = useridd;
        }
        //Get UserData From SharedPref//////////

        changeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });
        bupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = edname.getText().toString();
                address = edaddress.getText().toString();
                emailid = edmailid.getText().toString();

                if(StringUri  == null){
                    try {
                        jsonForUpdate.put("_id", SendUserID);
                        jsonForUpdate.put("name", name);
                        jsonForUpdate.put("emailid", emailid);
                        jsonForUpdate.put("address", address);
                        jsonForUpdate.put("profilepic", "0");

                        try {
                            updatePost();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    new tasktoUpload().execute();
                    try {

                        jsonForUpdate.put("_id", SendUserID);
                        jsonForUpdate.put("name", name);
                        jsonForUpdate.put("emailid", emailid);
                        jsonForUpdate.put("address", address);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }


            }
        });
    }
    public class tasktoUpload extends AsyncTask<String,String,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(UserEditScreen.this);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setIndeterminate(false);
            progressDialog.setMessage("Please Wait");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            File file=new File(StringUri);
            Map config = new HashMap();
            config.put("cloud_name", "ddknctkcj");
            config.put("api_key", "365112624636693");
            config.put("api_secret", "TlqUrg07ImSe-TmtvH0ALUdp6gI");
            Cloudinary cloudinary = new Cloudinary(config);
            try {
                Result=cloudinary.uploader().upload(file, ObjectUtils.emptyMap()).toString();
                jsonForUpdate.put("profilepic", Result);
            } catch (IOException e) {
                e.printStackTrace();
                UserEditScreen.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplication(), "Err ", Toast.LENGTH_LONG).show();
                    }
                });

                Log.d("Err", String.valueOf(e));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(final String s) {
            super.onPostExecute(s);

            try {
                updatePost();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri uri = data.getData();
            StringUri= getRealPathFromURI(uri);

            InputStream is = null;
            try {
                is = getContentResolver().openInputStream(uri);
                Bitmap bimg = BitmapFactory.decodeStream(is);
                cim.setImageBitmap(bimg);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getBaseContext(), "faild", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(UserEditScreen.this, HomeScreen.class);
        startActivity(i);
    }



    void updatePost() throws IOException {
        String postUrl="http://ec2-18-219-200-74.us-east-2.compute.amazonaws.com:8080/agri/v1/User/updateUser";
       //String postUrl = "https://agrinai.herokuapp.com/agri/v1/User/updateUser";
        //String postUrl = "http://192.168.43.140:9000/agri/v1/User/updateUser";
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        Log.d("Requpdate",jsonForUpdate.toString());
        RequestBody body = RequestBody.create(JSON, jsonForUpdate.toString());
        progressDialog = new ProgressDialog(UserEditScreen.this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIndeterminate(false);
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        final Request request = new Request.Builder()
                .url(postUrl)
                .post(body)
                .build();
        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                UserEditScreen.this.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(UserEditScreen.this, "Failure", Toast.LENGTH_SHORT).show();
                    }
                });
                call.cancel();}

            @Override
            public void onResponse(okhttp3.Call call, final Response response) throws IOException {
                final String myRes = response.body().string();
                UserEditScreen.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject json = null;
                        try {
                            progressDialog.dismiss();
                            json = new JSONObject(myRes);
                            Log.d("UpdateResult",json.toString());
                            JSONObject obj = json.getJSONObject("data");
                            UserName = obj.getString("name");
                            UserPhone = obj.getString("emailorphone");
                            Useremail = obj.getString("emailid");
                            UserAddress = obj.getString("address");
                            Userpropic = obj.getString("profilepic");


                            SharedPreferences sp = getSharedPreferences("userpropic", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("pic", Userpropic);
                            editor.putString("userphone", UserPhone);
                            editor.putString("useremail", Useremail);
                            editor.putString("useraddress", UserAddress);
                            editor.putString("username", UserName);
                            editor.apply();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
    //Code For getting Full Path Of Image//
    public String getRealPathFromURI(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        @SuppressWarnings("deprecation")
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
    //Code For getting Full Path Of Image//
}