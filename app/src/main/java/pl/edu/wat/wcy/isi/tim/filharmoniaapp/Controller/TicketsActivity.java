package pl.edu.wat.wcy.isi.tim.filharmoniaapp.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.Adapters.TicketListAdapter;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.Database.DatabaseSingleton;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.Model.AndroidTicket;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.R;

public class TicketsActivity extends AppCompatActivity {

    @BindView(R.id.ticket_list) ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tickets);
        ButterKnife.bind(this);

        List<AndroidTicket> tickets = DatabaseSingleton.get(getApplicationContext()).getTickets();

        TicketListAdapter adapter = new TicketListAdapter(this, tickets);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            AndroidTicket ticket = tickets.get(i);
            Intent intent = new Intent(TicketsActivity.this, ConcertActivity.class);
            intent.putExtra("ticket", ticket);
            startActivity(intent);
        });
    }

}
