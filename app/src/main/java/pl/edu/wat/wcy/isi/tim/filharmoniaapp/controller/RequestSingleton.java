package pl.edu.wat.wcy.isi.tim.filharmoniaapp.controller;

import android.content.Context;
import android.content.res.AssetManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.io.InputStream;
import java.util.Properties;

public class RequestSingleton {

    private static RequestSingleton instance;
    private RequestQueue requestQueue;
    private static Properties properties;

    private RequestSingleton(Context context) {
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    static synchronized RequestSingleton getInstance(Context context) {
        if (instance == null) instance = new RequestSingleton(context);
        return instance;
    }

    <T> void addToRequestQueue(Request<T> req) {
        requestQueue.add(req);
    }

    static String getProperty(String key, Context context) {
        if (properties == null) properties = getMyProperties(context);
        return properties.getProperty(key);
    }

    private static Properties getMyProperties(Context context){
        Properties properties = new Properties();
        try {
            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open("app.properties");
            properties.load(inputStream);
        }catch (Exception e) {
            System.out.print(e.getMessage());
        }

        return properties;
    }
}
