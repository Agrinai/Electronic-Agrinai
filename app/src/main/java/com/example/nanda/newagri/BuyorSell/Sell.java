package com.example.nanda.newagri.BuyorSell;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nanda.newagri.NamesList;
import com.example.nanda.newagri.R;
import com.example.nanda.newagri.Sell.dialogSell;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Sell extends Fragment {
    AutoCompleteTextView idproname;
    NamesList namelist = new NamesList();
    ArrayAdapter<String> ad, spinnerData;
    Spinner typeSpinner;
    String[] Types = {"Select Product Category", "Vegetables", "Fruits", "Seeds"};
    String product_name, kilo, prize;
    TextView text4;
    EditText idkilo, idprize;
    Button submit, matches, warehouse;

    String useridd, useriddd, SendUserID;
    ProgressDialog progressDialog;
    String[] PN;
    public Sell() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.sell_layout, container, false);
        typeSpinner=(Spinner)view.findViewById(R.id.typeSpinner);
        spinnerData = new ArrayAdapter<String>(getActivity(), R.layout.spinnercategory,Types);
        typeSpinner.setAdapter(spinnerData);
        idproname=(AutoCompleteTextView)view.findViewById(R.id.autoCompleteTextView1);

        idkilo=(EditText)view.findViewById(R.id.editText2);
        idprize = (EditText)view. findViewById(R.id.editText3);
        submit=(Button)view.findViewById(R.id.button);
        warehouse=(Button)view.findViewById(R.id.warehouse);

        SharedPreferences sp = getActivity().getSharedPreferences("loggeduser", Context.MODE_PRIVATE);
        useriddd = sp.getString("userid", "");
        SharedPreferences sp1 = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        useridd = sp1.getString("userid", "");

        if(useriddd.length()!=0){
            SendUserID=useriddd;}
        if(useridd.length()!=0){
            SendUserID=useridd;}

        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:
                        idproname.setText("");
                        break;
                    case 1:
                        idproname.setText("");
                        String[] vlist=namelist.VegList();
                        ad=new ArrayAdapter<String>(getActivity(), R.layout.spinnercategory,vlist);
                        idproname.setAdapter(ad);
                        idproname.setThreshold(1);

                        break;
                    case 2:
                        idproname.setText("");
                        String[] flist=namelist.FruitList();
                        ad=new ArrayAdapter<String>(getActivity(), R.layout.spinnercategory,flist);
                        idproname.setAdapter(ad);
                        idproname.setThreshold(1);
                        break;
                    case 3:
                        idproname.setText("");
                        String[] slist=namelist.SeedList();
                        ad=new ArrayAdapter<String>(getActivity(), R.layout.spinnercategory,slist);
                        idproname.setAdapter(ad);
                        idproname.setThreshold(1);
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getActivity(), "Please Select Category ", Toast.LENGTH_SHORT).show();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getActivity().getSharedPreferences("loggeduser", Context.MODE_PRIVATE);
                useriddd = sp.getString("userid", "");
                SharedPreferences sp1 = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
                useridd = sp1.getString("userid", "");

                if (useriddd.length() != 0) {
                    SendUserID = useriddd;
                }
                if (useridd.length() != 0) {
                    SendUserID = useridd;
                }

                if ((idproname.getText().toString().trim().length() != 0) && (idkilo.getText().toString().trim().length() != 0) && (idprize.getText().toString().trim().length() != 0)) {
                    product_name = idproname.getText().toString().toLowerCase();
                    kilo = idkilo.getText().toString();
                    prize = idprize.getText().toString();
                    Toast.makeText(getActivity(), "Product Name : " + idproname.getText().toString(), Toast.LENGTH_SHORT).show();
                    callServer();
                } else {
                    if (idproname.getText().toString().trim().length() == 0) {
                        Toast.makeText(getActivity(), "Please Enter Product Name", Toast.LENGTH_SHORT).show();
                    }
                    if (idkilo.getText().toString().trim().length() == 0) {
                        Toast.makeText(getActivity(), "Please Enter Product KG", Toast.LENGTH_SHORT).show();
                    }
                    if (idprize.getText().toString().trim().length() == 0) {
                        Toast.makeText(getActivity(), "Please Enter Product Prize", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        warehouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), dialogSell.class);
                i.putExtra("buyorsell", "Sell");
                startActivity(i);
                /*Dialog dialog=new Dialog(Sell.this);

                dialog.setContentView(R.layout.activity_dialog_sale);
                dialog.setCancelable(true);
                dialog.show();*/
            }
        });

        return view;
    }

    public void callServer(){
        new saveProduct1().execute();
    }
    class saveProduct1 extends AsyncTask<String,String,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setIndeterminate(false);
            progressDialog.setMessage("Please Wait");
            progressDialog.show();
        }


        @Override
        protected String doInBackground(String... params) {
            String postUrl = "https://agrinai.herokuapp.com/agri/v1/Sell/saveVeg";
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            JSONObject json=new JSONObject();
            try {
                json.put("UserId", SendUserID);
                json.put("VegName", product_name);
                json.put("VegKG", kilo);
                json.put("VegPrice", prize);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            OkHttpClient client = new OkHttpClient();
            RequestBody body = RequestBody.create(JSON, json.toString());
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
            Toast.makeText(getActivity(), "YOUR SELL PROFILE IS UPDATED", Toast.LENGTH_LONG).show();
            idproname.setText("");
            idkilo.setText("");
            idprize.setText("");
        }
        @Override
        protected void onCancelled() {
            super.onCancelled();
            Toast.makeText(getActivity(), "Request was cancelled", Toast.LENGTH_LONG).show();
        }
    }
}