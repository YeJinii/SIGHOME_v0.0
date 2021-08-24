package com.example.sighome_v00;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class ExplainActivity extends AppCompatActivity {

    private boolean doorBtnControl = true;
    private boolean bellBtnControl = true;
    private boolean windowBtnControl = true;
    private ImageView backIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explain);

        ImageButton doorBtn = findViewById(R.id.enterBotton);
        ImageButton bellBtn = findViewById(R.id.bellBotton);
        ImageButton windowBtn = findViewById(R.id.windowBotton);
        backIv = findViewById(R.id.back_iv);

        doorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (doorBtnControl) {
                    doorBtn.setBackgroundResource(R.drawable.enter_img_2);
                    doorBtnControl = false;
                } else {
                    doorBtn.setBackgroundResource(R.drawable.enter_img_1);
                    doorBtnControl = true;
                }
            }
        });

        bellBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bellBtnControl) {
                    bellBtn.setBackgroundResource(R.drawable.bell_img_2);
                    bellBtnControl = false;
                } else {
                    bellBtn.setBackgroundResource(R.drawable.bell_img_1);
                    bellBtnControl = true;
                }
            }
        });

        windowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (windowBtnControl) {
                    windowBtn.setBackgroundResource(R.drawable.window_img_2);
                    windowBtnControl = false;
                } else {
                    windowBtn.setBackgroundResource(R.drawable.window_img_1);
                    windowBtnControl = true;
                }
            }
        });

        backIv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //회원가입 화면으로 이동
                Intent intent = new Intent(ExplainActivity.this, com.example.sighome_v00.MainActivity.class);
                startActivity(intent);
            }
        });

    }



}