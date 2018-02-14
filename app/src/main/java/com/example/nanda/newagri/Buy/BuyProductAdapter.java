package com.example.nanda.newagri.Buy;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nanda.newagri.R;
import com.squareup.picasso.Picasso;

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
        BuyProduct product = productList.get(position);

        //binding the data with the viewholder views
        holder.textViewTitle.setText("Name : "+product.getName());
        holder.textViewProductName.setText("Name : "+product.getProductname());
        holder.textViewMobileNumber.setText("MobileNumber : "+product.getMobilenumber());
        holder.textViewKilo.setText("Quantity : "+String.valueOf(product.getKilo())+" KG ");
        holder.textViewPrice.setText("Price : "+String.valueOf(product.getPrice())+".0 ₹");
        Picasso.with(mCtx).load(product.getImage()).into(holder.imageView);
        //holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(product.getImage()));

    }


    @Override
    public int getItemCount() {
        return productList.size();
    }


    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, textViewProductName,textViewMobileNumber, textViewKilo, textViewPrice;
        ImageView imageView;

        public ProductViewHolder(View itemView) {
            super(itemView);

            textViewTitle = (TextView) itemView.findViewById(R.id.textViewName);
            textViewProductName =(TextView) itemView.findViewById(R.id.textViewProductName);
            textViewMobileNumber=(TextView) itemView.findViewById(R.id.textViewMobileNumber);
            textViewKilo =(TextView) itemView.findViewById(R.id.textViewKilo);
            textViewPrice = (TextView)itemView.findViewById(R.id.textViewPrice);
            imageView = (ImageView)itemView.findViewById(R.id.imageView);
        }
    }
}
