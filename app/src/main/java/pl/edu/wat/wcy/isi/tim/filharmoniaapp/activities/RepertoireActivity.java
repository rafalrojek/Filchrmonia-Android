package pl.edu.wat.wcy.isi.tim.filharmoniaapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.adapters.ConcertListAdapter;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.controllers.RepertoireController;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.model.Concert;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.R;

public class RepertoireActivity extends AppCompatActivity {

    @BindView(R.id.lv_list) ListView listView;
    List<Concert> concerts;
    ConcertListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repertoire);
        ButterKnife.bind(this);

        concerts = new ArrayList<>();

        adapter = new ConcertListAdapter(this, concerts);
        adapter.sort((concert, t1) -> concert.getDate().compareTo(t1.getDate()));

        new RepertoireController(this).getConcerts();

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this::onItemClick);

    }


    public void listConcerts(ArrayList<Concert> concerts) {
        this.concerts.addAll(concerts);
        runOnUiThread(() -> adapter.notifyDataSetChanged());
    }


    public void onError(VolleyError error) {
        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
    }

    private void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Concert concert = concerts.get(i);
        Intent intent = new Intent(RepertoireActivity.this, ConcertActivity.class);
        intent.putExtra("concert", concert);
        startActivity(intent);
    }
}
