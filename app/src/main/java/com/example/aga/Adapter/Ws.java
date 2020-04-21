package com.example.aga.Adapter;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class Ws {

    private static RequestQueue queue;

    public static RequestQueue q(Context context){
        if( queue == null ) queue = Volley.newRequestQueue(context);
        return queue;
    }

}
