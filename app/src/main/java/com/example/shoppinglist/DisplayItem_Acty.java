package com.example.shoppinglist;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayItem_Acty extends AppCompatActivity {
    private TextView itemName, itemDetails, itemQuantity, itemSize;
    private ImageView urgentIcon;
    private ImageButton editButton;
    private Context context;
    private Item item;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_item__acty);
        context = this.getApplicationContext();
        initDisplayUI();
        Intent intent = getIntent();
        if(intent.getExtras() != null)
        {
            item = intent.getExtras().getParcelable("selectedItem");
            if(item !=null) {
                String fragmentName = intent.getExtras().getString("fragment");
                itemName.setText(item.getName());
                itemDetails.setText(item.getDetails());
                itemQuantity.setText(String.valueOf(item.getQuantity()));
                itemSize.setText(item.getSize());
                if(item.getUrgent()==1){
                    urgentIcon.setImageResource(R.drawable.checked);
                }
                else{
                    urgentIcon.setImageResource(R.drawable.unchecked);
                }

                if (fragmentName != null){
                    if (fragmentName.equals("complete")){
                        editButton.setVisibility(View.GONE);//Dont show edit button
                    } else {
                        editButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent();
                                intent.putExtra("selectedItem", item);
                                intent.putExtra("activity", "edit");
                                intent.setClass(context, AddItem_Actvy.class); //Go to edit activity
                                startActivityForResult(intent,2);
                            }
                        });
                    }
                }
            }
        }
    }

    private void initDisplayUI(){
        itemName = findViewById(R.id.itemTextView);
        itemDetails = findViewById(R.id.detailTextView);
        itemQuantity = findViewById(R.id.qtyTextView);
        itemSize = findViewById(R.id.sizeTextView);
        urgentIcon = findViewById(R.id.urgentIcon);
        editButton = findViewById(R.id.editBtn);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (data.getParcelableExtra("selectedItem") != null) {
                if (requestCode == 2) {
                    // Send edit item back to main activity
                    if (resultCode == RESULT_OK) {
                        Intent intent = new Intent();
                        item = data.getParcelableExtra("selectedItem");
                        intent.putExtra("selectedItem", data.getParcelableExtra("selectedItem"));
                        intent.putExtra("activity", data.getStringExtra("activity"));
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }
            }
        }
    }
}