package pl.edu.wat.wcy.isi.tim.filharmoniaapp.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.controllers.SelectSeatController;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.model.Concert;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.model.Seat;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.R;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.model.Ticket;

public class SelectSeatActivity extends AppCompatActivity {
    @BindView(R.id.selectDiscountButton) Button button;
    Concert concert;

    SelectSeatController controller;

    @Override
    @SuppressLint("CheckResult")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_seat_acivity);
        ButterKnife.bind(this);

        this.concert = (Concert) getIntent().getSerializableExtra("concert");

        controller = new SelectSeatController(this, concert.getIdConcert());
        controller.getSeats(concert.getIdConcert());
    }

    private Seat getSeat (int id) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                String Sid = "checkBox" + i + j;
                int resId = getResources().getIdentifier(Sid, "id", getPackageName());
                if (resId == id) return new Seat(i+1,j+1);
            }
        }
        return null;
    }

    public void OnCheckBoxClick(View view) {
        int id = view.getId();
        CheckBox box = findViewById(id);
        Seat seat = getSeat(id);

        controller.SendSeatMessage(box.isChecked(), seat);

    }

    public void OnButtonClick(View view) {
        if (controller.isEmpty()) Toast.makeText(SelectSeatActivity.this, R.string.noSeat, Toast.LENGTH_SHORT).show();
        else {
            ArrayList<Ticket> tickets = controller.forwardMessage();
            Intent intent = new Intent(SelectSeatActivity.this, DiscountActivity.class);
            intent.putExtra("Tickets", tickets);
            intent.putExtra("Concert", concert);
            startActivity(intent);
        }
    }

    public void switchCheckbox(Seat seat, boolean enabled, boolean checked) {
        String sid = "checkBox"+(seat.getRow()-1)+(seat.getCol()-1);
        int resId = getResources().getIdentifier(sid, "id", getPackageName());
        CheckBox box = findViewById(resId);
        runOnUiThread(() -> {
            box.setEnabled(enabled);
            box.setChecked(checked);
        });
    }

    @Override
    public void onBackPressed() {
        controller.disconnectMessage();
        finish();
    }

}
