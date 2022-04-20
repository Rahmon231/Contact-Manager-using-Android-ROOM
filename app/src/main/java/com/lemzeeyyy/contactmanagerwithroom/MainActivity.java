package com.lemzeeyyy.contactmanagerwithroom;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lemzeeyyy.contactmanagerwithroom.adapter.RecyclerViewAdapter;
import com.lemzeeyyy.contactmanagerwithroom.data.Contact;
import com.lemzeeyyy.contactmanagerwithroom.model.ContactViewModel;
import com.lemzeeyyy.contactmanagerwithroom.util.ContactRoomDatabase;

import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.onContactClickListener {
    public static final int START_ACTIVITY_REQUEST_CODE = 1;
    public static final String CONTACT_ID = "contact_id";
    private ContactViewModel contactViewModel;
    private FloatingActionButton fab ;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private LiveData<List<Contact>> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fab = findViewById(R.id.addContactFab);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        contactViewModel = new ViewModelProvider.AndroidViewModelFactory(MainActivity.this
                .getApplication())
                .create(ContactViewModel.class);
        contactViewModel.getAllContacts().observe(this, contacts -> {
          //  ContactViewModel.deleteAll();
            recyclerViewAdapter = new RecyclerViewAdapter(contacts,MainActivity.this,this);
            recyclerView.setAdapter(recyclerViewAdapter);


        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewContact.class);
                startActivityForResult(intent, START_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == START_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){

            String name = data.getStringExtra(NewContact.NAME_REPLY);
            String occupation = data.getStringExtra(NewContact.OCCUPATION_REPLY);

        Contact contact = new Contact(name, occupation);
        ContactViewModel.insert(contact);

        }
    }

    @Override
    public void onContactClick(int position) {
        Contact contact = contactViewModel.getAllContacts().getValue().get(position);
        Intent intent = new Intent(MainActivity.this, NewContact.class);
        intent.putExtra(CONTACT_ID,contact.getId());
        startActivity(intent);




    }
}