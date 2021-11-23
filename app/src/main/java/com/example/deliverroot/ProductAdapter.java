package com.example.deliverroot;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deliverroot.models.Categorymodel;
import com.example.deliverroot.models.ProductModel;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    public ProductAdapter(ArrayList<ProductModel> items) {
        this.items = items;
    }
    private ArrayList<ProductModel> items;

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_viewholder,parent,false);
        ProductAdapter.ProductViewHolder holder=new ProductAdapter.ProductViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        ProductModel currentItem= items.get(position);
        holder.imageV.setImageResource(currentItem.getImage());
        holder.nameV.setText(currentItem.getName());
        holder.rateV.setRating(currentItem.getRating());
        holder.priceV.setText(currentItem.getPrice());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView nameV;
        ImageView imageV;
        RatingBar rateV;
        TextView priceV;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imageV=itemView.findViewById(R.id.imageT20);
            nameV=itemView.findViewById(R.id.title);
            rateV=itemView.findViewById(R.id.rating);
            priceV=itemView.findViewById(R.id.txtprice);

        }
    }
}
