package com.example.deliverroot;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deliverroot.models.Categorymodel;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    public CategoryAdapter(ArrayList<Categorymodel> items) {
        this.items = items;
    }

    private ArrayList<Categorymodel> items;

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.category_viewholder,parent,false);
        CategoryViewHolder holder=new CategoryViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {

        Categorymodel currentItem= items.get(position);
        holder.imageV.setImageResource(currentItem.getImage());
        holder.nameV.setText(currentItem.getName());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {

        TextView nameV;
        ImageView imageV;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            imageV=itemView.findViewById(R.id.categoryPic);
            nameV=itemView.findViewById(R.id.categoryName);

        }
    }
}
