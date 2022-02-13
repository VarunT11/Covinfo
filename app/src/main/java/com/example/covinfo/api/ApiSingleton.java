package com.example.covinfo.api;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;

public class ApiSingleton {

    public interface FetchApiInterface {
        void onApiFetchComplete(boolean success, String result);
    }

    private static final HashMap<Context, ApiSingleton> singletonHashMap = new HashMap<>();

    public static ApiSingleton getInstance(Context context) {
        if (!singletonHashMap.containsKey(context)) {
            singletonHashMap.put(context, new ApiSingleton(context));
        }
        return singletonHashMap.get(context);
    }

    private final Context context;

    private ApiSingleton(Context context) {
        this.context = context;
    }

    public void sendGetRequest(String url, FetchApiInterface fetchApiInterface) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                url,
                response -> fetchApiInterface.onApiFetchComplete(true, response),
                error -> fetchApiInterface.onApiFetchComplete(false, error.getMessage())
        );
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        );
        Volley.newRequestQueue(context).add(stringRequest);
    }

}
