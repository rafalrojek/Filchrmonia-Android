package pl.edu.wat.wcy.isi.tim.filharmoniaapp;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class RequestSingleton {

    public static String address = "http://10.0.2.2:8081";
    public static String wsaddress = "ws://10.0.2.2:8081/ws/websocket";
    private static RequestSingleton instance;
    private RequestQueue requestQueue;
    private static Context ctx;

    private RequestSingleton(Context context) {
        ctx = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized RequestSingleton getInstance(Context context) {
        if (instance == null) {
            instance = new RequestSingleton(context);
        }
        return instance;
    }

    private RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}
