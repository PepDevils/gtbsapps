package com.example.dezvezesdez.getbus_android;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class Activity_Formulario extends Activity_Bilheteira {

    private String price;
    private String id;

    private TextView tv_price_pay;
    private TextView tv_id_ticket;

    private Bilhete ticket;

    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        UI_menu_inflater();

        price = getIntent().getStringExtra("tag_price");
        id = getIntent().getStringExtra("tag_id");
        ticket = new Bilhete(id,price);
        price = ticket.getTicket_price();
        id = ticket.getTicket_id();
        tv_price_pay = (TextView) findViewById(R.id.tv_price_pay);
        tv_price_pay.setText(price);
        tv_id_ticket = (TextView) findViewById(R.id.tv_id_ticket);
        tv_id_ticket.setText(id);





    }


    private void UI_menu_inflater(){

        appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        getSupportActionBar().setIcon(null);

        appBarLayout.addOnOffsetChangedListener(this);

        // collapsingToolbarLayout.setTitle(Title);
        collapsingToolbarLayout.setTitle(null);
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(this, Activity_Bilheteira.class);
        startActivity(i);
    }
}