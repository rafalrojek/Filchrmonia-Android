package pl.edu.wat.wcy.isi.tim.filharmoniaapp.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.model.AndroidTicket;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.R;

public class TicketDetailsActivity extends AppCompatActivity {

    AndroidTicket ticket;
    @BindView(R.id.ticketViewName) TextView ticketViewName;
    @BindView(R.id.ticketViewDate) TextView ticketViewDate;
    @BindView(R.id.ticketViewCost) TextView ticketViewCost;
    @BindView(R.id.ticketViewDiscount) TextView ticketViewDiscount;
    @BindView(R.id.ticketViewRoom) TextView ticketViewRoom;
    @BindView(R.id.ticketViewRow) TextView ticketViewRow;
    @BindView(R.id.ticketViewPosition) TextView ticketViewPosition;


    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_details);
        ButterKnife.bind(this);
        ticket = (AndroidTicket) getIntent().getSerializableExtra("ticket");

        System.out.println(ticket);

        ticketViewName.setText(ticket.getConcertTitle());
        ticketViewDate.setText(ticket.getDate());
        ticketViewCost.setText(String.format("%.2f",ticket.getCost()) + " zł");
        ticketViewDiscount.setText(ticket.getDiscount());
        ticketViewRoom.setText(ticket.getConcertRoom());
        ticketViewRow.setText(""+ ticket.getRow());
        ticketViewPosition.setText(""+ticket.getPosition());
    }
}
