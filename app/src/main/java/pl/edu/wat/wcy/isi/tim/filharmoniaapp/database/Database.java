package pl.edu.wat.wcy.isi.tim.filharmoniaapp.database;


import android.arch.persistence.room.RoomDatabase;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.model.AndroidTicket;

@android.arch.persistence.room.Database(entities = {AndroidTicket.class}, version = 1)
public abstract class Database extends RoomDatabase {
    public abstract AndroidTicketDAO getAndroidItemDAO();
}
