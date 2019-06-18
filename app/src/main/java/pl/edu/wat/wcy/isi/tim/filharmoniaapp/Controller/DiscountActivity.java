package pl.edu.wat.wcy.isi.tim.filharmoniaapp.Controller;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.Adapters.DiscountAdapter;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.Model.Concert;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.Model.Purchase;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.Model.Ticket;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.R;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.RequestSingleton;

public class DiscountActivity extends AppCompatActivity {
    @BindView(R.id.ticketSum) TextView textView;
    @BindView(R.id.tickets_discounts) ListView listView;
    ArrayList<Ticket> tickets;
    Concert concert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount);
        ButterKnife.bind(this);

        tickets = (ArrayList<Ticket>) getIntent().getSerializableExtra("Tickets");
        concert = (Concert) getIntent().getSerializableExtra("Concert");

        listView.setAdapter(new DiscountAdapter(this, tickets, textView, concert.getTicketCost()));
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(DiscountActivity.this, SelectSeatActivity.class);
        i.putExtra("concert", concert);
        startActivity(i);
    }

    public void Purchase(View view) {

        @SuppressLint("HardwareIds")
        String android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);

        Purchase purchase = new Purchase(concert.getIdConcert(), android_id, tickets);

        String url = RequestSingleton.address + "/paypal/payment/make";
        JSONObject object = new JSONObject();

        try {
             object = new JSONObject(new Gson().toJson(purchase));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestSingleton.getInstance(getApplicationContext()).addToRequestQueue(new JsonObjectRequest(
                Request.Method.POST, url, object,
                response -> {
                    Intent i = new Intent(DiscountActivity.this, PaymentActivity.class);
                    System.out.println(response.toString());
                    i.putExtra("PayPalURL", response.toString());
                    startActivity(i);
                }, error -> {
                    Toast.makeText(getApplicationContext(), R.string.connectionError, Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                }
        ));
    }
}
