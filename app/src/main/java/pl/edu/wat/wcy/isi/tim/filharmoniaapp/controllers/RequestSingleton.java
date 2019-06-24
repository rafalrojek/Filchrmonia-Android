package pl.edu.wat.wcy.isi.tim.filharmoniaapp.controllers;

import android.content.Context;
import android.content.res.AssetManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.io.InputStream;
import java.util.Properties;

public class RequestSingleton {

    public static String address;
    public static String wsaddress;
    public static String sendAdress;
    public static String registerAdress;
    private static RequestSingleton instance;
    private RequestQueue requestQueue;

    private RequestSingleton(Context context) {
        Properties properties = getMyProperties(context);
        address = properties.getProperty("REST");
        wsaddress = properties.getProperty("WS");
        sendAdress = properties.getProperty("wsSendMesssage");
        registerAdress = properties.getProperty("wsAddUser");

        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public static synchronized RequestSingleton getInstance(Context context) {
        if (instance == null) {
            instance = new RequestSingleton(context);
        }
        return instance;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        requestQueue.add(req);
    }

    private Properties getMyProperties(Context context){
        Properties properties = new Properties();
        try{
            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open("app.properties");
            properties.load(inputStream);

        }catch (Exception e){
            System.out.print(e.getMessage());
        }

        return properties;
    }
}
