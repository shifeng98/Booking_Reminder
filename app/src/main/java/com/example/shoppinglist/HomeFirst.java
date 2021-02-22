package com.example.shoppinglist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class HomeFirst extends AppCompatActivity {
    private Button button;

    android.widget.TextView textView;
    android.widget.Button btnSign, btnFirst;
    android.widget.ImageView ivSplash;
    android.view.animation.Animation animf,mationsec,mationthird;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_first);

        textView = findViewById(R.id.textView);
        btnFirst = (Button) findViewById(R.id.btnFirst);
        btnSign = (Button) findViewById(R.id.btnSign);
        ivSplash = findViewById(R.id.ivSplash);

        //load animation
        animf = AnimationUtils.loadAnimation( this, R.anim.animf);
        mationsec = AnimationUtils.loadAnimation( this, R.anim.motionsec);
        mationthird = AnimationUtils.loadAnimation( this, R.anim.motionthird);

        //passing animation
        ivSplash.startAnimation(animf);
        textView.startAnimation(mationsec);
        btnSign.startAnimation(mationthird);
        btnFirst.startAnimation(mationthird);

        //passing event
        btnFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.content.Intent a = new android.content.Intent(HomeFirst.this, LoginAct.class);
                a.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(a);
            }
        });
        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.content.Intent a = new android.content.Intent(HomeFirst.this, RegisterAct.class);
                a.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(a);
            }
        });
    }
}
