package com.lemzeeyyy.contactmanagerwithroom.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.lemzeeyyy.contactmanagerwithroom.ContactDao;
import com.lemzeeyyy.contactmanagerwithroom.util.ContactRoomDatabase;

import java.util.List;

public class ContactRepository {

    private ContactDao contactDao;
    private LiveData<List<Contact>> allContacts;

    public ContactRepository(Application application) {
        ContactRoomDatabase roomDatabase = ContactRoomDatabase.getDatabase(application);
        contactDao = roomDatabase.contactDao();
        allContacts = contactDao.getAllContacts();
    }
    public LiveData<List<Contact>> getAllData(){
        return allContacts;
    }
    public void insert(Contact contact){
        ContactRoomDatabase.databaseWriteExecutor.execute(()->{
            contactDao.insert(contact);
        });
    }
}
