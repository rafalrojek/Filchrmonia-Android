package pl.edu.wat.wcy.isi.tim.filharmoniaapp.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.Adapters.ConcertListAdapter;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.Model.Concert;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.R;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.RequestSingleton;

public class RepertoireActivity extends AppCompatActivity {

    @BindView(R.id.lv_list) ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repertoire);
        ButterKnife.bind(this);

        List<Concert> concerts = new ArrayList<>();
        String url = RequestSingleton.address + "/concert/approved";

        Type listType = new TypeToken<List<Concert>>(){}.getType();

        ConcertListAdapter adapter = new ConcertListAdapter(this, concerts);
        adapter.sort((concert, t1) -> concert.getDate().compareTo(t1.getDate()));
        RequestSingleton.getInstance(getApplicationContext()).addToRequestQueue(new JsonArrayRequest
                (Request.Method.GET, url, null,
                        response -> {
                            concerts.addAll(new Gson().fromJson(response.toString(), listType));
                            adapter.notifyDataSetChanged();
                        },
                        error -> {
                    Toast.makeText(RepertoireActivity.this, R.string.connectionError, Toast.LENGTH_SHORT).show();
                    Toast.makeText(RepertoireActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                })
        );


        listView.setAdapter(adapter);
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Concert concert = concerts.get(i);
            Intent intent = new Intent(RepertoireActivity.this, ConcertActivity.class);
            intent.putExtra("concert", concert);
            startActivity(intent);
        });

    }
}
