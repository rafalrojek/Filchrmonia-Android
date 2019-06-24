package pl.edu.wat.wcy.isi.tim.filharmoniaapp.controllers;

import android.annotation.SuppressLint;
import android.util.Log;
import ua.naiksoftware.stomp.StompClient;

public class StompUtils {
	@SuppressLint("CheckResult")
    public static void lifecycle(StompClient stompClient) {
		stompClient.lifecycle().subscribe(lifecycleEvent -> {
			switch (lifecycleEvent.getType()) {
				case OPENED:
					Log.d("stomp", "Stomp connection opened");
					break;

				case ERROR:
					Log.e("stomp", "Error", lifecycleEvent.getException());
					break;

				case CLOSED:
					Log.d("stomp", "Stomp connection closed");
					break;
			}
		});
	}
}
