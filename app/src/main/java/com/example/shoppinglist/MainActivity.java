package com.example.shoppinglist;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements ItmAdapter.OnItemClickListner {
    private BottomNavigationView navBar;
    private ArrayList<Item> allItemsList;
    private ItmAdapter item_adapter;
    private ViewPagerAdapter viewPagerAdapter;
    private DBHandler dbHandler;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDBItems();
        allItemsList = dbHandler.getAllItems();
        initUI();
    }

    private void initUI(){
        viewPager = findViewById(R.id.viewPager);
        item_adapter = new ItmAdapter(allItemsList, "home",this); //Set to homepage when launch
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        //Add fragments into viewpager adapter
        Fragment1 fragment1 = Fragment1.newInstance("home");
        fragment1.setItmAdapter(item_adapter);
        viewPagerAdapter.addFragment(fragment1);

        fragment1 = Fragment1.newInstance("urgent");
        fragment1.setItmAdapter(item_adapter);
        viewPagerAdapter.addFragment(fragment1);

        fragment1 = Fragment1.newInstance("complete");
        fragment1.setItmAdapter(item_adapter);
        viewPagerAdapter.addFragment(fragment1);

        //Set the landing fragment
        viewPager.setAdapter(viewPagerAdapter);
        item_adapter.setFragment("home");
        viewPager.setCurrentItem(0);

        navBar = findViewById(R.id.bottomNavBar);
        navBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //Switch fragments based on
                switch (item.getItemId()) {
                    case R.id.homeNav:
                        item_adapter.setFragment("home");
                        viewPager.setCurrentItem(0);
                        getSupportActionBar().setTitle("Shopping List");
                        return true;
                    case R.id.urgentNav:
                        item_adapter.setFragment("urgent");
                        viewPager.setCurrentItem(1);
                        getSupportActionBar().setTitle("Urgent Items");
                        return true;
                    case R.id.completeNav:
                        item_adapter.setFragment("complete");
                        viewPager.setCurrentItem(2);
                        getSupportActionBar().setTitle("Items Bought");
                        return true;
                }
                return false;
            }
        });

        FloatingActionButton cartButton = findViewById(R.id.cartFloatButton);
        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("activity", "addItemAct");
                intent.setClass(getApplicationContext(), AddItem_Actvy.class);
                startActivityForResult(intent,1); //Start add item activity
            }
        });
    }


    @Override
    public void onToggleSwitch(Item item) {
        //Set item as complete
        item.setBought(true);
        String date = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(new Date());//Retrieve current date
        item.setDate(date);
        dbHandler.updateItem(item);// Update to database
        generateList();
        item_adapter.setItemList(allItemsList);
    }

    private void generateList() {
        if(allItemsList!=null){
            allItemsList.clear();
        }
        allItemsList = dbHandler.getAllItems();
    }

    @Override
    public void onItemClick(Item item, String fragmentName) {
        Intent intent = new Intent();
        intent.putExtra("selectedItem", item);
        intent.putExtra("fragment", fragmentName);
        intent.setClass(this, DisplayItem_Acty.class);
        startActivityForResult(intent,2);
    }

    //Delete an Item
    @Override
    public void onItemLongClick(final Item deleteItem) {
        //Alert dialog box to ask the user whether they are sure to delete
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Item").setMessage("Are you sure you want to delete " + deleteItem.getName() + " from the list?")
                .setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dbHandler.removeItem(deleteItem.getId());
                        generateList();
                        item_adapter.setItemList(allItemsList);
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void initDBItems() {
        dbHandler = new DBHandler(this, null, null, 1);
        //When DB is empty add the default items with their default status into the DB
        if (dbHandler.rowNums() == 0) {
            dbHandler.addItem(new Item("Bread", "Default","French Bread", 1, true));
            dbHandler.addItem(new Item("Milk", "Large","Gooday Milk",  3, true));
            dbHandler.addItem(new Item("Chocolate Bar", "Small","Calbury chocolate",  1, false));
            dbHandler.addItem(new Item("Instant noodle", "Default","Korean Rameen",  1, false));
            dbHandler.addItem(new Item("Juice", "Large","Apple Juice",  2, false));
            Item shampoo = new Item("Shampoo", "Small","Dove 2 in 1 Shampoo",  1, false);
            Item showerGel = new Item("Shower Gel", "Large","Normal shower gel",  1, false);
            shampoo.setDate("24 Aug 2020");
            showerGel.setDate("24 Aug 2020");
            shampoo.setBought(true);
            showerGel.setBought(true);
            dbHandler.addItem(shampoo);
            dbHandler.addItem(showerGel);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (data.getParcelableExtra("selectedItem") != null) {
                Item item = data.getParcelableExtra("selectedItem");
                //Add Item
                if (requestCode == 1 && resultCode == RESULT_OK) {
                    dbHandler.addItem(item);
                }
                //Edit Item
                if (requestCode == 2 && resultCode == RESULT_OK) {
                    dbHandler.updateItem(item);
                }
                generateList();
                item_adapter.setItemList(allItemsList);
            }
        }
    }
}