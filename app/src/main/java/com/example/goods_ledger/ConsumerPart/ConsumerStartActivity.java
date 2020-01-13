package com.example.goods_ledger.ConsumerPart;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.goods_ledger.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class ConsumerStartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumer_start);
        BottomNavigationView navView = findViewById(R.id.nav_consumer_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_consumer_home, R.id.navigation_consumer_products, R.id.navigation_consumer_queries, R.id.navigation_consumer_options)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_consumer_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);
    }

}
