package pl.edu.wat.wcy.isi.tim.filharmoniaapp.view.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.R;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.controller.DiscountController;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.controller.DiscountListController;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.model.model.Discount;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.model.model.Ticket;

@SuppressLint("ViewHolder")
public class DiscountAdapter extends ArrayAdapter<Ticket> {

    private Context context;
    private List<Ticket> tickets;
    private ArrayList<Discount> discounts;
    private DiscountController controller;

    @BindView(R.id.tv_SeatRowPosition) TextView SeatRowPosition;
    @BindView(R.id.spinnerDiscount) Spinner spinner;

    public DiscountAdapter(@NonNull Context context, @NonNull List<Ticket> tickets,
                           @NonNull ArrayList<Discount> discounts, DiscountController controller) {
        super(context, R.layout.discount_list, tickets);
        this.context = context;
        this.tickets = tickets;
        this.discounts = discounts;
        this.controller = controller;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.discount_list, parent, false);
        ButterKnife.bind(this, view);

        final Ticket ticket = tickets.get(position);
        String title = context.getString(R.string.rowDiscount) + ticket.getSeatRow() + ", " + context.getString(R.string.PositionDiscount) + ticket.getSeatCol();
        SeatRowPosition.setText(title);

        final ArrayAdapter<Discount> adapter = new ArrayAdapter<>(context, R.layout.spinner_item, discounts);

        adapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new DiscountListController(controller,ticket));
        return view;
    }
}
