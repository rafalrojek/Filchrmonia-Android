package pl.edu.wat.wcy.isi.tim.filharmoniaapp.adapters;

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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.model.Discount;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.model.Ticket;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.R;

@SuppressLint("ViewHolder")
public class DiscountAdapter extends ArrayAdapter<Ticket> {

    private Context context;
    private List<Ticket> tickets;
    private Map<Ticket, Integer> price;
    private BigDecimal ticketPrice;
    private TextView textView;
    private  ArrayList<Discount> discounts;

    @BindView(R.id.tv_SeatRowPosition) TextView SeatRowPosition;
    @BindView(R.id.spinnerDiscount) Spinner spinner;

    public DiscountAdapter (@NonNull Context context, @NonNull List<Ticket> tickets,
                            @NonNull TextView textView, @NonNull BigDecimal ticketPrice,
                            @NonNull ArrayList<Discount> discounts) {
        super(context, R.layout.discount_list, tickets);
        this.context = context;
        this.tickets = tickets;
        this.textView = textView;
        this.price = new HashMap<>();
        this.ticketPrice = ticketPrice;
        this.discounts = discounts;
    }

    private BigDecimal updatePrice () {
        BigDecimal sum = new BigDecimal(0);
        for (Ticket t: tickets) {
            if (price.get(t) != null) {
                BigDecimal discount = new BigDecimal((100 - price.get(t)) / 100.0);
                sum = sum.add(ticketPrice.multiply(discount));
            }
        }
        return sum.setScale(2, RoundingMode.HALF_UP);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.discount_list, parent, false);
        ButterKnife.bind(this, view);

        final Ticket ticket = tickets.get(position);
        String title = context.getString(R.string.rowDiscount) + ticket.getSeatRow() + " " + context.getString(R.string.PositionDiscount) + ticket.getSeatCol();
        SeatRowPosition.setText(title);

        final ArrayAdapter<Discount> adapter = new ArrayAdapter<>(context, R.layout.spinner_item, discounts);

        adapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ticket.setDiscountName(discounts.get(i).getName());
                price.remove(ticket);
                price.put(ticket, discounts.get(i).getPercents());
                String s = context.getString(R.string.sum) + updatePrice().toPlainString() + context.getString(R.string.PLN);
                textView.setText(s);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                ticket.setDiscountName(null);
            }

        });


        return view;
    }
}
