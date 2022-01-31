package com.example.covinfo.api;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.covinfo.interfaces.FetchApiInterface;

import java.util.HashMap;

public class ApiSingleton {

    private Context context;

    private ApiSingleton(Context context) {
        this.context = context;
    }

    private static HashMap<Context, ApiSingleton> singletonHashMap = new HashMap<>();

    public static ApiSingleton getInstance(Context context) {
        if (!singletonHashMap.containsKey(context)) {
            ApiSingleton apiSingleton = new ApiSingleton(context);
            singletonHashMap.put(context, apiSingleton);
        }
        return singletonHashMap.get(context);
    }

    public void sendGetRequest(String url, FetchApiInterface fetchApiInterface) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                url,
                response -> fetchApiInterface.onApiFetchComplete(true, response),
                error -> fetchApiInterface.onApiFetchComplete(false, error.getLocalizedMessage())
        );
        Volley.newRequestQueue(context).add(stringRequest);
    }

}
