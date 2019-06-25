package pl.edu.wat.wcy.isi.tim.filharmoniaapp.view.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.VolleyError;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.controller.PaymentController;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.R;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.model.model.Purchase;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.view.views.PayPalWebView;

public class PaymentActivity extends AppCompatActivity {
    @BindView(R.id.WebView) WebView webView;
    @BindView(R.id.PaymentProgressBar) ProgressBar progressBar;
    private boolean notPaid = true;
    private PaymentController controller;


    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        ButterKnife.bind(this);

        this.controller = new PaymentController(this);
        switchViability(false);
        Purchase purchase = (Purchase) getIntent().getSerializableExtra("Purchase");
        controller.makePayment(purchase);
    }



    public void switchViability (boolean isWebView) {
        runOnUiThread(() -> {
            if (isWebView) {
                webView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            } else {
                webView.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    public void paymentCanceled() {
        Toast.makeText(PaymentActivity.this, R.string.PaymentCanelled, Toast.LENGTH_SHORT).show();
        finish();
    }

    public void paymentSuccess(String url) {
        if (notPaid) {
            notPaid = false;
            Toast.makeText(PaymentActivity.this, R.string.PaymentSuccess, Toast.LENGTH_SHORT).show();
            controller.sendPayment(url);
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void onResponse(String url) {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.setWebViewClient(new PayPalWebView(this));
        webView.loadUrl(url);
    }


    public void onErrorResponse(VolleyError volleyError) {
        Toast.makeText(PaymentActivity.this, volleyError.getMessage(), Toast.LENGTH_LONG).show();
        finish();
    }
}
