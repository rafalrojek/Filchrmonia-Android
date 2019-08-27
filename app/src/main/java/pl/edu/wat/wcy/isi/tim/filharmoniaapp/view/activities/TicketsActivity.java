package pl.edu.wat.wcy.isi.tim.filharmoniaapp.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.view.adapters.TicketListAdapter;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.controller.TicketController;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.model.entities.AndroidTicket;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.R;

public class TicketsActivity extends AppCompatActivity {

    @BindView(R.id.ticket_list) ListView listView;
    @BindView(R.id.downloadTickets) Button button;
    private TicketListAdapter adapter;
    private List<AndroidTicket> tickets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tickets);
        ButterKnife.bind(this);

        TicketController controller = new TicketController(this);

        tickets = controller.getTickets();

        adapter = new TicketListAdapter(this, tickets);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this::onItemClick);

        button.setOnClickListener(controller::downloadTickets);
    }


    public void updateTickets(List<AndroidTicket> tickets) {
        this.tickets.clear();
        this.tickets.addAll(tickets);
        adapter.notifyDataSetChanged();
    }

    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
    }

    private void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        AndroidTicket ticket = tickets.get(i);
        Intent intent = new Intent(TicketsActivity.this, TicketDetailsActivity.class);
        intent.putExtra("ticket", ticket);
        startActivity(intent);
    }
}
