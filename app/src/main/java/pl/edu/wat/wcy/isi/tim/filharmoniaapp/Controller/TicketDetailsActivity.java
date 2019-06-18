package pl.edu.wat.wcy.isi.tim.filharmoniaapp.Controller;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.Model.AndroidTicket;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.Model.Ticket;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.R;

public class TicketDetailsActivity extends AppCompatActivity {

    AndroidTicket ticket;
    @BindView(R.id.ticketViewName)
    TextView ticketViewName;
    @BindView(R.id.ticketViewDate)
    TextView ticketViewDate;
    @BindView(R.id.ticketViewCost)
    TextView ticketViewCost;
    @BindView(R.id.ticketViewDiscount)
    TextView ticketViewDiscount;
    @BindView(R.id.ticketViewRoom)
    TextView ticketViewRoom;
    @BindView(R.id.ticketViewRow)
    TextView ticketViewRow;
    @BindView(R.id.ticketViewPosition)
    TextView ticketViewPosition;


    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_details);
        ButterKnife.bind(this);
        ticket = (AndroidTicket) getIntent().getSerializableExtra("ticket");

        ticketViewName.setText(ticket.getName());
        ticketViewDate.setText(ticket.getDate());
        ticketViewCost.setText(String.format("%.2f",ticket.getCost()) + " zł");
        ticketViewDiscount.setText(ticket.getDiscount());
        ticketViewRoom.setText(ticket.getRoom());
        ticketViewRow.setText(ticket.getRow());
        ticketViewPosition.setText(ticket.getPosition());
    }

    public void addToCalendar(View view) {
        ContentResolver cr = getApplicationContext().getContentResolver();
        ContentValues values = new ContentValues();

        values.put(CalendarContract.Events.DTSTART, ticket.getDate());
        values.put(CalendarContract.Events.TITLE, ticket.getName());
        values.put(CalendarContract.Events.EVENT_LOCATION, ticket.getName());

        TimeZone timeZone = TimeZone.getDefault();
        values.put(CalendarContract.Events.EVENT_TIMEZONE, timeZone.getID());

        // Default calendar
        values.put(CalendarContract.Events.CALENDAR_ID, 1);

        // Set Period for 1 Hour
        values.put(CalendarContract.Events.DURATION, "+P2H");

        values.put(CalendarContract.Events.HAS_ALARM, 1);

// Insert event to calendar
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(TicketDetailsActivity.this, R.string.CalendarError, Toast.LENGTH_SHORT).show();

            return;
        }
        Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);
    }
}
