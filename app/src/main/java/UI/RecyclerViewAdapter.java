package UI;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.Activities.DetailActivity;
import com.example.myapplication.R;

import java.util.List;

import Model.Item;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

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

            //Onclick event for the view
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    Item item = itemList.get(position);

                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("itemID",item.getId());
                    intent.putExtra("itemName",item.getItemName());
                    intent.putExtra("itemQuantity",item.getItemQuantity());
                    intent.putExtra("itemDate",item.getItemDateCreated());
                    context.startActivity(intent);
                }
            });

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.deleteBtn:
                    //TODO: Delete
                    break;
                case R.id.editBtn:
                    //TODO: Edit
                    break;

            }

        }
    }
}
