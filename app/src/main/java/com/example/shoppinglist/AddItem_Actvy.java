package com.example.shoppinglist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import java.util.ArrayList;

public class AddItem_Actvy extends AppCompatActivity implements View.OnClickListener {
    private int itemQuantity;
    private boolean urgentIsSet;
    private String itemName, itemDetails, itemSize, activityName;
    private EditText editItemName, editDetails, editQty;
    private Spinner sizeSpinner;
    private ArrayList<String> sizeList;
    private CheckBox urgentCheckBox;
    private Button addItemBtn;
    private ImageButton increaseBtn, decreaseBtn;
    private Item item;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setTitle("Add Item");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item__actvy);
        initAddItemUI();
        sizeSpinner.setSelection(0); //Default selection
        itemQuantity=0;
        intent = getIntent();
        activityName = intent.getExtras().getString("activity");
        intentHandler();
    }

    private void initAddItemUI(){
        editItemName = findViewById(R.id.editItemName);
        editDetails = findViewById(R.id.editDetails);
        editQty = findViewById(R.id.editQty);
        sizeSpinner = findViewById(R.id.sizeSpinner);
        addItemBtn = findViewById(R.id.addButton);
        urgentCheckBox = findViewById(R.id.checkBox);
        increaseBtn = findViewById(R.id.increaseButton);
        decreaseBtn = findViewById(R.id.decreaseButton);

        increaseBtn.setOnClickListener(this);
        decreaseBtn.setOnClickListener(this);
        addItemBtn.setOnClickListener(this);

        sizeList = new ArrayList<>();
        sizeList.add("Default");
        sizeList.add("Short");
        sizeList.add("Medium");
        sizeList.add("Long");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sizeList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sizeSpinner.setAdapter(adapter);
        sizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapter, View v, int position, long id) {
                itemSize = adapter.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void intentHandler(){
        if(intent.getExtras() != null) {
            // Check is it either add or edit
            if (activityName != null) {
                addItemBtn.setText("Add To List");
                if (activityName.equals("edit")) {
                    addItemBtn.setText("Update Item");
                    getSupportActionBar().setTitle("Edit Item");
                    item = intent.getExtras().getParcelable("selectedItem");
                    if (item != null) {
                        // Get intent and get text
                        editItemName.setText(item.getName());
                        editDetails.setText(item.getDetails());
                        editQty.setText(String.valueOf(item.getQuantity()));
                        sizeSpinner.setSelection(sizeList.indexOf(item.getSize()));
                        boolean check = false;
                        if(item.getUrgent() == 1){
                            check = true;
                        }
                        urgentCheckBox.setChecked(check);
                    }
                }
            }
        }
    }

    @Override
    public void onClick(View view) {

        //Get the initial quantity value when item is being edit
        if(!editQty.getText().toString().equals("")){
            itemQuantity = Integer.parseInt(editQty.getText().toString());
        }

        switch (view.getId()){
            case R.id.increaseButton:
                itemQuantity++;
                editQty.setText(String.valueOf(itemQuantity));
                break;

            case R.id.decreaseButton:
                //Make sure item quantity min is 1
                if(itemQuantity > 1){
                    itemQuantity--;
                }
                editQty.setText(String.valueOf(itemQuantity));
                break;

            case R.id.addButton:
                if(intent.getExtras() != null && activityName != null) {
                    if (editItemName.getText().toString().equals("")) {
                        editItemName.setError("Please enter the item to be borrowed");
                    } else {
                        itemName = editItemName.getText().toString();
                        itemDetails = editDetails.getText().toString();
                        urgentIsSet = urgentCheckBox.isChecked();

                        if (activityName.equals("addItemAct")) {
                            item = new Item(itemName, itemSize, itemDetails, itemQuantity, urgentIsSet);
                        } else if (activityName.equals("edit")) {
                            item.setName(itemName);
                            item.setDetails(itemDetails);
                            item.setSize(itemSize);
                            item.setQuantity(itemQuantity);
                            item.setUrgent(urgentIsSet);
                        }

                        if (item != null) {
                            Intent intent = new Intent();
                            intent.putExtra("selectedItem", item);
                            intent.putExtra("activity", activityName);
                            setResult(RESULT_OK, intent);
                            finish();//Back to main activity
                        }
                    }
                }
            break;
        }
    }
}