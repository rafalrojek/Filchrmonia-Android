package pl.edu.wat.wcy.isi.tim.filharmoniaapp.Controller;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.Database.DatabaseSingleton;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.Model.AndroidTicket;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.R;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.RequestSingleton;

public class PaymentActivity extends AppCompatActivity {
    @BindView(R.id.WebView) WebView webView;
    @BindView(R.id.PaymentProgressBar) ProgressBar progressBar;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        ButterKnife.bind(this);

        String url = (String) getIntent().getSerializableExtra("PayPalURL");

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                webView.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);

                //Płatnośc anulowana
                if (url.contains("caneled")) {
                    Toast.makeText(PaymentActivity.this, R.string.PaymentCanelled, Toast.LENGTH_SHORT).show();
                    finish();
                }
                else if (url.contains("success")) {
                    Toast.makeText(PaymentActivity.this, R.string.PaymentSuccess, Toast.LENGTH_SHORT).show();

                    //Wyślij informację ze płatnośc zakończona i odbierz bilet
                    String send = RequestSingleton.address + "/paypal/payment/complete/"+url.substring(38);
                    Type listType = new TypeToken<List<AndroidTicket>>(){}.getType();
                    RequestSingleton.getInstance(getApplicationContext()).addToRequestQueue(new JsonObjectRequest(
                            Request.Method.POST, send, null,
                            response -> {
                                ArrayList<AndroidTicket> tickets = new ArrayList<>(new Gson().fromJson(response.toString(), listType));
                                AndroidTicket[] ticketsArray = (AndroidTicket[]) tickets.toArray();
                                DatabaseSingleton.get(getApplicationContext()).insertTickets(ticketsArray);
                                startActivity(new Intent(PaymentActivity.this, TicketsActivity.class));
                            }, error -> {
                        Toast.makeText(getApplicationContext(), R.string.connectionError, Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    ));
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                webView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);

            }
        });
        webView.loadUrl(url); //UTUtaj adres do paypal
    }
}
