package com.example.questionnaire;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.questionnaire.fragments.fragmentDashboard;
import com.example.questionnaire.fragments.fragmentFaq;
import com.example.questionnaire.fragments.fragmentSetting;
import com.example.questionnaire.global.global;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.jsoup.select.Evaluator;

import de.hdodenhof.circleimageview.CircleImageView;

public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private CircleImageView circleImageViewUserImage;
    private TextView tvNavigationEmailUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        navigationView = findViewById(R.id.nav_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        loadFragment(new fragmentDashboard());
        loadingProfileImage();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mdashboard:
                loadFragment(new fragmentDashboard());
                break;
            case R.id.mfaq:
                loadFragment(new fragmentFaq());
                break;
            case R.id.msetting:
                loadFragment(new fragmentSetting());
                break;
            case R.id.mlogout:
                global.user = null;
                startActivity(new Intent(this, MainActivity.class));
                Toast.makeText(this, "Logged Out", Toast.LENGTH_SHORT).show();
                break;
        }
        return false;
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.detach(fragment);
        transaction.attach(fragment);
        transaction.commit();
    }

    public void loadingProfileImage() {
        View hView = navigationView.getHeaderView(0);
        tvNavigationEmailUser = hView.findViewById(R.id.navigation_emailuser);
        tvNavigationEmailUser.setText(global.user.getEmail());
        if (global.user.getProfile_image() != null) {
            circleImageViewUserImage = hView.findViewById(R.id.navigation_imguser);
            Picasso.get().load(global.user.getProfile_image()).into(circleImageViewUserImage);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}