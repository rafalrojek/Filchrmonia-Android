package pl.edu.wat.wcy.isi.tim.filharmoniaapp.model.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import pl.edu.wat.wcy.isi.tim.filharmoniaapp.model.entities.AndroidTicket;

@Dao
public interface AndroidTicketDAO {

    @Insert
    void insert (AndroidTicket... tickets);

    @Query("DELETE FROM AndroidTicket")
    void nukeTable();

    @Query("SELECT * FROM AndroidTicket ORDER BY date")
    List<AndroidTicket> getItems();
}
