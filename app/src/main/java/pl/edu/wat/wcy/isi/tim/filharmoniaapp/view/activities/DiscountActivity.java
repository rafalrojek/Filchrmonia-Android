package pl.edu.wat.wcy.isi.tim.filharmoniaapp.view.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.VolleyError;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.view.adapters.DiscountAdapter;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.controller.DiscountController;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.model.model.Concert;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.model.model.Discount;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.model.model.Purchase;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.model.model.Ticket;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.R;

public class DiscountActivity extends AppCompatActivity {
    @BindView(R.id.ticketSum) TextView textView;
    @BindView(R.id.tickets_discounts) ListView listView;
    @BindView(R.id.buyTicketsButton) Button button;
    ArrayList<Ticket> tickets;
    Concert concert;
    DiscountController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount);
        ButterKnife.bind(this);

        tickets = (ArrayList<Ticket>) getIntent().getSerializableExtra("Tickets");
        concert = (Concert) getIntent().getSerializableExtra("Concert");
        controller = new DiscountController(this, tickets, concert.getTicketCost());
        controller.getDiscounts();
        button.setOnClickListener(this::Purchase);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(DiscountActivity.this, SelectSeatActivity.class);
        i.putExtra("concert", concert);
        startActivity(i);
    }

    public void configAdapter (ArrayList<Discount> discounts) {
        listView.setAdapter(new DiscountAdapter(this, tickets, discounts, controller));
    }

    public void setPrice (String sum) {
        String s = getApplicationContext().getString(R.string.sum) + " "
                + sum + getApplicationContext().getString(R.string.PLN);
        textView.setText(s);
    }

    public void Purchase(View view) {
        @SuppressLint("HardwareIds")
        String android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);

        Purchase purchase = new Purchase(concert.getIdConcert(), android_id, tickets);

        Intent i = new Intent(DiscountActivity.this, PaymentActivity.class);
        i.putExtra("Purchase", purchase);
        startActivity(i);
    }

    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
