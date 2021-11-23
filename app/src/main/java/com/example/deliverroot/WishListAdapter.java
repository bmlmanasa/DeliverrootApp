package com.example.deliverroot;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.deliverroot.models.CategoryProductModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class WishListAdapter  extends FirebaseRecyclerAdapter<CategoryProductModel, WishListAdapter.WishViewHolder> {
    OnListclickListener onListclickListener;
    public WishListAdapter(@NonNull FirebaseRecyclerOptions<CategoryProductModel> options,OnListclickListener onListclickListener) {
        super(options);
        this.onListclickListener=onListclickListener;
    }

    @Override
    protected void onBindViewHolder(@NonNull final WishViewHolder holder, final int position, @NonNull CategoryProductModel categoryProductModel) {
        holder.productName.setText(categoryProductModel.getProductName());
        holder.cost.setText("₹"+categoryProductModel.getCost());
        holder.desc.setText(categoryProductModel.getDesc());
        holder.productwish.setImageResource(R.drawable.wishlist_icon);
        Glide.with(holder.image).load(categoryProductModel.getImageurl()).into(holder.image);
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(holder.image.getContext(), ProductDetailsActivity.class);
                i.putExtra("id",getRef(position).getKey());
                (holder.image.getContext()).startActivity(i);
            }
        });

        holder.productwish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.productwish.setImageResource(R.drawable.wishborder);
                String u= FirebaseAuth.getInstance().getCurrentUser().getUid();
                FirebaseDatabase.getInstance().getReference("users").child(u).child("wishlist")
                        .child(getRef(position).getKey()).removeValue();
                Toast.makeText(holder.productwish.getContext(),"Removed from wishList",Toast.LENGTH_SHORT).show();
            }
        });

    }

    @NonNull
    @Override
    public WishViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.categoryproduct_viewholder,parent,false);
        return new WishViewHolder(view,onListclickListener);
    }

    public class WishViewHolder extends RecyclerView.ViewHolder{
        TextView productName;
        TextView desc;
        TextView cost;
        ImageView image;
        ImageView addbtn,productwish;
        WishListAdapter.OnListclickListener onListclickListener;

        public WishViewHolder(@NonNull View itemView,final  WishListAdapter.OnListclickListener onListclickListener) {
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
                    addbtn.setImageResource(R.drawable.profile_pic);
                    onListclickListener.addedToCart(getAdapterPosition());
                }
            });
        }
    }
    public interface OnListclickListener{
        void addedToCart(int pos);
    }
}
