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
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.Model.PieceOfMusic;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.R;

public class RepertoireAdapter extends ArrayAdapter<PieceOfMusic> {

    private Context context;
    private List<PieceOfMusic> pieceOfMusics;
    @BindView(R.id.tv_TitleComposer) TextView concertName;

    public RepertoireAdapter(@NonNull Context context, @NonNull List<PieceOfMusic> objects) {
        super(context, R.layout.repertoire_list, objects);
        this.context = context;
        this.pieceOfMusics = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.repertoire_list, parent, false);
        ButterKnife.bind(this, view);

        String text = pieceOfMusics.get(position).getComposer() + " - " + pieceOfMusics.get(position).getTitle();
        concertName.setText(text);

        return view;
    }
}
