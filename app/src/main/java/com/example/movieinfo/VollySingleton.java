package com.example.movieinfo;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VollySingleton {

    private RequestQueue requestQueue;
    private static VollySingleton mInstance;

    private VollySingleton(Context context){
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public static synchronized VollySingleton getmInstance(Context context){

        if (mInstance == null){
            mInstance = new VollySingleton(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue(){
        return requestQueue;
    }
}
