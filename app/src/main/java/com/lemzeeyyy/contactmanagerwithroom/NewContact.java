package com.lemzeeyyy.contactmanagerwithroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;
import com.lemzeeyyy.contactmanagerwithroom.data.Contact;
import com.lemzeeyyy.contactmanagerwithroom.model.ContactViewModel;

public class NewContact extends AppCompatActivity {
    public static final String NAME_REPLY = "name_reply";
    public static final String OCCUPATION_REPLY = "occupation_reply";
    private EditText enterName;
    private EditText enterOccupation;
    private Button saveBtn,updateBtn, deleteBtn;
    private ContactViewModel contactViewModel;
    private int contactId = 0 ;
    private Boolean isEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);
        enterName = findViewById(R.id.enter_name);
        enterOccupation = findViewById(R.id.enter_occupation);
        saveBtn = findViewById(R.id.save_button);
        contactViewModel = new ViewModelProvider.AndroidViewModelFactory(NewContact.this
                .getApplication())
                .create(ContactViewModel.class);
            if (getIntent().hasExtra(MainActivity.CONTACT_ID)) {
                contactId = getIntent().getIntExtra(MainActivity.CONTACT_ID,0);
//                int id = data.getInt(MainActivity.CONTACT_ID);
                contactViewModel.getContact(contactId).observe(this, contact -> {
                    if(contact!=null) {
                        enterName.setText(contact.getName());
                        enterOccupation.setText(contact.getOccupation());
                    }
                });
                isEdit = true;
        }

        saveBtn.setOnClickListener(view -> {
            Intent replyIntent = new Intent();
            if(!TextUtils.isEmpty(enterName.getText().toString())
                    && !TextUtils.isEmpty(enterOccupation.getText().toString())) {
                String name = enterName.getText().toString();
                String occupation = enterOccupation.getText().toString();
                replyIntent.putExtra(NAME_REPLY,name);
                replyIntent.putExtra(OCCUPATION_REPLY,occupation);
                setResult(RESULT_OK,replyIntent);
            }else{
                setResult(RESULT_CANCELED,replyIntent);
            }
            finish();

        });

            //implement update button functionalties
        updateBtn = findViewById(R.id.update_btn);
        updateBtn.setOnClickListener(view -> {
            int id = contactId;
            String name = enterName.getText().toString().trim();
            String occupation = enterOccupation.getText().toString().trim();
            if(TextUtils.isEmpty(name) || TextUtils.isEmpty(occupation)){
                Snackbar.make(enterName,R.string.empty, Snackbar.LENGTH_SHORT)
                        .show();
            }else{
                Contact contact = new Contact();
                contact.setId(id);
                contact.setName(name);
                contact.setOccupation(occupation);
                ContactViewModel.update(contact);
                finish();
            }

        });
        deleteBtn = findViewById(R.id.delete_btn);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = contactId;
                String name = enterName.getText().toString().trim();
                String occupation = enterOccupation.getText().toString().trim();
                if(TextUtils.isEmpty(name) || TextUtils.isEmpty(occupation)){
                    Snackbar.make(enterName,R.string.empty, Snackbar.LENGTH_SHORT)
                            .show();
                }else{
                    Contact contact = new Contact();
                    contact.setId(id);
                    contact.setName(name);
                    contact.setOccupation(occupation);
                    ContactViewModel.delete(contact);
                    finish();
                }
            }
        });

        if(isEdit){
            saveBtn.setVisibility(View.GONE);
        }else{
            updateBtn.setVisibility(View.GONE);
            deleteBtn.setVisibility(View.GONE);
        }
    }
}