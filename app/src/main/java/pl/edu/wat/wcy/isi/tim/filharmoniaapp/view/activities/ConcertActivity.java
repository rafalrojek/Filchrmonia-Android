package pl.edu.wat.wcy.isi.tim.filharmoniaapp.view.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.view.adapters.RepertoireAdapter;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.model.model.Concert;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.R;

public class ConcertActivity extends AppCompatActivity {

    @BindView(R.id.concertViewName) TextView name;
    @BindView(R.id.concertViewDate) TextView date;
    @BindView(R.id.concertViewCost) TextView cost;
    @BindView(R.id.concertViewRoom) TextView room;
    @BindView(R.id.concertViewPerformer) TextView performer;
    @BindView(R.id.concertViewRepertoire) ListView listView;
    @BindView(R.id.BuyTicketButton) Button button;

    private Concert concert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concert);
        ButterKnife.bind(this);

        if (getIntent().getSerializableExtra("concert") != null)
            concert = (Concert) getIntent().getSerializableExtra("concert");

        name.setText(concert.getConcertTitle());
        date.setText(parseDate(concert.getDate()));
        cost.setText(concert.getTicketCost().toPlainString());
        room.setText(concert.getConcertRoomName());
        performer.setText(concert.getConcertPerformers());
        RepertoireAdapter adapter = new RepertoireAdapter(this, concert.getRepertoire());
        listView.setAdapter(adapter);

        button.setOnClickListener(this::onClick);
    }

    private String parseDate(String date) {
        return date.substring(0, 10) + " " + date.substring(11,16);
    }

    private void onClick(View view) {
        Intent i = new Intent(ConcertActivity.this, SelectSeatActivity.class);
        i.putExtra("concert", concert);
        startActivity(i);
    }
}
