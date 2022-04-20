package com.lemzeeyyy.contactmanagerwithroom.util;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.lemzeeyyy.contactmanagerwithroom.ContactDao;
import com.lemzeeyyy.contactmanagerwithroom.data.Contact;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Contact.class}, version = 1, exportSchema = false)
public abstract class ContactRoomDatabase extends RoomDatabase {
    public abstract ContactDao contactDao();
    public static final int NUMBER_OF_THTREADS = 4;

    private static volatile ContactRoomDatabase INSTANCE;
    public static final ExecutorService databaseWriteExecutor
            = Executors.newFixedThreadPool(NUMBER_OF_THTREADS);
    public static ContactRoomDatabase getDatabase(final Context context ){
        if(INSTANCE == null){
            synchronized (ContactRoomDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ContactRoomDatabase.class,"contact_database")
                            .addCallback(sRoomDataBaseCallBack)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    private static final RoomDatabase.Callback sRoomDataBaseCallBack =
            new RoomDatabase.Callback(){
                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);
                    databaseWriteExecutor.execute(()->{
                        ContactDao contactDao = INSTANCE.contactDao();
                        contactDao.deleteAll();

//                        Contact contact = new Contact("Lemzy","Engineer");
//                        contactDao.insert(contact);
//
//                        contact = new Contact("Kaothar","Business");
//                        contactDao.insert(contact);
//
//                        contact = new Contact("Rahmon","Programmer");
//                        contactDao.insert(contact);
                    });
                }
            };
}
