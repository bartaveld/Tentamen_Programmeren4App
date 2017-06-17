package com.sleintrab.movierental.API;

import android.app.DownloadManager;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.sleintrab.movierental.BuildConfig;
import com.sleintrab.movierental.Volley.JSONObjectRequest;
import com.sleintrab.movierental.Volley.VolleyRequestQueue;

import org.json.JSONObject;

import es.dmoral.toasty.Toasty;

/**
 * Created by barti on 17-Jun-17.
 */

public class RentalAPI implements Response.ErrorListener, Response.Listener {

    private final String URL = BuildConfig.SERVER_URL + "rentals/";

    private RequestQueue mQueue;

    private static Context context;

    private OnRentalSuccess listener = null;

    public RentalAPI(Context context, OnRentalSuccess listener){
        this.context = context;
        this.listener = listener;

        mQueue = VolleyRequestQueue.getInstance(context.getApplicationContext()).getRequestQueue();
    }

    public void handInRental(int customerID, int inventoryID){

        final JSONObjectRequest req = new JSONObjectRequest(Request.Method.PUT,
                URL + customerID + "/" + inventoryID,
                new JSONObject(),
                this,
                this,
                context);
        req.setTag("HandInTAG");
        mQueue.add(req);
    }


    @Override
    public void onErrorResponse(VolleyError error) {
        error.printStackTrace();
        Toasty.error(context, "Failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(Object response) {
        listener.onRentalSuccess();
    }

    public interface OnRentalSuccess {
        void onRentalSuccess();
    }
}
