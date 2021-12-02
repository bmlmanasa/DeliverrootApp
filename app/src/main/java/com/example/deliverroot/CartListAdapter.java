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
import com.example.deliverroot.models.CartModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CartListAdapter extends FirebaseRecyclerAdapter<CartModel, CartListAdapter.WishViewHolder> {

    static int totalPrice=0;
    ArrayList<CartModel> cartlistItems;
    DatasetListener datasetListener;
    public CartListAdapter(@NonNull FirebaseRecyclerOptions<CartModel> options, DatasetListener datasetListener) {
        super(options);
        cartlistItems=new ArrayList<>();
        this.datasetListener=datasetListener;
        totalPrice=0;
    }
    @Override
    protected void onBindViewHolder(@NonNull final WishViewHolder holder, final int position, @NonNull final CartModel model) {
        holder.name.setText(model.getName());
        holder.price.setText("₹"+model.getPrice());
        cartlistItems.add(model);
        holder.quantity.setText(model.getQuantity());
        Glide.with(holder.image).load(model.getImageurl()).into(holder.image);

        int total = ((Integer.valueOf(model.getPrice())))* Integer.valueOf(model.getQuantity());
        totalPrice=totalPrice+total;
        datasetListener.setValues(totalPrice);
        holder.totalprice.setText("₹"+String.valueOf(total));
        holder.deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String u=FirebaseAuth.getInstance().getCurrentUser().getUid();
                FirebaseDatabase.getInstance().getReference("users").child(u).child("cartlist")
                    .child(getRef(position).getKey()).removeValue();
                notifyDataSetChanged();
               int total=changeTotal(getRef(position).getKey(),cartlistItems);
                totalPrice=totalPrice-total;
                datasetListener.setValues(totalPrice);
                Toast.makeText(holder.deletebtn.getContext(), "Deleted",Toast.LENGTH_SHORT).show();

            }
        });

        holder.addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(holder.addbtn.getContext(), ProductDetailsActivity.class);
                i.putExtra("id",getRef(position).getKey());

                (holder.addbtn.getContext()).startActivity(i);
            }
        });
        holder.subbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(holder.addbtn.getContext(), ProductDetailsActivity.class);
                i.putExtra("id",getRef(position).getKey());

                (holder.addbtn.getContext()).startActivity(i);
            }
        });



    }

    public int changeTotal(String id,ArrayList<CartModel> cartlistItems) {
        int amount=0;
        totalPrice=0;
        for(int i=0;i<cartlistItems.size();i++){
            CartModel c=cartlistItems.get(i);
            if(c.getId().equals(id)){
                amount=Integer.valueOf(c.getPrice());
                cartlistItems.remove(c);
            }
        }



        return amount;
    }

    @NonNull
    @Override
    public WishViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_viewholder,parent,false);
        return new WishViewHolder(view);
    }



    public class WishViewHolder extends RecyclerView.ViewHolder {

        TextView name,quantity,totalprice;
        TextView price;
        ImageView image,deletebtn,addbtn,subbtn;

        public WishViewHolder(@NonNull View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.name);
            quantity=itemView.findViewById(R.id.count);
            price=itemView.findViewById(R.id.pprice);
            image=itemView.findViewById(R.id.image);
            deletebtn=itemView.findViewById(R.id.deletebtn);
            totalprice=itemView.findViewById(R.id.totalprice);
            addbtn=itemView.findViewById(R.id.add_btn);
            subbtn=itemView.findViewById(R.id.subtract_btn);
        }
    }

}
