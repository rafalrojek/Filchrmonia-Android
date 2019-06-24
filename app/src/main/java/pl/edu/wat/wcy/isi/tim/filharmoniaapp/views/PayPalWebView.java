package pl.edu.wat.wcy.isi.tim.filharmoniaapp.views;

import android.graphics.Bitmap;
import android.webkit.WebViewClient;

import pl.edu.wat.wcy.isi.tim.filharmoniaapp.activities.PaymentActivity;

public class PayPalWebView extends WebViewClient {

    PaymentActivity activity;

    public PayPalWebView(PaymentActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onPageStarted(android.webkit.WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        activity.switchViability(false);

        if (url.contains("http://localhost:4200/concerts")) {
            activity.paymentCanceled();
        }
        else if (url.contains("payment-complete")) {
            activity.paymentSuccess(url);
        }
    }

    @Override
    public void onPageFinished(android.webkit.WebView view, String url) {
        super.onPageFinished(view, url);
        activity.switchViability(!url.contains("payment-complete"));
    }
}
