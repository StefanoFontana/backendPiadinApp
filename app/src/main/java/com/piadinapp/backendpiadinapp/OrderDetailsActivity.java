package com.piadinapp.backendpiadinapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.piadinapp.backendpiadinapp.adapters.OrderItemAdapter;
import com.piadinapp.backendpiadinapp.model.Ordine;
import com.piadinapp.backendpiadinapp.model.OrdineItem;
import com.piadinapp.backendpiadinapp.utility.GenericCallback;
import com.piadinapp.backendpiadinapp.utility.JSONHelper;
import com.piadinapp.backendpiadinapp.utility.OnlineHelper;

import org.json.JSONObject;

import java.util.ArrayList;

public class OrderDetailsActivity extends AppCompatActivity {
    private static final String SEPARATOR_QR_CODE = ";";

    private RecyclerView mRvItems;
    private OrderItemAdapter mItemsAdapter;
    private Ordine mOrdine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_details_order);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        mOrdine = intent.getParcelableExtra("selected_order");
        if(mOrdine == null)
            return;

        String finalText;
        TextView id = (TextView) findViewById(R.id.tvId);
        finalText = getResources().getString(R.string.lbl_id_text) + String.valueOf(mOrdine.getId());
        id.setText(finalText);

        TextView email = (TextView) findViewById(R.id.tvEmail);
        finalText = getResources().getString(R.string.lbl_email_text) + mOrdine.getEmail();
        email.setText(finalText);

        TextView phone = (TextView) findViewById(R.id.tvPhone);
        finalText = getResources().getString(R.string.lbl_phone_text) + mOrdine.getPhone();
        phone.setText(finalText);

        TextView date = (TextView) findViewById(R.id.tvDate);
        finalText = getResources().getString(R.string.lbl_date_text) + mOrdine.getRegisterDate();
        date.setText(finalText);

        TextView fascia = (TextView) findViewById(R.id.tvFascia);
        finalText = getResources().getString(R.string.lbl_fascia_text) + mOrdine.getFasciaNumber();
        fascia.setText(finalText);

        TextView color = (TextView) findViewById(R.id.tvColor);
        finalText = getResources().getString(R.string.lbl_color_text) + String.valueOf(mOrdine.getFasciaColor());
        color.setText(finalText);

        ArrayList<OrdineItem> itemsList = mOrdine.getOrderItems();

        TextView numPiade = (TextView) findViewById(R.id.tvNumPiadine);
        int totPiade = itemsList.size();
        finalText = getResources().getString(R.string.lbl_piadine_num_text) + String.valueOf(totPiade);
        numPiade.setText(finalText);

        //Fill item recycler view
        mRvItems = findViewById(R.id.rvPiadineList);
        mRvItems.setLayoutManager(new LinearLayoutManager(this));
        mItemsAdapter = new OrderItemAdapter();
        mItemsAdapter.updateData(itemsList);
        mRvItems.setAdapter(mItemsAdapter);

        //Scan qr code button
        Button scanBtn = (Button) findViewById(R.id.btnScanQr);
        scanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                beginQRScan();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);

        if(result == null) {
            super.onActivityResult(requestCode,resultCode,data);
            return;
        }

        if(result.getContents() == null) {
            Toast.makeText(this,"QR scan cancelled",Toast.LENGTH_SHORT).show();
        } else {
            String[] resultArray = result.getContents().split(SEPARATOR_QR_CODE);
            //updateUserTimbriRequest(resultArray[0],resultArray[1]);
            updateTimbriRequest(resultArray[0]);
        }
    }

    //PRIVATE FUNCTIONS------------------------------------------------------------
    private void beginQRScan()
    {
        new IntentIntegrator(this).initiateScan();
    }

    private void updateTimbriRequest(String userEmail)
    {
        GenericCallback updTimbriCallback = new GenericCallback() {
            @Override
            public void onSuccess(JSONObject resultData)
            {
                boolean res = JSONHelper.getSuccessResponseValue(resultData);

                String msg;
                if(res) {
                    msg = "Dati timbri aggiornati con successo";
                } else {
                    msg = JSONHelper.getStringFromObj(resultData,"message");
                }

                Toast.makeText(OrderDetailsActivity.this,msg,Toast.LENGTH_SHORT).show();
            }
        };

        OnlineHelper onlineHelper = new OnlineHelper(this);
        onlineHelper.updateUserTimbri(userEmail,mOrdine.getOrderItems().size(),updTimbriCallback);
    }
    //-----------------------------------------------------------------------------
}
