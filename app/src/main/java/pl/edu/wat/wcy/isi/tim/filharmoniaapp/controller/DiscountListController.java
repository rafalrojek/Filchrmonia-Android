package pl.edu.wat.wcy.isi.tim.filharmoniaapp.controller;

import android.view.View;
import android.widget.AdapterView;

import pl.edu.wat.wcy.isi.tim.filharmoniaapp.model.model.Ticket;

public class DiscountListController implements AdapterView.OnItemSelectedListener {

    DiscountController controller;
    Ticket ticket;

    public DiscountListController(DiscountController controller, Ticket ticket) {
        this.controller = controller;
        this.ticket = ticket;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        controller.onItemSelected(ticket,i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        controller.onNothingSelected(ticket);
    }
}
