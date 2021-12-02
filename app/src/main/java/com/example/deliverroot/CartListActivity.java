package com.example.deliverroot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deliverroot.models.CartModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.deliverroot.CartListAdapter.totalPrice;

public class CartListActivity extends AppCompatActivity implements DatasetListener {

    FirebaseAuth fAuth;
    FirebaseUser user;
    RecyclerView wishView;
    CartListAdapter Adapter;
    TextView Totaltxt,deliveryTxt,totalAmt;
    DatabaseReference d;
    String UserID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_list);
        wishView=findViewById(R.id.wishlistview);
        Totaltxt=findViewById(R.id.totalFeeTxt);
        deliveryTxt=findViewById(R.id.deliveryTxt);
        totalAmt=findViewById(R.id.totalTxt);
        Toolbar t1=findViewById(R.id.toolbarw);
        setSupportActionBar(t1);
        t1.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CartListActivity.this,HomeActivity.class));
            }
        });

        fAuth= FirebaseAuth.getInstance();
        user=fAuth.getCurrentUser();
        UserID=user.getUid();
         d=FirebaseDatabase.getInstance().getReference("users").child(UserID);


        addCartItems();

    }



    private void addCartItems() {
        FirebaseRecyclerOptions<CartModel> options =
                new FirebaseRecyclerOptions.Builder<CartModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("users").child(UserID).child("cartlist"), CartModel.class)
                        .build();

       wishView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        Adapter =new CartListAdapter(options,this);
        wishView.setAdapter(Adapter);

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
    public void setValues(int totalprice) {
        Totaltxt.setText("₹"+String.valueOf(totalprice));
        deliveryTxt.setText("₹"+String.valueOf(30));
        int total=totalprice+30;
        totalAmt.setText("₹"+String.valueOf(total));
    }


//    private void calculatePrice() {
//        int totalprice=0;
//        Toast.makeText(CartListActivity.this, "incalculate"+cartlistItems.size(), Toast.LENGTH_SHORT).show();
//        for(int i=0;i<cartlistItems.size();i++){
//            CartModel c=cartlistItems.get(i);
//            int t= ((Integer.valueOf(c.getPrice())))* Integer.valueOf(c.getQuantity());
//            totalprice += t;
//        }
//        Totaltxt.setText("₹"+String.valueOf(totalprice));
//        deliveryTxt.setText("₹"+String.valueOf(30));
//        int total=totalprice+30;
//        totalAmt.setText("₹"+String.valueOf(total));
//    }
}
