package com.piadinapp.backendpiadinapp.utility;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class OnlineHelper {
    private static final String URL_UPDATE_TIMBRI = "http://piadinapp.altervista.org/update_timbri.php";
    private static final String URL_GET_ORDERS = "http://piadinapp.altervista.org/get_orders_jimmy.php";

    private static final String EMAIL_FIELD = "email";
    private static final String TIMBRI_FIELD = "timbri";

    private Response.ErrorListener mErrorListener;
    private Context mContext;

    public OnlineHelper(final Context context)
    {
        mContext = context;

        mErrorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError){
                    Toast.makeText(mContext, "TimeOut Error!", Toast.LENGTH_SHORT).show();
                }else if (error instanceof NoConnectionError) {
                    Toast.makeText(mContext, "NoConnection Error!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(mContext, "Authentication Error!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(mContext, "Server Side Error!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(mContext, "Network Error!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(mContext, "Parse Error!", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    public void updateUserTimbri(final String userEmail,final int numTimbri,
                                 final GenericCallback callback)
    {
        Map<String,String> params = new HashMap<>();
        params.put(EMAIL_FIELD,userEmail);
        params.put(TIMBRI_FIELD,String.valueOf(numTimbri));

        //Create response listener
        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                callback.onSuccess(response);
            }
        };

        CustomRequest jsonRequest = new CustomRequest(Request.Method.POST,
                                                      URL_UPDATE_TIMBRI,
                                                      params,
                                                      responseListener,
                                                      mErrorListener);

        VolleySingleton.getInstance(mContext).addToRequestQueue(jsonRequest);
    }

    public void getOrderList(final GenericCallback callback)
    {
        Map<String,String> params = new HashMap<>();

        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                callback.onSuccess(response);
            }
        };

        CustomRequest jsonRequest = new CustomRequest(Request.Method.POST,
                                                      URL_GET_ORDERS,
                                                      params,
                                                      responseListener,
                                                      mErrorListener);

        VolleySingleton.getInstance(mContext).addToRequestQueue(jsonRequest);
    }
}
