package com.example.needs.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.needs.Data.DatabaseHandler;
import com.example.needs.R;
import com.example.needs.model.Item;

import java.text.MessageFormat;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<Item> itemList;
    public RecyclerViewAdapter(Context context, List<Item> itemList) {

        this.context = context;
        this.itemList = itemList;

    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row,parent,false);


        return new ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {

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

            switch (v.getId()){

                case R.id.editButton:
                    //edit item
                    break;

                case R.id.deleteButton:
                    //delete item
                    position = getAdapterPosition();

                    Item item = itemList.get(position);
                    deleteItem(item.getId());
                    break;
            }

        }
        private void deleteItem(int id) {

            DatabaseHandler db = new DatabaseHandler(context);
            db.deleteItem(id);

            itemList.remove(getAdapterPosition());
            notifyItemRemoved(getAdapterPosition());

        }
    }
    /*int getId(int position){
        return itemList.get(position).getId();
    }*/

}
