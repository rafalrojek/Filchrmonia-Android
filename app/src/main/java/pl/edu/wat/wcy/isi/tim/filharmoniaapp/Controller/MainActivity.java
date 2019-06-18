package pl.edu.wat.wcy.isi.tim.filharmoniaapp.Controller;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.R;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.button) Button button;
    @BindView(R.id.button2) Button button2;
    @BindView(R.id.button3) Button button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        button.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, RepertoireActivity.class)));
        button2.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, TicketsActivity.class)));

        button3.setOnClickListener(v -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("geo:52.2344564,21.0112763,15"))));
    }

}
