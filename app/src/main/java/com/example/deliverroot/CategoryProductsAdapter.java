package com.example.deliverroot;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.deliverroot.models.CategoryProductModel;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.FutureTask;

public class CategoryProductsAdapter extends RecyclerView.Adapter<CategoryProductsAdapter.CategoryProductsViewHolder> {

    private ArrayList<CategoryProductModel> items;
    LayoutInflater inflater;
    Context context;
    OnListclickListener onListclickListener;
    private ArrayList<CategoryProductModel> filteredUserDataList;


    public CategoryProductsAdapter(Context ctx, ArrayList<CategoryProductModel> items, OnListclickListener onListclickListener) {
        this.items = items;
        this.context=ctx;
        this.filteredUserDataList=items;
        this.inflater=LayoutInflater.from(ctx);
        this.onListclickListener=onListclickListener;
    }



    @NonNull
    @Override
    public CategoryProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= inflater.inflate(R.layout.categoryproduct_viewholder,parent,false);
        CategoryProductsViewHolder holder=new CategoryProductsViewHolder(view,onListclickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryProductsViewHolder holder, final int position) {

        final CategoryProductModel currentItem= filteredUserDataList.get(position);
        holder.productName.setText(currentItem.getProductName());
        holder.cost.setText("₹"+currentItem.getCost());
        holder.desc.setText(currentItem.getDesc());

        Glide.with(holder.image).load(currentItem.getImageurl()).into(holder.image);
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context, ProductDetailsActivity.class);
                i.putExtra("id",filteredUserDataList.get(position).getId());

                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredUserDataList.size();
    }

    public static class CategoryProductsViewHolder extends RecyclerView.ViewHolder {

        TextView productName;
        TextView desc;
        TextView cost;
        ImageView image;
        ImageView addbtn,productwish;
        OnListclickListener onListclickListener;

        public CategoryProductsViewHolder(@NonNull View itemView, final OnListclickListener onListclickListener) {
            super(itemView);
            productName=itemView.findViewById(R.id.productname);
            addbtn=itemView.findViewById(R.id.add);
            desc=itemView.findViewById(R.id.productdesc);
            cost=itemView.findViewById(R.id.productcost);
            image=itemView.findViewById(R.id.productimage);
            productwish=itemView.findViewById(R.id.productwish);


            addbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onListclickListener.addedToCart(getAdapterPosition());
                }
            });

            productwish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    productwish.setImageResource(R.drawable.wishlist_icon);
                    onListclickListener.addedToWish(getAdapterPosition());
                }
            });

        }
    }

    public Filter getfilter(){
    return new Filter(){

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            String Key = charSequence.toString();
            if(Key.isEmpty()){
                filteredUserDataList = items;
            }
            else{

                ArrayList<CategoryProductModel> lstFiltered = new ArrayList<>();
                for(CategoryProductModel row: items){
                    if(row.getProductName().toLowerCase().contains(Key.toLowerCase())){
                        lstFiltered.add(row);

                    }
                }

                filteredUserDataList = lstFiltered;
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredUserDataList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredUserDataList = (ArrayList<CategoryProductModel>)results.values;
            notifyDataSetChanged();
        }
    };
    }

    public interface OnListclickListener{
        void addedToCart(int pos);
        void addedToWish(int pos);
    }

}
