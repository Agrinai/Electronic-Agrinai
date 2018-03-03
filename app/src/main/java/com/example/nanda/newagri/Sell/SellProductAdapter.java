package com.example.nanda.newagri.Sell;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nanda.newagri.Buy.BuyMatchMap;
import com.example.nanda.newagri.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Nanda on 2/4/2018.
 */
/*public class ProductAdapter {
}*/
public class SellProductAdapter extends RecyclerView.Adapter<SellProductAdapter.ProductViewHolder> {


    //this context we will use to inflate the layout
    private Context mCtx;

    //we are storing all the products in a list
    private List<SellProduct> productList;

    //getting the context and product list with constructor
    public SellProductAdapter(Context mCtx, List<SellProduct> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.recyclelayoutformatchsell,null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        //getting the product of the specified position
        final SellProduct product = productList.get(position);

        //binding the data with the viewholder views
        holder.textViewTitle.setText("Name :"+product.getName());
        holder.textViewMobileNumber.setText("Mobile Number :"+product.getMobilenumber());
        holder.textViewProductName.setText("Name :"+product.getProductname());
        holder.textViewKilo.setText("Quantity :"+String.valueOf(product.getKilo())+" KG ");
        Picasso.with(mCtx).load(product.getImage()).into(holder.imageView);
       // holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(product.getImage()));

        holder.mapView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mlat=product.getLat();
                String mlong=product.getLong();
                String name=product.getName();
                String phno=product.getMobilenumber();
                Intent i=new Intent(mCtx,SellMatchMap.class);
                i.putExtra("mlat",mlat);
                i.putExtra("mlong",mlong);
                i.putExtra("name",name);
                i.putExtra("phno",phno);
                mCtx.startActivity(i);
                Log.d("Lat",mlat);
                Log.d("Long",mlong);
            }
        });

    }


    @Override
    public int getItemCount() {
        return productList.size();
    }


    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, textViewProductName,textViewMobileNumber, textViewKilo;
        ImageView imageView;
        Button mapView;

        public ProductViewHolder(View itemView) {
            super(itemView);

            textViewTitle = (TextView) itemView.findViewById(R.id.textViewName);
            textViewMobileNumber = (TextView) itemView.findViewById(R.id.textViewMobileNumber);
            textViewProductName =(TextView) itemView.findViewById(R.id.textViewProductName);
            textViewKilo =(TextView) itemView.findViewById(R.id.textViewKilo);
            imageView = (ImageView)itemView.findViewById(R.id.imageView);
            mapView=(Button)itemView.findViewById(R.id.mapView);

        }
    }
}
