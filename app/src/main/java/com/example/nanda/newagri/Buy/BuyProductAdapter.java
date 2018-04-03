package com.example.nanda.newagri.Buy;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nanda.newagri.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Nanda on 2/4/2018.
 */
/*public class ProductAdapter {
}*/
public class BuyProductAdapter extends RecyclerView.Adapter<BuyProductAdapter.ProductViewHolder> {


    //this context we will use to inflate the layout
    private Context mCtx;

    //we are storing all the products in a list
    private List<BuyProduct> productList;

    //getting the context and product list with constructor
    public BuyProductAdapter(Context mCtx, List<BuyProduct> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.recyclelayoutformatchbuy,null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        //getting the product of the specified position
        final BuyProduct product = productList.get(position);

        //binding the data with the viewholder views
        holder.textViewTitle.setText("Name : "+product.getName());
        holder.textViewProductName.setText("Name : "+product.getProductname());
        //holder.textViewMobileNumber.setText("MobileNumber : "+product.getMobilenumber());
        holder.textViewKilo.setText("Quantity : "+String.valueOf(product.getKilo())+" KG ");
        holder.textViewPrice.setText("Price : "+String.valueOf(product.getPrice())+".0 ₹");
        Picasso.with(mCtx).load(product.getImage()).into(holder.imageView);
        //holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(product.getImage()));
        holder.textViewPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name=product.getPrice();
                Log.d("Price",Name);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject userObj=new JSONObject();
                try {
                    userObj.put("sellerid",product.getSellerId());
                    userObj.put("selleruserid",product.getSellerUserId());
                    userObj.put("vegname",product.getProductname());
                    userObj.put("kg",product.getKilo());
                    userObj.put("price",product.getPrice());
                    userObj.put("username",product.getName());
                    userObj.put("userpic",product.getImage());
                    userObj.put("contact",product.getMobilenumber());
                    userObj.put("lat",product.getLat());
                    userObj.put("lng",product.getLong());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(mCtx,"Selected",Toast.LENGTH_LONG).show();
                Intent i=new Intent(mCtx, BuyUserProfile.class);
                i.putExtra("userObj", String.valueOf(userObj));
                mCtx.startActivity(i);

            }
        });
        /*holder.mapView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mlat=product.getLat();
                String mlong=product.getLong();
                String name=product.getName();
                String phno=product.getMobilenumber();
                Intent i=new Intent(mCtx,BuyMatchMap.class);
                i.putExtra("mlat",mlat);
                i.putExtra("mlong",mlong);
                i.putExtra("name",name);
                i.putExtra("phno",phno);
                mCtx.startActivity(i);
            }
        });
        holder.callView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String contactStr=product.getMobilenumber();
                mCtx.startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel",contactStr,null)));
            }
        });*/


    }


    @Override
    public int getItemCount() {
        return productList.size();
    }


    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, textViewProductName,textViewMobileNumber, textViewKilo, textViewPrice;
        ImageView imageView;
        ImageView mapView,callView;
        CardView finalCardView;

        public ProductViewHolder(View itemView) {
            super(itemView);

            textViewTitle = (TextView) itemView.findViewById(R.id.textViewName);
            textViewProductName =(TextView) itemView.findViewById(R.id.textViewProductName);
            //textViewMobileNumber=(TextView) itemView.findViewById(R.id.textViewMobileNumber);
            textViewKilo =(TextView) itemView.findViewById(R.id.textViewKilo);
            textViewPrice = (TextView)itemView.findViewById(R.id.textViewPrice);
            imageView = (ImageView)itemView.findViewById(R.id.imageView);
//            mapView = (ImageView)itemView.findViewById(R.id.mapView);
//            callView = (ImageView)itemView.findViewById(R.id.callView);
            finalCardView=(CardView)itemView.findViewById(R.id.finalcardView);
            //mapView=(Button)itemView.findViewById(R.id.mapView);
        }
    }
}
