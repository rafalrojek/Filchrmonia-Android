package pl.edu.wat.wcy.isi.tim.filharmoniaapp.Database;


import android.arch.persistence.room.RoomDatabase;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.Model.AndroidTicket;

/**
 * Created by Arvin on 2/16/2018.
 */
@android.arch.persistence.room.Database(entities = {AndroidTicket.class}, version = 1)
public abstract class Database extends RoomDatabase {
    public abstract AndroidTicketDAO getAndroidItemDAO();
}
