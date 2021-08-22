package com.example.sighome_v00;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class ExplainActivity extends AppCompatActivity {

    private boolean doorBtnControl = true;
    private boolean bellBtnControl = true;
    private boolean windowBtnControl = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explain);

        ImageButton doorBtn = findViewById(R.id.enterBotton);
        ImageButton bellBtn = findViewById(R.id.bellBotton);
        ImageButton windowBtn = findViewById(R.id.windowBotton);


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

    }

    private TextView setColorInPartitial(String pre_string, String string, String color, TextView textView){
        SpannableStringBuilder builder = new SpannableStringBuilder(pre_string + string);
        builder.setSpan(new ForegroundColorSpan(Color.parseColor(color)), 0, pre_string.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.append(builder);
        return textView;
    }

}