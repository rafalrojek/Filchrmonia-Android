package pl.edu.wat.wcy.isi.tim.filharmoniaapp.model.dtos;

import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import pl.edu.wat.wcy.isi.tim.filharmoniaapp.model.entities.AndroidTicket;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.model.model.*;

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

    @Mappings({
            @Mapping(source = "discount.name", target = "discountName")
    })
    TicketDto ticketToDto (Ticket ticket);
}
