package com.example.nanda.newagri;

/**
 * Created by Nanda on 8/16/2017.
 */
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.charset.Charset;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Belal on 7/22/2015.
 */
public class CustomListForBuy extends ArrayAdapter<String> {
    private String[] PN;
    private String[] KG;
    private String[] Name;
    private String[] Pr;
    private String[] PHNO;
    String[] propic;
    Bitmap bitmap;
    private Activity context;

    public CustomListForBuy(Activity context, String[] PN, String[] KG, String[] Name, String[] Price,String[] PHNO,String[] profilepic ) {
        super(context, R.layout.list_layout2, PN);
        this.context = context;
        this.Name = Name;
        this.PN = PN;
        this.KG = KG;
        this.Pr=Price;
        this.PHNO=PHNO;
        this.propic=profilepic;


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_layout2, null, true);
        CircleImageView cim=(CircleImageView) listViewItem.findViewById(R.id.cirimg);
        TextView tv1 = (TextView) listViewItem.findViewById(R.id.tv1);
        TextView tv2 = (TextView) listViewItem.findViewById(R.id.tv2);
        TextView tv3 = (TextView) listViewItem.findViewById(R.id.tv3);
        TextView tv4 = (TextView) listViewItem.findViewById(R.id.tv4);
        TextView tv5 = (TextView) listViewItem.findViewById(R.id.tv5);



      int len=propic.length;
        if(len!=0){
            String da=propic[position];
            byte[] data;
            data=da.getBytes();

            byte[] decodedBytes = Base64.decode(data, 0);
           bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
            cim.setImageBitmap(bitmap);
        }


        tv1.setText("Seller Name :  "+Name[position]);
        tv2.setText("Product Name:  "+PN[position]);
        tv3.setText("KG:  "+KG[position]);
        tv4.setText("Price(per kg):  "+Pr[position]);
        tv5.setText("Mobile Number:  "+PHNO[position]);


        return  listViewItem;
    }
}