package pl.edu.wat.wcy.isi.tim.filharmoniaapp.adapters;

import android.annotation.SuppressLint;
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
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.model.AndroidTicket;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.R;

@SuppressLint("ViewHolder")
public class TicketListAdapter extends ArrayAdapter<AndroidTicket> {

    private Context context;
    private List<AndroidTicket> tickets;

    @BindView(R.id.tv_ticket_name) TextView ticketName;
    @BindView(R.id.tv_ticket_datetime) TextView concertDate;

    public TicketListAdapter(@NonNull Context context,@NonNull List<AndroidTicket> objects){
        super(context, R.layout.ticket_list,objects);
        this.context=context;
        this.tickets=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        LayoutInflater layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.ticket_list,parent,false);
        ButterKnife.bind(this, view);

        ticketName.setText(tickets.get(position).getConcertTitle());
        concertDate.setText(tickets.get(position).getDate());

        return view;
    }
}