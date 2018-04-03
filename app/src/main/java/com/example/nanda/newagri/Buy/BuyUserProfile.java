package com.example.nanda.newagri.Buy;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nanda.newagri.Constants;
import com.example.nanda.newagri.R;
import com.example.nanda.newagri.TransactionHistory.BuySellTransaction;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Array;
import java.util.Arrays;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class BuyUserProfile extends AppCompatActivity {

    TextView vegname,vegavailablekg, vegprice, veg1kgprice,vegmarket1kgprice, sellername, contact, formtype, location, ratings ,totalamount;
    ImageView callButton, viewRoute, msgButton,userpic;
    Spinner kgspinner;
    String lat,lng,usernameStr,userpicStr,contactStr,priceStr,kgStr,vegnameStr,sellerId,sellerUserId;
    String useridd,useriddd,SendUserID;
    Integer marketprice1KG,price1KG,price,kg,selectedKG,totalprice;
    Button buynow;
    JSONObject json=new JSONObject();
    ProgressDialog progressDialog;
    Constants constant=new Constants();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyuserprofile);
        vegname=(TextView)findViewById(R.id.vegname);
        vegavailablekg=(TextView)findViewById(R.id.vegavailablekg);
        vegprice=(TextView)findViewById(R.id.vegprice);
        veg1kgprice=(TextView)findViewById(R.id.veg1kgprice);
        vegmarket1kgprice=(TextView)findViewById(R.id.vegmarket1kgprice);
        sellername=(TextView)findViewById(R.id.sellername);
        contact=(TextView)findViewById(R.id.contact);
        formtype=(TextView)findViewById(R.id.formtype);
        location=(TextView)findViewById(R.id.location);
        ratings=(TextView)findViewById(R.id.ratings);
        kgspinner=(Spinner)findViewById(R.id.kgspinner);
        totalamount=(TextView)findViewById(R.id.totalamount);
        userpic=(ImageView)findViewById(R.id.userpic);
        callButton=(ImageView)findViewById(R.id.callButton);
        viewRoute=(ImageView)findViewById(R.id.viewRoute);
        msgButton=(ImageView)findViewById(R.id.msgButton);
        buynow=(Button)findViewById(R.id.buynowButton);

        String userGetObj=getIntent().getStringExtra("userObj");
        try {
            JSONObject userObj=new JSONObject(userGetObj);
            Log.d("userObj",userObj.toString());
            sellerId=userObj.getString("sellerid");
            sellerUserId=userObj.getString("selleruserid");
            vegnameStr=userObj.getString("vegname");
            kgStr=userObj.getString("kg");
            kg=Integer.parseInt(kgStr);
            priceStr=userObj.getString("price");
            price=Integer.parseInt(priceStr);
            price1KG=price/kg;
            marketprice1KG=price1KG+10;
            usernameStr=userObj.getString("username");
            userpicStr=userObj.getString("userpic");
            contactStr=userObj.getString("contact");
            lat=userObj.getString("lat");
            lng=userObj.getString("lng");

            Picasso.with(BuyUserProfile.this).load(userpicStr).into(userpic);
            vegname.setText(vegnameStr);
            vegavailablekg.setText(kgStr+" KG ");
            vegprice.setText(priceStr+".0 ₹");
            veg1kgprice.setText(""+price1KG+".0 ₹");
            vegmarket1kgprice.setText(""+marketprice1KG+".0 ₹");
            sellername.setText(usernameStr);
            contact.setText(contactStr);
            formtype.setText("Organic");
            location.setText("Adyar");
            ratings.setText("****");

            callButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel",contactStr,null)));
                }
            });
            msgButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(BuyUserProfile.this);
                    final EditText edittext = new EditText(BuyUserProfile.this);
                    alert.setTitle("Send SMS");
                    alert.setMessage("Enter Your Message");
                    alert.setView(edittext);

                    alert.setPositiveButton("Send", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {

                            String Message = edittext.getText().toString();
                            SmsManager smsManager=SmsManager.getDefault();
                            smsManager.sendTextMessage(contactStr,null,Message,null,null);
                        }
                    });

                    alert.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            // what ever you want to do with No option.

                        }
                    });

                    alert.show();
                }
            });

            viewRoute.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String mlat=lat;
                    String mlong=lng;
                    String name=usernameStr;
                    String phno=contactStr;
                    Intent i=new Intent(BuyUserProfile.this,BuyMatchMap.class);
                    i.putExtra("mlat",mlat);
                    i.putExtra("mlong",mlong);
                    i.putExtra("name",name);
                    i.putExtra("phno",phno);
                    startActivity(i);
                }
            });
            Integer kgarraylength=kg+1;
            String[] kgarray=new String[kgarraylength];
            kgarray[0]="Select kg";
            for (int i=1;i<=kg;i++){
                kgarray[i]=""+i;
            }

            SpinnerAdapter adapter = new ArrayAdapter<String>(BuyUserProfile.this, android.R.layout.simple_list_item_1,kgarray);
            kgspinner.setAdapter(adapter);
            kgspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if(position!=0){
                        selectedKG=Integer.valueOf(kgspinner.getSelectedItem().toString());
                        totalprice=selectedKG*price1KG;
                        totalamount.setText(""+totalprice+".0 ₹");
                    }
                    if(position==0){
                        totalamount.setText(".0 ₹");
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

        buynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getSharedPreferences("BuyData", Context.MODE_PRIVATE);
                String buyerId=sp.getString("id","");
                SharedPreferences sp1 = getSharedPreferences("loggeduser", Context.MODE_PRIVATE);
                useriddd = sp1.getString("userid", "");
                SharedPreferences sp2 = getSharedPreferences("user", Context.MODE_PRIVATE);
                useridd = sp2.getString("userid", "");

                if (useriddd.length() != 0) {
                    SendUserID = useriddd;
                }
                if (useridd.length() != 0) {
                    SendUserID = useridd;
                }
                if(selectedKG.toString().length()==0 || totalprice.toString().length()==0){
                    Toast.makeText(getApplicationContext(),"Pllease Select Number of Kg",Toast.LENGTH_LONG).show();
                }else{
                    try {
                        json.put("sellerId",sellerId);
                        json.put("sellerUserId",sellerUserId);
                        json.put("buyerId",buyerId);
                        json.put("buyerUserId",SendUserID);
                        json.put("vegetableName",vegnameStr);
                        json.put("vegetableKg",selectedKG);
                        json.put("totalAmount",totalprice);

                        new soldProduct().execute();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }
    class soldProduct extends AsyncTask<String,String,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(BuyUserProfile.this);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setIndeterminate(false);
            progressDialog.setMessage("Please Wait");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String postUrl=constant.URL()+"/agri/v1/Sold/save";
            // String postUrl="http://ec2-18-219-200-74.us-east-2.compute.amazonaws.com:8080/agri/v1/Buy/saveVeg";
            //String postUrl = "https://agrinai.herokuapp.com/agri/v1/Buy/saveVeg";
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            OkHttpClient client = new OkHttpClient();
            JSONObject jsonToSend=new JSONObject();
            try {
                jsonToSend.put("soldObj",json);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            RequestBody body = RequestBody.create(JSON, jsonToSend.toString());
            final Request request = new Request.Builder()
                    .url(postUrl)
                    .post(body)
                    .build();

            try {
                Response response = client.newCall(request).execute();
                return response.body().string();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            AlertDialog.Builder alert = new AlertDialog.Builder(BuyUserProfile.this);
            final EditText edittext = new EditText(BuyUserProfile.this);
            alert.setTitle("Completed");
            alert.setMessage("Product has been ordered");

            alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                    /*String  selKg=String.valueOf(selectedKG);
                    String toPrice=String.valueOf(totalprice);*/
                    SharedPreferences sp = getSharedPreferences("transaction", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("vegnameStr",vegnameStr);
                    editor.putInt("selectedKG",selectedKG);
                    editor.putInt("totalprice",totalprice);

                    editor.apply();

                 Intent transaction=new Intent(BuyUserProfile.this, BuySellTransaction.class);
                    transaction.putExtra("vegnameStr",vegnameStr);
                    transaction.putExtra("selectedKG",selectedKG);
                    transaction.putExtra("totalprice",totalprice);
                    transaction.putExtra("sellername",usernameStr);
                 startActivity(transaction);
                }
            });

            alert.show();
            Log.d("SoldResponse",s);
        }
    }
}
