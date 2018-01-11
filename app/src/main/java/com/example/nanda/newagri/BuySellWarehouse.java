package com.example.nanda.newagri;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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


/**
 * Created by Lokesh on 17-08-2017.
 */
class BuySellWarehouse extends ArrayAdapter<String>
{
    private String[] PN;
    private String[] KG;
    private String[] _id;

    String buyorsell;
    private Activity context;
    String godata;
    String urltosend,SendUserID;


    public BuySellWarehouse(Activity context, String[] PN, String[] KG,String[] _id,String buyorsell) {
        super(context, R.layout.buysellwarehouse, PN);
        this.context = context;
        this.PN = PN;
        this.KG = KG;
        this._id=_id;
        this.buyorsell=buyorsell;
        this.SendUserID=SendUserID;


    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.buysellwarehouse, null, true);
        TextView tv1 = (TextView) listViewItem.findViewById(R.id.vname);
        TextView tv2 = (TextView) listViewItem.findViewById(R.id.vkg);
        ImageView go=(ImageView) listViewItem.findViewById(R.id.go);

        urltosend="https://agrinai.herokuapp.com/agri/v1/"+buyorsell+"/deletePost";



            tv1.setText("PRODUCT NAME:  "+PN[position]);
            tv2.setText("KG:  " + KG[position]);




        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                godata = PN[position];
                new sendToAgri(""+_id[position]).execute();
            }
        });




        return  listViewItem;
    }

    private class sendToAgri extends AsyncTask<String, Void, String> {
        int code = 0;
        String  userID;



        public sendToAgri(String a) {
            userID = a;

        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            URL url;
            String jsonResponse;

            try {

                url = new URL(urltosend);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-type", "application/json");
                connection.setDoOutput(true);


                JSONObject jsondata = new JSONObject();
                jsondata.put("_id", userID);



                Writer writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
                writer.write(String.valueOf(jsondata));
                writer.close();

                InputStream inputStream = null;
                int status = connection.getResponseCode();
                if (status == 200) {
                    inputStream = connection.getInputStream();
                } else {
                    inputStream = connection.getErrorStream();
                }

                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    return null;
                }

                BufferedReader reader;
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String inputline;
                while ((inputline = reader.readLine()) != null)
                    buffer.append(inputline).append("\n");
                if (buffer.length() == 0) {
                    return null;
                }
                jsonResponse = buffer.toString();
                JSONObject json = new JSONObject(jsonResponse);
                code = json.getInt("code");

                return null;

            } catch (SocketTimeoutException e) {



            } catch (MalformedURLException e) {
                e.printStackTrace();

            } catch (IOException e) {

                e.printStackTrace();

            } catch (JSONException e) {
                e.printStackTrace();

            }

            return null;
        }

        @TargetApi(Build.VERSION_CODES.GINGERBREAD)
        @Override
        protected void onPostExecute(String s) {


            super.onPostExecute(s);
            if (code == 200) {
                if (BuySellWarehouse.this != null) {

                    if(buyorsell=="Sell"){
                        Intent intent=new Intent(context,dialogSell.class);
                        context.startActivity(intent);
                    }
                    else{
                        Intent intent=new Intent(context,dialogBuy.class);
                        context.startActivity(intent);
                    }


                }
            }
        }

    }



}