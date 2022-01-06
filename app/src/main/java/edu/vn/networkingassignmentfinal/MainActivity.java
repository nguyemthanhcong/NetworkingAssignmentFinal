package edu.vn.networkingassignmentfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;
import edu.vn.networkingassignmentfinal.activiti.LoginActivity;
import edu.vn.networkingassignmentfinal.fragment.CategoryFragment;
import edu.vn.networkingassignmentfinal.fragment.ChangePasswordFragment;
import edu.vn.networkingassignmentfinal.fragment.HomeFragment;
import edu.vn.networkingassignmentfinal.fragment.MyCartFragment;
import edu.vn.networkingassignmentfinal.fragment.MyOrderFragment;
import edu.vn.networkingassignmentfinal.fragment.NewProductFragment;
import edu.vn.networkingassignmentfinal.fragment.OfferFragment;
import edu.vn.networkingassignmentfinal.fragment.ProfileFragment;
import edu.vn.networkingassignmentfinal.models.UserModel;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final int FRAGMENT_HOME = 0;
    private static final int FRAGMENT_CATEGORY = 1;
    private static final int FRAGMENT_OFFERS = 2;
    private static final int FRAGMENT_NEW_PRODUCT = 3;
    private static final int FRAGMENT_MY_ORDER = 4;
    private static final int FRAGMENT_MY_CART = 5;
    private static final int FRAGMENT_PROFILE = 6;
    private static final int FRAGMENT_CHANGE_PASSWORD = 7;

    //This code is made by Cong, 06/01/2022


    private int mCurrentFragment = FRAGMENT_HOME;


    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawerLayout;
    FirebaseAuth auth;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.drawer_layout);

        Toolbar toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        replaceFragment(new HomeFragment());
        navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_category, R.id.nav_profile,R.id.nav_offers,R.id.nav_new_product,
                R.id.nav_my_orders,R.id.nav_my_cart)
                .setDrawerLayout(drawerLayout)
                .build();

        View headerView = navigationView.getHeaderView(0);
        TextView headerName = headerView.findViewById(R.id.tvHeaderName);
        TextView headerEmail = headerView.findViewById(R.id.tvHeaderEmail);

        CircleImageView headerImg = headerView.findViewById(R.id.imgHeader);



        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserModel userModel = snapshot.getValue(UserModel.class);

                headerName.setText(userModel.getName());
                headerEmail.setText(userModel.getEmail());
                Glide.with(MainActivity.this).load(userModel.getProfileImg()).into(headerImg);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home){
            if (mCurrentFragment != FRAGMENT_HOME){
                replaceFragment(new HomeFragment());
                mCurrentFragment = FRAGMENT_HOME;
            }
        }else if (id == R.id.nav_category){
            if (mCurrentFragment != FRAGMENT_CATEGORY){
                replaceFragment(new CategoryFragment());
                mCurrentFragment = FRAGMENT_CATEGORY;
            }
        }else if (id == R.id.nav_offers){
            if (mCurrentFragment != FRAGMENT_OFFERS){
                replaceFragment(new OfferFragment());
                mCurrentFragment = FRAGMENT_OFFERS;
            }
        }else if (id == R.id.nav_new_product){
            if (mCurrentFragment != FRAGMENT_NEW_PRODUCT){
                replaceFragment(new NewProductFragment());
                mCurrentFragment = FRAGMENT_NEW_PRODUCT;
            }
        }else if (id == R.id.nav_my_orders){
            if (mCurrentFragment != FRAGMENT_MY_ORDER){
                replaceFragment(new MyOrderFragment());
                mCurrentFragment = FRAGMENT_MY_ORDER;
            }
        }else if (id == R.id.nav_my_cart){
            if (mCurrentFragment != FRAGMENT_MY_CART){
                replaceFragment(new MyCartFragment());
                mCurrentFragment = FRAGMENT_MY_CART;
            }
        }else if (id == R.id.nav_profile){
            if (mCurrentFragment != FRAGMENT_PROFILE){
                replaceFragment(new ProfileFragment());
                mCurrentFragment = FRAGMENT_PROFILE;
            }
        }else if (id == R.id.nav_change_password){
            if (mCurrentFragment != FRAGMENT_CHANGE_PASSWORD){
                replaceFragment(new ChangePasswordFragment());
                mCurrentFragment = FRAGMENT_CHANGE_PASSWORD;
            }
        }else if (id == R.id.logout){
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }


        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

    private void replaceFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.commit();
    }


}