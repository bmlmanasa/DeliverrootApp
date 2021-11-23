package com.example.deliverroot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.deliverroot.models.CartModel;
import com.example.deliverroot.models.CategoryProductModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class ProductDetailsActivity extends AppCompatActivity {

    ImageView img,cartic,backbtn,plus,minus;
    TextView title,dis,pcost,count;
    String id="";
    int countn=1;
    Button Addcart;
    FirebaseAuth fAuth;
    FirebaseUser user;
    CategoryProductModel product;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productdetailspage);
    
       backbtn=findViewById(R.id.backbtn);
      

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProductDetailsActivity.this,CategoryProducts.class));
            }
        });

        cartic=findViewById(R.id.cart_icon);
        cartic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProductDetailsActivity.this,CartListActivity.class));
            }
        });

        title=findViewById(R.id.Productname);
        dis=findViewById(R.id.Productdesc);
        img=findViewById(R.id.product_image);
        pcost=findViewById(R.id.pprice);
        count=findViewById(R.id.count);
        plus=findViewById(R.id.plus);
        minus=findViewById(R.id.minus);

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countn++;
                count.setText(String.valueOf(countn));
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countn--;
                if(countn<=0){
                    count.setText("1");
                    countn=1;
                }
                else{
                    count.setText(String.valueOf(countn));
                }
            }
        });

        Intent data=getIntent();
        id=data.getStringExtra("id");
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Products").child(id);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    product=dataSnapshot.getValue(CategoryProductModel.class);
                    title.setText(product.getProductName());
                    dis.setText(product.getDesc());
                    pcost.setText("₹"+product.getCost());
                    Glide.with(ProductDetailsActivity.this).load(product.getImageurl()).into(img);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Addcart=findViewById(R.id.cartbutton);
        Addcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart();
            }
        });
    }

    private void addToCart() {
        fAuth= FirebaseAuth.getInstance();
        user=fAuth.getCurrentUser();
        String UserID=user.getUid();
        final DatabaseReference d=FirebaseDatabase.getInstance().getReference("users").child(UserID);
        final HashMap<String, Object> item = new HashMap<>();
        item.put("id",product.getId());
        item.put("imageurl",product.getImageurl());
        item.put("name",product.getProductName());
        item.put("price",product.getCost());
        item.put("quantity",count.getText().toString());
        d.child("cartlist").child(product.getId()).updateChildren(item).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(ProductDetailsActivity.this,"added",Toast.LENGTH_SHORT).show();
                Addcart.setText("Added to Cart");
            }
        });
    }

}
