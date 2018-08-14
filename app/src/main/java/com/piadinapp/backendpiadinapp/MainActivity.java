package com.piadinapp.backendpiadinapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.piadinapp.backendpiadinapp.utility.CustomRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String URL_GET_TIMBRI    = "http://piadinapp.altervista.org/get_timbri.php";
    private static final String URL_UPDATE_TIMBRI = "http://piadinapp.altervista.org/update_timbri.php";

    private static final String KEY_UPDATE_REQUEST_SUCCESS = "success";
    private static final String KEY_UPDATE_REQUEST_MESSAGE = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button scanQRBtn = (Button) findViewById(R.id.btnScanQr);
        scanQRBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initiateQRScan();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
            //Toast.makeText(this,"Scanned: " + result.getContents(),Toast.LENGTH_SHORT).show();
            updateUserTimbriRequest(result.getContents());
        }
    }

    //PRIVATE FUNCTIONS----------------------------------------------
    private void initiateQRScan()
    {
        new IntentIntegrator(this).initiateScan();
    }

    private void updateUserTimbriRequest(final String emailUser)
    {
        Map<String,String> params = new HashMap<>();
        params.put("email",emailUser);

        RequestQueue queue = Volley.newRequestQueue(this);

        JSONObject jsonParameters = new JSONObject(params);
        Log.d("JSON",jsonParameters.toString());

        //Make POST request
        CustomRequest jsonObjRequest = new CustomRequest(Request.Method.POST, URL_UPDATE_TIMBRI, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("UPDATE_TIMBRI_RESPONSE",response.toString());

                        try{
                            boolean success = response.getBoolean("success");
                            Log.d("UPDATE_TIMBRI_2",success ? "true" : "false");

                        }catch(JSONException e){
                            e.fillInStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof TimeoutError){
                            Toast.makeText(getApplicationContext(), "TimeOut Error!", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof NoConnectionError) {
                            Toast.makeText(getApplicationContext(), "NoConnection Error!", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof AuthFailureError) {
                            Toast.makeText(getApplicationContext(), "Authentication Error!", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof ServerError) {
                            Toast.makeText(getApplicationContext(), "Server Side Error!", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof NetworkError) {
                            Toast.makeText(getApplicationContext(), "Network Error!", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof ParseError) {
                            Toast.makeText(getApplicationContext(), "Parse Error!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        queue.add(jsonObjRequest);
    }
    //---------------------------------------------------------------
}