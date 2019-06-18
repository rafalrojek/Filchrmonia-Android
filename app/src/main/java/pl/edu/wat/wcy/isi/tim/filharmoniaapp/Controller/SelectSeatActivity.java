package pl.edu.wat.wcy.isi.tim.filharmoniaapp.Controller;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.Model.AndroidSeat;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.Model.Concert;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.Model.Seat;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.Model.Ticket;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.R;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.RequestSingleton;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;
import ua.naiksoftware.stomp.dto.StompCommand;
import ua.naiksoftware.stomp.dto.StompHeader;
import ua.naiksoftware.stomp.dto.StompMessage;

public class SelectSeatActivity extends AppCompatActivity {
    ArrayList<Ticket> tickets;
    @BindView(R.id.selectDiscountButton) Button button;
    Concert concert;
    StompClient stompClient;

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    @SuppressLint("CheckResult")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_seat_acivity);
        ButterKnife.bind(this);

        tickets = new ArrayList<>();
        this.concert = (Concert) getIntent().getSerializableExtra("concert");

        getSeats(concert.getIdConcert());

        stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP,RequestSingleton.wsaddress);
        stompClient.connect();
        StompUtils.lifecycle(stompClient);
        System.out.println("Podłączono " + concert.getIdConcert());
        stompClient.topic("/concert/seats/"+concert.getIdConcert())
               .subscribe(stompMessage ->  onMessage(stompMessage.getPayload()));
    }


    private void getSeats(int idConcert) {
        String url = RequestSingleton.address + "/free-seat/"+idConcert;
        Type listType = new TypeToken<List<Seat>>(){}.getType();

        ArrayList<Seat> seats = new ArrayList<>();
        RequestSingleton.getInstance(getApplicationContext()).addToRequestQueue(new JsonArrayRequest
                (Request.Method.GET, url, null,
                        response -> {
                            seats.addAll(new Gson().fromJson(response.toString(), listType));
                            for (Seat s: seats) {
                                String sid = "checkBox"+(s.getRow()-1)+(s.getCol()-1);
                                int resId = getResources().getIdentifier(sid, "id", getPackageName());
                                CheckBox box = findViewById(resId);
                                box.setEnabled(true);
                            }
                        },
                        error -> {
                            Toast.makeText(getApplicationContext(), "Musisz zająć co najmniej jedno miejsce", Toast.LENGTH_SHORT).show();
                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                            finish();
                        })
        );
    }

    private Seat getSeat (int id) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                String Sid = "checkBox" + i + j;
                int resId = getResources().getIdentifier(Sid, "id", getPackageName());
                if (resId == id) return new Seat(i,j);
            }
        }
        return null;
    }

    private Ticket getTicket (Seat seat) {
        for (Ticket t: tickets) {
            if (t.getSeatCol() == seat.getCol() && t.getSeatRow() == seat.getRow())
                return t;
        }
        return null;
    }

    private void removeTicket(Seat seat) {
        System.out.println(seat.getCol() + " " + seat.getRow());
        for (Ticket t: tickets) {
            if (t.getSeatCol() == seat.getCol() && t.getSeatRow() == seat.getRow()) {
                tickets.remove(t);
                return;
            }
        }

    }

    public void OnCheckBoxClick(View view) {
        int id = view.getId();
        CheckBox box = findViewById(id);
        Seat seat =getSeat(id);

        @SuppressLint("HardwareIds")  String android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);

        ArrayList<Seat> seats = new ArrayList<>();
        seat.setCol(seat.getCol()+1);
        seat.setRow(seat.getRow()+1);
        System.out.println(seat.getCol() + " " + seat.getRow());
        seats.add(seat);
        AndroidSeat androidSeat;
        if (box.isChecked())
            androidSeat = new AndroidSeat(concert.getIdConcert(), android_id, seats, AndroidSeat.MessageType.LOCKED);
        else
            androidSeat = new AndroidSeat(concert.getIdConcert(), android_id, seats, AndroidSeat.MessageType.UNLOCKED);

        //ws2.send(new Gson().toJson(androidSeat));
        stompClient.send(new StompMessage(StompCommand.SEND,
                Collections.singletonList(new StompHeader(StompHeader.DESTINATION, "/app/socket.sendMessage")),
                new Gson().toJson(androidSeat))).subscribe();
        button.setEnabled(true);

    }

    public void OnButtonClick(View view) {
        if (tickets.isEmpty()) Toast.makeText(SelectSeatActivity.this, R.string.connectionError, Toast.LENGTH_SHORT).show();
        else {
            @SuppressLint("HardwareIds")  String android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            AndroidSeat androidSeat = new AndroidSeat(concert.getIdConcert(), android_id, null, AndroidSeat.MessageType.FORWARDED);
            stompClient.send(new StompMessage(StompCommand.SEND,
                    Collections.singletonList(new StompHeader(StompHeader.DESTINATION, "/app/socket.sendMessage")),
                    new Gson().toJson(androidSeat))).subscribe();
            stompClient.disconnect();
            Intent intent = new Intent(SelectSeatActivity.this, DiscountActivity.class);
            intent.putExtra("Tickets", tickets);
            intent.putExtra("Concert", concert);
            startActivity(intent);
        }
    }

    private void onMessage(String text) {
        AndroidSeat androidSeat = new Gson().fromJson(text, AndroidSeat.class);
        System.out.println("Kupa");
        if(androidSeat.getConcertId() != concert.getIdConcert()) return;

        @SuppressLint("HardwareIds")  String android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);

        for (Seat seat: androidSeat.getSeat()) {
            String sid = "checkBox"+(seat.getRow()-1)+(seat.getCol()-1);
            int resId = getResources().getIdentifier(sid, "id", getPackageName());
            CheckBox box = findViewById(resId);

            if (androidSeat.getType() == AndroidSeat.MessageType.LOCKED) {
                if (android_id.equals(androidSeat.getAndroidId())) {
                    tickets.add(new Ticket(seat));
                    box.setChecked(true);
                }
                else {
                    box.setEnabled(false);
                    box.setChecked(false);
                    System.out.println("Dupka");
                    removeTicket(seat);
                }
            }
            else {
                box.setEnabled(true);
                box.setChecked(false);
                removeTicket(seat);
                System.out.println(tickets.size());
            }

        }
    }
    @Override
    public void onBackPressed() {
        @SuppressLint("HardwareIds")  String android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        AndroidSeat androidSeat = new AndroidSeat(concert.getIdConcert(), android_id, null, AndroidSeat.MessageType.DISCONNECT);
        stompClient.send(new StompMessage(StompCommand.SEND,
                Collections.singletonList(new StompHeader(StompHeader.DESTINATION, "/app/socket.sendMessage")),
                new Gson().toJson(androidSeat))).subscribe();
        stompClient.disconnect();
    }
}
