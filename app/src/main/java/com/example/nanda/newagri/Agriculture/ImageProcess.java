package com.example.nanda.newagri.Agriculture;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.net.Uri;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.nanda.newagri.Constants;
import com.example.nanda.newagri.R;
import com.example.nanda.newagri.User.UserEditScreen;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ImageProcess extends AppCompatActivity {
    private static int RESULT_LOAD_IMAGE = 1;
    ImageView soilimg,soilimg1,soilimg2,soilimg3;
    JSONArray colorArrayObject;
    JSONObject rgbObject=new JSONObject();
    Bitmap bimg,bm1;
    Button bcamera,bgallery,bnext;
    boolean captured;
    String camera;
    private int[][] rgbValues;
    ProgressDialog progressDialog;
    Constants constant=new Constants();

    private static final int CAMERA_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_process);
        soilimg=(ImageView)findViewById(R.id.soilimg);
        bcamera=(Button)findViewById(R.id.bcamera);
        bgallery=(Button)findViewById(R.id.bgallery);
        bnext=(Button)findViewById(R.id.bnext);

        soilimg.setVisibility(View.INVISIBLE);
        bnext.setVisibility(View.INVISIBLE);

        bcamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, 0);
            }
        });
        bnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rgbValuesFromBitmap(bm1);
            }
        });
        bgallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 1);
            }
        });

       /* Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_LOAD_IMAGE);*/


    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case 0:
                if(resultCode == RESULT_OK){
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    Log.d("extras", extras.toString());
                    soilimg.setImageBitmap(imageBitmap);
                    soilimg.setVisibility(View.VISIBLE);
                    bnext.setVisibility(View.VISIBLE);
                    int width = imageBitmap.getWidth()/2;
                    int height = imageBitmap.getHeight()/2;
                    bm1=Bitmap.createBitmap(imageBitmap,width,height,width/2,height/2);
                }
                break;
            case 1:
                if(resultCode == RESULT_OK){
                    Uri uri = data.getData();
                    InputStream is = null;
                    try {
                        is = getContentResolver().openInputStream(uri);
                        bimg = BitmapFactory.decodeStream(is);
                        soilimg.setImageBitmap(bimg);
                        soilimg.setVisibility(View.VISIBLE);
                        bnext.setVisibility(View.VISIBLE);
                        int width = bimg.getWidth()/2;
                        int height = bimg.getHeight()/2;
                        bm1=Bitmap.createBitmap(bimg,width,height,width/2,height/2);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        Toast.makeText(getBaseContext(), "faild", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    private byte[] rgbValuesFromBitmap(Bitmap bitmap)
    {
        /*for(int i=0; i < 1; i++)
        {
            for(int j=0; j < 20; j++)
            {
                //This is a great opportunity to filter the ARGB values
                int pixel = bitmap.getPixel(i,j);
                int red = Color.red(pixel);
                int blue = Color.blue(pixel);
                int green = Color.green(pixel);
                try {
                    JSONObject jsonToString=new JSONObject();
                    String rgbstring=""+red+","+green+","+blue;
                    jsonToString.put("red",String.valueOf(red));
                    jsonToString.put("green",String.valueOf(red));
                    jsonToString.put("blue",String.valueOf(red));
                    colorArrayObject.put(jsonToString);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("pixelvalue",String.valueOf(pixel));

            }
        }
        try {
            updatePost();
        } catch (IOException e) {
            e.printStackTrace();
        }
*/
        colorArrayObject=new JSONArray();
        ColorMatrix colorMatrix = new ColorMatrix();
        ColorFilter colorFilter = new ColorMatrixColorFilter(
                colorMatrix);

        Bitmap argbBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(argbBitmap);

        Paint paint = new Paint();

        paint.setColorFilter(colorFilter);
        canvas.drawBitmap(bitmap, 0, 0, paint);

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Log.d("width",String.valueOf(width));
        Log.d("height",String.valueOf(height));

        int componentsPerPixel = 3;
        int totalPixels = width * height;
        int totalBytes = totalPixels * componentsPerPixel;


        byte[] rgbValues = new byte[totalBytes];
        @ColorInt int[] argbPixels = new int[totalPixels];
        argbBitmap.getPixels(argbPixels, 0, width, 0, 0, width, height);
        Log.d("totalpixel",String.valueOf(totalPixels));
        for (int i = 0; i < totalPixels; i++) {
            @ColorInt int argbPixel = argbPixels[i];
            int red = Color.red(argbPixel);
            int green = Color.green(argbPixel);
            int blue = Color.blue(argbPixel);
            try {
                JSONObject jsonToString=new JSONObject();
                String rgbstring=""+red+","+green+","+blue;
                jsonToString.put("red",String.valueOf(red));
                jsonToString.put("green",String.valueOf(red));
                jsonToString.put("blue",String.valueOf(red));
                colorArrayObject.put(jsonToString);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Log.d("Birmap RGB Value",colorArrayObject.toString());
        try {
            updatePost();
        } catch (IOException e) {
            e.printStackTrace();
        }

      byte[] rgbValues1 = new byte[6];
        return rgbValues1;

    }
    void   updatePost() throws IOException {
        String result="200";
        String postUrl=constant.URL()+"/agri/v1/sendRGB";
        //String postUrl = "http://192.168.43.140:3000/sendRGB";
        //String postUrl = "http://10.10.24.84:3000/sendRGB";
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject json=new JSONObject();
        try {
            json.put("rgbvalue", this.colorArrayObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, json.toString());
        final Request request = new Request.Builder()
                .url(postUrl)
                .post(body)
                .build();
        OkHttpClient client = new OkHttpClient();
        progressDialog = new ProgressDialog(ImageProcess.this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIndeterminate(false);
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                ImageProcess.this.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(ImageProcess.this, "Failure", Toast.LENGTH_SHORT).show();
                    }
                });
                call.cancel();}
            @Override
            public void onResponse(okhttp3.Call call, final Response response) throws IOException {
                final String myRes = response.body().string();
                Log.d("myRes",myRes);

                ImageProcess.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject json = null;
                        try {
                            progressDialog.dismiss();
                            json = new JSONObject(myRes);
                            Log.d("myRes1",json.toString());
                            String color=json.getString("color");
                            Log.d("ResColor",color);
                            Intent i=new Intent(ImageProcess.this,VegListOfPHvalue.class);
                            i.putExtra("color",color);
                            startActivity(i);
                            Log.d("UpdateResult",json.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

}
