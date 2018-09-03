package com.piadinapp.backendpiadinapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.piadinapp.backendpiadinapp.frags.ContentRequestListener;
import com.piadinapp.backendpiadinapp.frags.OrderListFragment;
import com.piadinapp.backendpiadinapp.model.Ordine;
import com.piadinapp.backendpiadinapp.utility.GenericCallback;
import com.piadinapp.backendpiadinapp.utility.OnlineHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,ContentRequestListener {

    private static final String ORDER_EXTRA = "selected_order";

    private static final String JSON_ORDER_ARRAY       = "ordini";
    private static final String JSON_ID_FIELD          = "id";
    private static final String JSON_EMAIL_FIELD       = "email";
    private static final String JSON_PHONE_FIELD       = "phone";
    private static final String JSON_DATA_FIELD        = "data";
    private static final String JSON_PREZZO_FIELD      = "prezzo";
    private static final String JSON_DESCRIZIONE_FIELD = "descrizione";
    private static final String JSON_NOTE_FIELD        = "note";
    private static final String JSON_FASCIA_FIELD      = "fascia";
    private static final String JSON_COLOR_FIELD       = "colore_fascia";
    private static final String JSON_START_FIELD       = "orario_inizio";
    private static final String JSON_END_FIELD         = "orario_fine";

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
    }

    @Override
    public void onStart()
    {
        super.onStart();

        updateOrderList();
        ArrayList<Ordine> list = new ArrayList<>();
        mFragOrderList.onOrderListChanged(list);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        Fragment fragment = mFragManager.findFragmentById(R.id.flFragContainer);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if(fragment != null && !(fragment instanceof OrderListFragment)) {
            mFragTransaction = mFragManager.beginTransaction();
            mFragTransaction.remove(fragment);
            mFragTransaction.commit();
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
        }/* else if(id == R.id.nav_fasce) {
            //Show fasce frag
        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //FRAGMENTS CALLBACK---------------------------------------
    @Override
    //Called whe user selects an order from order list
    public void onOrderSelected(Ordine selected)
    {
        Intent detailsIntent = new Intent(this,OrderDetailsActivity.class);
        detailsIntent.putExtra(ORDER_EXTRA,selected);
        startActivity(detailsIntent);
    }

    @Override
    public void onOrderListRefreshRequest() {
        updateOrderList();
    }
    //--------------------------------------------------------

    private void updateOrderList()
    {
        GenericCallback genericCallback = new GenericCallback() {
            @Override
            public void onSuccess(JSONObject resultData) {
                mFragOrderList.onOrderListChanged(fillOrderList(resultData));
            }
        };

        OnlineHelper onlineHelper = new OnlineHelper(this);
        onlineHelper.getOrderList(genericCallback);
    }

    private ArrayList<Ordine> fillOrderList(JSONObject response)
    {
        ArrayList<Ordine> list = new ArrayList<>();

        int id,colore;
        String mail,phone,data,descrizione,nota,fascia, orarioInizio, orarioFine;
        double prezzo;

        Ordine ordine;
        try {
            JSONArray orderArray = response.getJSONArray(JSON_ORDER_ARRAY);
            for(int i = 0; i < orderArray.length(); i++) {
                JSONObject obj = orderArray.getJSONObject(i);
                id = obj.getInt(JSON_ID_FIELD);
                mail = obj.getString(JSON_EMAIL_FIELD);
                phone = obj.getString(JSON_PHONE_FIELD);
                data = obj.getString(JSON_DATA_FIELD);
                descrizione = obj.getString(JSON_DESCRIZIONE_FIELD);
                nota = obj.getString(JSON_NOTE_FIELD);
                prezzo = obj.getDouble(JSON_PREZZO_FIELD);
                fascia = obj.getString(JSON_FASCIA_FIELD);
                colore = obj.getInt(JSON_COLOR_FIELD);
                orarioInizio = obj.getString(JSON_START_FIELD);
                orarioFine = obj.getString(JSON_END_FIELD);

                ordine = new Ordine(id,mail,phone,data,(float)prezzo,
                        descrizione, nota, fascia, colore, orarioInizio, orarioFine);
                list.add(ordine);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return list;
        }

        return list;
    }
}
