package com.example.deliverroot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deliverroot.models.CartModel;
import com.example.deliverroot.models.CategoryProductModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Handler;

public class CategoryProducts extends AppCompatActivity implements CategoryProductsAdapter.OnListclickListener {

    RecyclerView dataView;
    private CategoryProductsAdapter categoryProductAdapter;
    ArrayList<CategoryProductModel> list,cartlist;
    CharSequence search="";
    FirebaseDatabase database_root;
    DatabaseReference d;
    FirebaseAuth fAuth;
    FirebaseUser user;
    String UserID;
    EditText searchView;
    FloatingActionButton addbtn;
    ImageView cartbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_products);

        dataView=findViewById(R.id.listview);
        searchView=findViewById(R.id.searchbar);

        Toolbar t1=findViewById(R.id.toolbar6);
        setSupportActionBar(t1);
        t1.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CategoryProducts.this,HomeActivity.class));
            }
        });
        fAuth=FirebaseAuth.getInstance();
        user=fAuth.getCurrentUser();
        UserID=user.getUid();
        d=FirebaseDatabase.getInstance().getReference("users").child(UserID);
        cartlist=new ArrayList<>();
        cartbtn=findViewById(R.id.cartbutton);
        cartbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CategoryProducts.this,CartListActivity.class);
                startActivity(intent);
            }
        });

       list=new ArrayList<>();

        list.add(addProducts("https://m.media-amazon.com/images/I/71PLgU+tE6L._AC_UL320_.jpg", "Plantmojo", "Golden Money plant is a good indoor plant as it can prosper even in low light. Removes indoor pollutants and is an air purifier. Can be placed in living room, balcony, bedroom or in hanging baskets.", "12","1"));
        list.add(addProducts("https://m.media-amazon.com/images/I/61DR0LUIaqL._AC_UL320_.jpg", "Bamboo plant", "Best For Home Decoration : Lucky Bamboo Plant is Perfect Choice for Indoors- Home Decor & Office Decor- table-top or office desks.", "17","2"));
        list.add(addProducts("https://m.media-amazon.com/images/I/71RNsBDMoML._AC_UL320_.jpg", "Areca Palm", "Best Indoor Air Purifier Plants for home and office desk and Removes Formaldehyde, Benzene & Carbon Monoxide", "10","3"));
        list.add(addProducts("https://rukminim1.flixcart.com/image/750/750/jbmjf680/plant-sapling/9/3/y/gsd-2017-s2-1-get-some-deals-original-imafyueuyuxhkcws.jpeg?q=20", "Snake plant", "Sansevieria trifasciata, commonly called snake plant or mother-in-law's tongue, is native to tropical western Africa. ", "9","4"));

        list.add(addProducts("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS5wWoh2RMRBtnelH5RvmGPVtKz8q40MDV1PQ&usqp=CAU", "Croton petra", "The Croton 'Petra' plant will produce multicolored leaves in moderate to bright, indirect sunlight.", "20","5"));
        list.add(addProducts("https://rukminim1.flixcart.com/image/750/750/kh0vonk0/plant-sapling/p/y/z/gsdlantoj-1830-1-edensfield-original-imafx45zkxmzjgz2.jpeg?q=20", "Peace lily", "Peace lilies are sturdy plants with glossy, dark green oval leaves that narrow to a point. ", "20","6"));


        categoryProductAdapter=new CategoryProductsAdapter(this,list,this);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false);
        dataView.setLayoutManager(gridLayoutManager);
        dataView.setAdapter(categoryProductAdapter);

        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                categoryProductAdapter.getfilter().filter(charSequence);
                search = charSequence;
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


    public CategoryProductModel addProducts(String imageurl, String productName, String desc, String cost, String id){
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("Products");
        CategoryProductModel c=new CategoryProductModel(imageurl,productName,desc,cost,id);
        ref.child(id).setValue(c);
        return c;
    }




    @Override
    public void addedToCart(int pos) {

        final CategoryProductModel c=list.get(pos);

        final CartModel item= new CartModel(c.getId(),c.getImageurl(),c.getProductName(),c.getCost(),"1");
        d.child("cartlist").child(c.getId()).setValue(item);
        Toast.makeText(CategoryProducts.this,"added",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addedToWish(int pos) {
        final CategoryProductModel c2=list.get(pos);
        Toast.makeText(CategoryProducts.this,"added"+c2.getProductName(),Toast.LENGTH_SHORT).show();
        d.child("wishlist").child(c2.getId()).setValue(c2);
    }

}
