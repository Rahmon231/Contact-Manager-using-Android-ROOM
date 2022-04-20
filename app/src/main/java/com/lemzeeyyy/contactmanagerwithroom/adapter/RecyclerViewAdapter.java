package com.lemzeeyyy.contactmanagerwithroom.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.lemzeeyyy.contactmanagerwithroom.R;
import com.lemzeeyyy.contactmanagerwithroom.data.Contact;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    private List<Contact> contactList;
    private Context context;
    private onContactClickListener onContactClickListener;


    public RecyclerViewAdapter(List<Contact> contactList, Context context, onContactClickListener onContactClickListener) {
        this.contactList = contactList;
        this.context = context;
        this.onContactClickListener = onContactClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.contact_row,parent,false);
        return new ViewHolder(view,onContactClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Contact contact = contactList.get(position);
        holder.name.setText(contact.getName());
        holder.occupation.setText(contact.getOccupation());

    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView name;
        public TextView occupation;
        public onContactClickListener onContactClickListener;


        public ViewHolder(@NonNull View itemView,onContactClickListener onContactClickListener) {
            super(itemView);
            name = itemView.findViewById(R.id.row_name_textview);
            occupation = itemView.findViewById(R.id.row_occupstion_textview);
            this.onContactClickListener = onContactClickListener;
            itemView.setOnClickListener(this::onClick);
        }

        @Override
        public void onClick(View view) {
            onContactClickListener.onContactClick(getAdapterPosition());
        }
    }
    public  interface onContactClickListener{
        void onContactClick(int position);
    }
}
