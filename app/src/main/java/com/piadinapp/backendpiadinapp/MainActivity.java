package com.piadinapp.backendpiadinapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.piadinapp.backendpiadinapp.frags.OrderListFragment;
import com.piadinapp.backendpiadinapp.model.Ordine;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar mToolbar;
    private OrderListFragment mFragOrderList;

    private FragmentManager mFragManager;
    private FragmentTransaction mFragTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.nav_orders);
        navigationView.setNavigationItemSelectedListener(this);

        //Add order list frag
        mFragOrderList = new OrderListFragment();
        mFragManager = getFragmentManager();
        mFragTransaction = mFragManager.beginTransaction();
        mFragTransaction.replace(R.id.flFragContainer,mFragOrderList,"FragOrderList");
        mFragTransaction.commit();

        //Create fragment frag
        //todo...
    }

    @Override
    public void onStart()
    {
        super.onStart();

        //Test orders
        Ordine test1 = new Ordine(1,"stefon1992@gmail.com","34237374406","22/03/2019",5.5f,"Pippo","Nota1","3",1);
        Ordine test2 = new Ordine(2,"stefon1992@gmail.com","34237374406","01/09/2018",3f,"Pippo2","Nota2","5",2);
        ArrayList<Ordine> list = new ArrayList<>();
        list.add(test1);
        list.add(test2);
        mFragOrderList.onOrderListChanged(list);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(id == R.id.nav_orders) {
            //Show order list frag
        } else if(id == R.id.nav_fasce) {
            //Show fasce frag
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
