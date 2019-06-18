package pl.edu.wat.wcy.isi.tim.filharmoniaapp.Database;

import android.arch.persistence.room.Room;
import android.content.Context;
import java.util.List;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.Model.AndroidTicket;

public class DatabaseSingleton {

    private static DatabaseSingleton singleton;
    private Database database;

    public static DatabaseSingleton get(Context context) {
        if (singleton ==null) singleton = new DatabaseSingleton(context);
        return singleton;
    }

    private DatabaseSingleton(Context context) {
        database = Room.databaseBuilder(context, Database.class, "mydb")
                .allowMainThreadQueries()
                .build();
    }

    public void insertTickets(AndroidTicket... tickets) {
        database.getAndroidItemDAO().insert(tickets);
    }

    public List<AndroidTicket> getTickets() {
        return database.getAndroidItemDAO().getItems();
    }
}
