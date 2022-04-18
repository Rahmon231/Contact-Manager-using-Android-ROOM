package com.lemzeeyyy.contactmanagerwithroom;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.lemzeeyyy.contactmanagerwithroom.data.Contact;

import java.util.List;
@Dao
public interface ContactDao {
    //CRUD
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Contact contact);

    @Query("DELETE FROM contact_table")
    void deleteAll();

    @Query("SELECT * FROM contact_table ORDER BY NAME ASC")
    LiveData<List<Contact>> getAllContacts();


}
