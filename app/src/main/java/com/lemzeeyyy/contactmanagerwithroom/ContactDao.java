package com.lemzeeyyy.contactmanagerwithroom;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

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

    @Query("SELECT * FROM contact_table WHERE contact_table.id == :id")
    LiveData<Contact> getContact(int id);

    @Update
    void update(Contact contact);

    @Delete
    void delete(Contact contact);


}
