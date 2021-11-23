package com.example.deliverroot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deliverroot.models.CartModel;
import com.example.deliverroot.models.CategoryProductModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class WishListActivity extends AppCompatActivity implements WishListAdapter.OnListclickListener{
    FirebaseAuth fAuth;
    FirebaseUser user;
    RecyclerView wishView2;
    WishListAdapter Adapter;
    String UserID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_list);

        wishView2=findViewById(R.id.wishview2);
        Toolbar t1=findViewById(R.id.toolbar10);
        setSupportActionBar(t1);
        t1.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WishListActivity.this,HomeActivity.class));
            }
        });

        fAuth= FirebaseAuth.getInstance();
        user=fAuth.getCurrentUser();
        UserID=user.getUid();

        DatabaseReference d= FirebaseDatabase.getInstance().getReference("users").child(UserID);

        addWishItems();

    }

    private void addWishItems() {

        FirebaseRecyclerOptions<CategoryProductModel> options =
                new FirebaseRecyclerOptions.Builder<CategoryProductModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("users").child(UserID).child("wishlist"),CategoryProductModel.class)
                        .build();
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false);
        wishView2.setLayoutManager(gridLayoutManager);
        Adapter =new WishListAdapter(options,this);
        wishView2.setAdapter(Adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Adapter.stopListening();
    }

    @Override
    public void addedToCart(int pos) {
        Toast.makeText(WishListActivity.this,"added",Toast.LENGTH_SHORT).show();
    }
}
