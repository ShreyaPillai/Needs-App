package com.example.needs.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.needs.Data.DatabaseHandler;
import com.example.needs.ListActivity;
import com.example.needs.R;
import com.example.needs.model.Item;
import com.google.android.material.snackbar.Snackbar;

import java.text.MessageFormat;
import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<Item> itemList;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private LayoutInflater inflater;

   // private androidx.appcompat.app.AlertDialog alertDialog;

    public RecyclerViewAdapter(Context context, List<Item> itemList) {

        this.context = context;
        this.itemList = itemList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row,parent,false);


        return new ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Item item = itemList.get(position);  //object item
        holder.itemName.setText(MessageFormat.format("Item:  {0}", item.getItemname()));
        holder.itemQuantity.setText(MessageFormat.format("Quantity: {0}", Integer.toString(item.getItemQty())));
        holder.itemColor.setText(MessageFormat.format("Color: {0}", item.getItemColor()));
        holder.itemSize.setText(MessageFormat.format("Size: {0}", item.getItemSize()));
        holder.dateAdded.setText(MessageFormat.format("Added on: {0}", item.getDateitemadded()));

    }

    @Override
    public int getItemCount() {

        return itemList.size();
    }

    

    public class ViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView itemName;
        public TextView itemQuantity;
        public  TextView itemColor;
        public TextView itemSize;

        public TextView dateAdded;

        public Button editButton;
        public  Button deleteButton;
        public int id;
        public ViewHolder(@NonNull View itemView ,Context ctx) {

            super(itemView);
            context = ctx;

            itemName = itemView.findViewById(R.id.item_name);
            itemQuantity = itemView.findViewById(R.id.item_qty);
            itemColor = itemView.findViewById(R.id.item_color);
            itemSize = itemView.findViewById(R.id.item_size);
            dateAdded = itemView.findViewById(R.id.item_date);

            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);

            editButton.setOnClickListener(this);
            deleteButton.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            int position;

            position = getAdapterPosition();

            Item item = itemList.get(position);

            switch (v.getId()){

                case R.id.editButton:
                    //edit item
                    editItem(item);
                    break;

                case R.id.deleteButton:
                    //delete item
                    deleteItem(item.getId());
                    break;
            }

        }
        private void deleteItem(final int id) {

            builder = new AlertDialog.Builder(context);
            inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.confirmation_pop,null);

            Button noButton = view.findViewById(R.id.conf_no_button);
            Button yesButton = view.findViewById(R.id.conf_yes_button);

            builder.setView(view);
            dialog = builder.create();
            dialog.show();


            yesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    DatabaseHandler db = new DatabaseHandler(context);
                    db.deleteItem(id);

                    itemList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    dialog.dismiss();

                }
            });

            noButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });



        }
        private void editItem(final Item newItem) {

            Item item = itemList.get(getAdapterPosition());

            builder = new AlertDialog.Builder(context);
            inflater = LayoutInflater.from(context);
            final View view = inflater.inflate(R.layout.popup, null);
            Button saveButton;
            final EditText reqitem;
            final EditText itemqty;
            final EditText itemcolor;
            final EditText itemsize;
            final TextView title;



            reqitem = view.findViewById(R.id.reqItem);
            itemqty = view.findViewById(R.id.ItemQuantity);
            itemcolor = view.findViewById(R.id.ItemColor);
            itemsize = view.findViewById(R.id.ItemSize);
            saveButton = view.findViewById(R.id.SaveButton);
            saveButton.setText(R.string.update);
            title = view.findViewById(R.id.title_text);

            //title.setText(R.string.edit_time);

            reqitem.setText(newItem.getItemname());
            itemqty.setText(String.valueOf(newItem.getItemQty()));
            itemcolor.setText(newItem.getItemColor());
            itemsize.setText(newItem.getItemSize());


            builder.setView(view);
            dialog = builder.create();
            dialog.show();

            saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseHandler databaseHandler = new DatabaseHandler(context);

                newItem.setItemname(reqitem.getText().toString());
                newItem.setItemQty(Integer.parseInt(itemqty.getText().toString()));
                newItem.setItemColor(itemcolor.getText().toString());
                newItem.setItemSize(itemsize.getText().toString());


                if (!reqitem.getText().toString().isEmpty()
                        && !itemcolor.getText().toString().isEmpty()
                        && !itemqty.getText().toString().isEmpty()
                        && !itemsize.getText().toString().isEmpty()){

                    databaseHandler.updateItem(newItem);
                    notifyItemChanged(getAdapterPosition(),newItem);
                }else {
                    Snackbar.make(view,"Empty feilds not allowed",Snackbar.LENGTH_SHORT).show();
                }

                dialog.dismiss();
            }
        });

        }


    }



}

