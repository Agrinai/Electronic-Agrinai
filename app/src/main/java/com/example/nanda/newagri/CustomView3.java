package com.example.nanda.newagri;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Lokesh on 17-08-2017.
 */
class CustomListForSell extends ArrayAdapter<String>
{
    private String[] PN;
    private String[] KG;
    private String[] Name;
    private String[] PHNO;
    String[] propic;
    Bitmap bitmap;
    private Activity context;


    public CustomListForSell(Activity context, String[] PN, String[] KG, String[] Name,String[] PHNO,String[] profilepic) {
        super(context, R.layout.list_layout3, PN);
        this.context = context;
        this.Name = Name;
        this.PN = PN;
        this.KG = KG;
        this.PHNO = PHNO;
        this.propic=profilepic;



    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_layout3, null, true);
        CircleImageView cim=(CircleImageView) listViewItem.findViewById(R.id.cirimg);
        TextView tv1 = (TextView) listViewItem.findViewById(R.id.tv1);
        TextView tv2 = (TextView) listViewItem.findViewById(R.id.tv2);
        TextView tv3 = (TextView) listViewItem.findViewById(R.id.tv3);
        TextView tv4 = (TextView) listViewItem.findViewById(R.id.tv4);
        int size=1;

        int len=propic.length;

        if(len!=0){
            String da=propic[position];
            byte[] data;
            data=da.getBytes();

            byte[] decodedBytes = Base64.decode(data, 0);
            bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
            cim.setImageBitmap(bitmap);
        }

            tv1.setText("Buyer Name :  "+Name[position]);
            tv2.setText("Product Name:  "+PN[position]);
            tv3.setText("KG:  "+KG[position]);
            tv4.setText("Mobile Number:  "+PHNO[position]);



        return  listViewItem;
    }
}