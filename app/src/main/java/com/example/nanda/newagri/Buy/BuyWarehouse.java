package com.example.nanda.newagri.Buy;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nanda.newagri.Buy.dialogBuy;
import com.example.nanda.newagri.R;


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


/**
 * Created by Lokesh on 17-08-2017.
 */
class BuyWarehouse extends ArrayAdapter<String>
{
    ProgressDialog progressDialog;
    private String[] PN;
    private String[] KG;
    private String[] _id;
    private Activity context;

    public BuyWarehouse(Activity context, String[] PN, String[] KG, String[] _id) {
        super(context, R.layout.buywarehouse, PN);
        this.context = context;
        this.PN = PN;
        this.KG = KG;
        this._id=_id;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.buywarehouse, null, true);
        TextView tv1 = (TextView) listViewItem.findViewById(R.id.vname);
        TextView tv2 = (TextView) listViewItem.findViewById(R.id.vkg);
        ImageView go=(ImageView) listViewItem.findViewById(R.id.go);

            tv1.setText("PRODUCT NAME:  " + PN[position]);
            tv2.setText("KG:  " + KG[position]);

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject json=new JSONObject();
                try {
                    json.put("_id", _id[position]);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    warehouseData(json.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        return  listViewItem;
    }


    void warehouseData(String postBody) throws IOException {
        String postUrl="https://agrinai.herokuapp.com/agri/v1/Buy/deletePost";
        //String postUrl="http://192.168.43.140/agri/v1/Buy/deletePost";
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(JSON, postBody);

        final Request request = new Request.Builder()
                .url(postUrl)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(okhttp3.Call call, final Response response) throws IOException {

                Intent intent=new Intent(context,dialogBuy.class);
                context.startActivity(intent);

            }
        });
    }
}