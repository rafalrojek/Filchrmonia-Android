package pl.edu.wat.wcy.isi.tim.filharmoniaapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.Model.Concert;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.R;

public class ConcertListAdapter extends ArrayAdapter<Concert> {

    @BindView(R.id.tv_concert_name) TextView concertName;
    @BindView(R.id.tv_concert_datetime) TextView concertDate;

    private Context context;
    private List<Concert> concerts;

    public ConcertListAdapter(@NonNull Context context, @NonNull List<Concert> objects) {
        super(context, R.layout.concert_list, objects);
        this.context = context;
        this.concerts = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.concert_list, parent, false);
        ButterKnife.bind(this, view);


        concertName.setText(concerts.get(position).getConcertTitle());
        concertDate.setText(concerts.get(position).getDate());

        return view;
    }
}
