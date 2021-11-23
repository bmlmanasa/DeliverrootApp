package com.example.deliverroot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.deliverroot.models.Categorymodel;
import com.example.deliverroot.models.ProductModel;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Menu menu;
    TextView textView;
    private RecyclerView recyclerView,recyclerView2;
    private CategoryAdapter categoryAdapter;
    private ProductAdapter productAdapter;
    FirebaseAuth fAuth;
    FirebaseUser user;
    ImageView cartbtn;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fAuth=FirebaseAuth.getInstance();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        drawerLayout = findViewById(R.id.my_drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        cartbtn=findViewById(R.id.cartbutton);
        cartbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeActivity.this,CartListActivity.class);
                startActivity(intent);
            }
        });

        EditText searchView=findViewById(R.id.searchbar);


        searchView.setOnTouchListener(new View.OnTouchListener()
        {
            public boolean onTouch(View arg0, MotionEvent arg1)
            {
                startActivity(new Intent(HomeActivity.this,CategoryProducts.class));
                return false;
            }
        });

        Menu menu=navigationView.getMenu();

        navigationView.bringToFront();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();



        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

        //slider
        ImageSlider im_slider=findViewById(R.id.slider);
        List<SlideModel> slidemodel=new ArrayList<>();
        slidemodel.add(new SlideModel("https://cdn.shopify.com/s/files/1/0047/9730/0847/files/nurserylive-app-home-page-banner-balcony-and-terrace-garden-metal-stand-v3-diwali_670x400.jpg?v=1634826921"));
        slidemodel.add(new SlideModel("https://media1.thehungryjpeg.com/thumbs/800_3551365_tm0abyv3ek368v36nmxx1znieofuvyhv761oyoj5.jpg"));
        slidemodel.add(new SlideModel("https://previews.123rf.com/images/appler/appler1807/appler180700086/114963798-summer-sale-banner-with-paper-art-cut-out-plants-grass-branches-on-green-background-floral-design-fo.jpg"));
        slidemodel.add(new SlideModel("https://d3cif2hu95s88v.cloudfront.net/live-site-2016/2021/plant-online/welcome-plant-banner-mobile1_640x300.jpg"));
        im_slider.setImageList(slidemodel,true);

        //categories
        ArrayList<Categorymodel> item=new ArrayList<>();
        item.add(new Categorymodel(R.drawable.indoor_plant,"indoor plant"));
        item.add(new Categorymodel(R.drawable.leaf_plant,"outdoor plant"));
        item.add(new Categorymodel(R.drawable.flower_plant,"flower plant"));
        item.add(new Categorymodel(R.drawable.indoor_plant,"Bonsai plant"));
        item.add(new Categorymodel(R.drawable.indoor_plant,"medicinal plant"));

        recyclerView=findViewById(R.id.categoryView);
        categoryAdapter =new CategoryAdapter(item);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setAdapter(categoryAdapter);
        setName();

        ArrayList<ProductModel> item1=new ArrayList<>();
        item1.add(new ProductModel(R.drawable.gardening,"Snake Plant",3.2f,"$12"));
        item1.add(new ProductModel(R.drawable.plant_item01,"Money Plant",3.2f,"$5"));
        item1.add(new ProductModel(R.drawable.plant_item02,"sundra orr",3.2f,"$9"));
        item1.add(new ProductModel(R.drawable.item03_aloe_vera_plant,"Alovera",3.2f,"$10"));
        item1.add(new ProductModel(R.drawable.plant_item01,"Grape Ivy",3.2f,"$18"));


        recyclerView2=findViewById(R.id.recentView);
        productAdapter =new ProductAdapter(item1);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recyclerView2.setAdapter(productAdapter);


}

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()){
            case R.id.nav_home:
                break;
            case R.id.nav_profile:
                Intent intent=new Intent(HomeActivity.this,ProfileActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_collections:
                Intent intent2=new Intent(HomeActivity.this,BlogActivity.class);
                startActivity(intent2);
                break;
            case R.id.nav_wishlist:
                Intent intent5=new Intent(HomeActivity.this, WishListActivity.class);
                startActivity(intent5);
                break;
            case R.id.nav_cart:
                Intent intent3=new Intent(HomeActivity.this,CartListActivity.class);
                startActivity(intent3);
                break;

            case R.id.nav_logout:
                fAuth.signOut();

                startActivity(new Intent(HomeActivity.this,LoginActivity.class));
                break;

            case R.id.nav_share:
                Toast.makeText(this, "shared", Toast.LENGTH_SHORT).show();
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void openProducts(View v){
         startActivity(new Intent(HomeActivity.this,CategoryProducts.class));
    }

    public  void setName(){
       View header=navigationView.getHeaderView(0);
       final TextView name=header.findViewById(R.id.Productname);
       TextView email=header.findViewById(R.id.email);
        user=fAuth.getCurrentUser();
       final String UserID=user.getUid();

        DatabaseReference d=FirebaseDatabase.getInstance().getReference("users").child(UserID).child("username");
        d.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                name.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
       email.setText(user.getEmail());
    }

}
