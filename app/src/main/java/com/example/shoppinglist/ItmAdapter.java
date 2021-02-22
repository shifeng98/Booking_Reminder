package com.example.shoppinglist;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ItmAdapter extends RecyclerView.Adapter<ItmAdapter.ItmViewHolder> {
    private ArrayList<Item> allItems;
    private ArrayList<Item> tempItemList;
    private String fragmentName;
    private OnItemClickListner onItemClickListner;

    public ItmAdapter(ArrayList<Item> allItemList, String fragmentName, OnItemClickListner onItemClickListner) {
        this.allItems = allItemList;
        this.fragmentName = fragmentName;
        this.onItemClickListner = onItemClickListner;
        tempItemList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ItmAdapter.ItmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout, parent,false);
        return new ItmViewHolder(view, onItemClickListner);
    }

    @Override
    public void onBindViewHolder(@NonNull ItmViewHolder holder, int position) {
        if(getItemCount() > 0) {
            Item item = tempItemList.get(position);
            holder.itemName.setText(item.getName());
            holder.itemSize.setText("Duration: " + item.getSize());
            holder.itemQuantity.setText("Vlm: " + item.getQuantity());
            holder.itemSwitch.setChecked(item.getBought()==1);

            //Change icon based on the item
            if (item.getBought() == 1) {
                holder.itemIcon.setImageResource(R.drawable.bought);
            } else if (item.getUrgent() == 1) {
                holder.itemIcon.setImageResource(R.drawable.urgent);
            } else {
                holder.itemIcon.setImageResource(R.drawable.buy);
            }

            if (fragmentName.equals("complete")) {
                holder.itemDate.setText(Html.fromHtml("<b>Date Borrowed: </b>" + item.getDate()));
                holder.itemDate.setVisibility(View.VISIBLE);
                holder.itemSwitch.setVisibility(View.INVISIBLE);
                holder.itemDate.setTextSize(12);
                holder.itemQuantity.setTextSize(12);
                holder.itemSize.setTextSize(12);
            } else {
                holder.itemSwitch.setVisibility(View.VISIBLE);
                holder.itemDate.setVisibility(View.INVISIBLE);
            }
        }
    }

    public class ItmViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private TextView  itemName;
        private TextView itemQuantity;
        private TextView itemSize;
        private TextView itemDate;
        private ImageView itemIcon;
        private SwitchCompat itemSwitch;
        private OnItemClickListner onItemClickListner;

        public ItmViewHolder(@NonNull View itemView, final OnItemClickListner onItemClickListner) {
            super(itemView);
            itemName = itemView.findViewById(R.id.itemName);
            itemQuantity = itemView.findViewById(R.id.itemQty);
            itemSize = itemView.findViewById(R.id.itemSize);
            itemDate = itemView.findViewById(R.id.itemDate);
            itemIcon = itemView.findViewById(R.id.itemIcon);
            itemSwitch = itemView.findViewById(R.id.itemSwitch);
            this.onItemClickListner = onItemClickListner;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            if(fragmentName.equals("Completed")) {
                itemSwitch.setVisibility(View.GONE);
            } else {
                itemDate.setVisibility(View.GONE);
                itemSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        int clickedItem = getAdapterPosition();
                        Item item = tempItemList.get(clickedItem);
                        if(compoundButton.isChecked() && item.getBought() == 0){
                            onItemClickListner.onToggleSwitch(item);
                        }
                    }
                });
            }
        }

        @Override
        public void onClick(View view) {
            Item item = tempItemList.get(getAdapterPosition());
            onItemClickListner.onItemClick(item, fragmentName);
        }

        @Override
        public boolean onLongClick(View view) {
            Item item = tempItemList.get(getAdapterPosition());
            onItemClickListner.onItemLongClick(item); //Pass the selected item for delete
            return true;
        }
    }

    @Override
    public int getItemCount() {
        return tempItemList.size();
    }

    public void setFragment(String fragName) {
        this.fragmentName = fragName;
        generateItemList();
        notifyDataSetChanged();
    }

    public void setItemList(List<Item> itemList) {
        this.allItems = new ArrayList<>(itemList);
        generateItemList();
        notifyDataSetChanged();
    }

    //Generate the items based on the fragment
    private void generateItemList() {
        if(!tempItemList.isEmpty()){
            tempItemList.clear();
        }

        switch (fragmentName){
            case "home":
                for (Item item : allItems){
                    if(item.getBought() == 0){
                        tempItemList.add(item);
                    }
                }
                break;
            case "urgent":
                for (Item item : allItems){
                    //Add item when the item is set as urgent
                    if(item.getBought() == 0 && item.getUrgent() == 1){
                        tempItemList.add(item);
                    }
                }
                break;
            case "complete":
                for (Item item : allItems){
                    //Add item when the item is bought
                    if(item.getBought() == 1){
                        tempItemList.add(item);
                    }
                }
                break;
        }
    }

    public interface OnItemClickListner {
        void onToggleSwitch(Item item);
        void onItemClick(Item item, String currentFragment);
        void onItemLongClick(Item item);
    }
}
