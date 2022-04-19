package com.lemzeeyyy.contactmanagerwithroom;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lemzeeyyy.contactmanagerwithroom.data.Contact;
import com.lemzeeyyy.contactmanagerwithroom.model.ContactViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int START_ACTIVITY_REQUEST_CODE = 1;
    private ContactViewModel contactViewModel;
    private FloatingActionButton fab ;
    private TextView showDBContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fab = findViewById(R.id.addContactFab);
        showDBContent = findViewById(R.id.text);
        contactViewModel = new ViewModelProvider.AndroidViewModelFactory(MainActivity.this
                .getApplication())
                .create(ContactViewModel.class);
        contactViewModel.getAllContacts().observe(this, contacts -> {
            StringBuilder stringBuilder = new StringBuilder();
            for (Contact contact :
                    contacts) {
                stringBuilder.append("-"+contact.getName()+" "+"-"+contact.getOccupation());
            }
            showDBContent.setText(stringBuilder);
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
}