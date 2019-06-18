package pl.edu.wat.wcy.isi.tim.filharmoniaapp.Adapters;

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
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.Model.Discount;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.Model.Ticket;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.R;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.RequestSingleton;

public class DiscountAdapter extends ArrayAdapter<Ticket> {

    private Context context;
    private List<Ticket> tickets;
    private Map<Ticket, Integer> price;
    private BigDecimal ticketPrice;
    TextView textView;

    @BindView(R.id.tv_SeatRowPosition) TextView SeatRowPosition;
    @BindView(R.id.spinnerDiscount) Spinner spinner;

    public DiscountAdapter (@NonNull Context context, @NonNull List<Ticket> tickets,
                            @NonNull TextView textView, @NonNull BigDecimal ticketPrice) {
        super(context, R.layout.discount_list, tickets);
        this.context = context;
        this.tickets = tickets;
        this.textView = textView;
        this.price = new HashMap<>();
        this.ticketPrice = ticketPrice;
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
        String title = context.getString(R.string.rowDiscount) + ticket.getSeatRow() + context.getString(R.string.PositionDiscount) + ticket.getSeatCol();
        SeatRowPosition.setText(title);

        List<Discount> discounts = new ArrayList<>();
        final ArrayAdapter<Discount> adapter = new ArrayAdapter<>(context, R.layout.spinner_item, discounts);

        String url = RequestSingleton.address + "/discount";
        Type listType = new TypeToken<List<Discount>>(){}.getType();

        RequestSingleton.getInstance(context).addToRequestQueue(new JsonArrayRequest
                (Request.Method.GET, url, null,
                        response -> {
                            discounts.addAll(new Gson().fromJson(response.toString(), listType));
                            adapter.notifyDataSetChanged();
                        },
                        error -> {
                            Toast.makeText(context, R.string.connectionError, Toast.LENGTH_SHORT).show();
                            Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                        })
        );

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
