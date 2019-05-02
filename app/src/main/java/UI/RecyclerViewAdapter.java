package UI;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Activities.MainActivity;
import com.example.myapplication.R;

import java.util.List;
import java.util.logging.Handler;

import Data.DataBaseHandler;
import Model.Item;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private AlertDialog.Builder alertDialogBuilder;
    private AlertDialog dialog;
    private LayoutInflater inflater;

    private Context context;
    private List<Item> itemList;

    public RecyclerViewAdapter(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate
                (R.layout.list_row,viewGroup,false);
        return new ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder viewHolder, int i) {

        Item item = itemList.get(i);
        viewHolder.itemName.setText(item.getItemName());
        viewHolder.itemQuantity.setText(item.getItemQuantity());
        viewHolder.dateCreated.setText(item.getItemDateCreated());


    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView itemName;
        public TextView itemQuantity;
        public TextView dateCreated;
        public Button deleteItem;
        public Button editItem;
        public int id;

        public ViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);
            ctx = context;


            itemName = (TextView) itemView.findViewById(R.id.name);
            itemQuantity =(TextView) itemView.findViewById(R.id.quantity);
            dateCreated = (TextView) itemView.findViewById(R.id.dataAdded);
            deleteItem = (Button) itemView.findViewById(R.id.deleteBtn);
            editItem = (Button) itemView.findViewById(R.id.editBtn);

            //Set the onclick listeners
            deleteItem.setOnClickListener(this);
            editItem.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.deleteBtn:
                    int position = getAdapterPosition();
                    Item item = itemList.get(position);
                    deleteItem(item.getId());
                    break;


                case R.id.editBtn:
                    int position1 = getAdapterPosition();
                    Item item1 = itemList.get(position1);
                    editItem(item1);
                    break;

            }

        }

        //Delete an Item method
        private void deleteItem(final int id){

            //create alert dialogue

            alertDialogBuilder = new AlertDialog.Builder(context);
            inflater = LayoutInflater.from(context);
            final View view = inflater.inflate(R.layout.confirmation_dialogue,null);

            Button yesButton = (Button) view.findViewById(R.id.yes_button);
            Button noButton = (Button) view.findViewById(R.id.no_button);

            alertDialogBuilder.setView(view);
            dialog = alertDialogBuilder.create();
            dialog.show();

            //On click listeners for the buttons
            yesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DataBaseHandler db = new DataBaseHandler(v.getContext());
                    db.deleteItem(id);
                    itemList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    notifyItemRangeChanged(getAdapterPosition(),itemList.size());
                    dialog.dismiss();

                    //Check if Database is empty
                    if (db.getAllItemsCount() == 0){
                        context.startActivity(new Intent(v.getContext(), MainActivity.class));

                    }
                }
            });

            noButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });


        }

        private void editItem(final Item item){
            alertDialogBuilder = new AlertDialog.Builder(context);
            View view1 = LayoutInflater.from(context).inflate(R.layout.popup,null);

            TextView titleText = (TextView) view1.findViewById(R.id.title);
            final EditText itemName = (EditText) view1.findViewById(R.id.itemNameID);
            final EditText itemQuantity = (EditText) view1.findViewById(R.id.itemQuantityID);
            Button saveButton = (Button) view1.findViewById(R.id.save_item_button);

            titleText.setText("Update Item");
            itemName.setText(item.getItemName());
            itemQuantity.setText(item.getItemQuantity());

            alertDialogBuilder.setView(view1);
            dialog = alertDialogBuilder.create();
            dialog.show();

            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DataBaseHandler db = new DataBaseHandler(context);
                    item.setItemName(itemName.getText().toString());
                    item.setItemQuantity(itemQuantity.getText().toString());

                    if (!itemName.getText().toString().isEmpty() && !itemQuantity.getText().toString().isEmpty()){
                        db.updateItem(item);
                        notifyItemChanged(getAdapterPosition(),item);
                        dialog.dismiss();
                    }else if (itemName.getText().toString().isEmpty() && !itemQuantity.getText().toString().isEmpty()){

                        Toast.makeText(v.getContext(),"Item Name is Empty",Toast.LENGTH_SHORT).show();

                    }else if (!itemName.getText().toString().isEmpty() && itemQuantity.getText().toString().isEmpty()){

                        Toast.makeText(v.getContext(),"Item Quantity is Empty",Toast.LENGTH_SHORT).show();

                    }else if (itemName.getText().toString().isEmpty() && itemQuantity.getText().toString().isEmpty()){

                        Toast.makeText(v.getContext(),"Item Name and Quantity are Empty",Toast.LENGTH_SHORT).show();

                    }
                }
            });



        }

    }
}
