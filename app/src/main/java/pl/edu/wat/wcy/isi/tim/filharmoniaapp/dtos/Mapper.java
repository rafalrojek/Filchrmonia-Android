package pl.edu.wat.wcy.isi.tim.filharmoniaapp.dtos;

import pl.edu.wat.wcy.isi.tim.filharmoniaapp.model.AndroidSeat;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.model.AndroidTicket;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.model.Concert;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.model.Discount;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.model.PieceOfMusic;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.model.Purchase;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.model.Seat;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.model.Ticket;

@org.mapstruct.Mapper
public interface Mapper {

    AndroidSeatDto androidSeatToDto (AndroidSeat androidSeat);
    AndroidSeat dtoToAndroidSeat (AndroidSeatDto androidSeatDto);

    AndroidTicketDto androidTicketToDto (AndroidTicket androidTicket);
    AndroidTicket dtoToAndroidTicket (AndroidTicketDto androidTicketDto);

    ConcertDto concertToDto (Concert concert);
    Concert dtoToConcert (ConcertDto concertDto);

    DiscountDto discountToDto (Discount discount);
    Discount dtoToDiscount (DiscountDto discountDto);

    PieceOfMusicDto pieceOfMusicToDto (PieceOfMusic pieceOfMusic);
    PieceOfMusic dtoToPieceOfMusic (PieceOfMusicDto pieceOfMusicDto);

    PurchaseDto purchaseToDto (Purchase purchase);
    Purchase dtoToPurchase (PurchaseDto purchaseDto);

    SeatDto seatToDto (Seat seat);
    Seat dtoToSeat (SeatDto seatDto);

    TicketDto ticketToDto (Ticket ticket);
    Ticket dtoToTicket (TicketDto ticketDto);
}
