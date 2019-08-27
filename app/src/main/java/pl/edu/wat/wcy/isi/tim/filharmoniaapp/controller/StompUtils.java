package pl.edu.wat.wcy.isi.tim.filharmoniaapp.controller;

import android.annotation.SuppressLint;
import android.util.Log;

import io.reactivex.disposables.Disposable;
import ua.naiksoftware.stomp.StompClient;

class StompUtils {
	@SuppressLint("CheckResult")
	static Disposable lifecycle(StompClient stompClient) {
		return stompClient.lifecycle().subscribe(lifecycleEvent -> {
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
