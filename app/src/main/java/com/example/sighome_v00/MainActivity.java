package com.example.sighome_v00;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

//060700
public class MainActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private DatabaseReference mRef;
    private FirebaseAuth mFirebaseAuth; //파이어베이스 인증
    private FirebaseUser mUser;
    private TextView mTvUserName, turnOffBtn;
    private ImageView modeIv;

    private DrawerLayout mDrawerLayout;
    private Context context = this;

    private MqttClient mqttClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mFirebaseAuth=FirebaseAuth.getInstance();
        mUser=mFirebaseAuth.getCurrentUser();

        turnOffBtn=findViewById(R.id.turnOff_btn);
        modeIv=findViewById(R.id.mode_iv);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        getSupportActionBar().setTitle("스마트 홈 케어 시스템");

        mDrawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.navi_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();

                int id = menuItem.getItemId();
                String title = menuItem.getTitle().toString();

                if(id == R.id.inside_mode){//실내 모드
                    toolbar.setBackgroundColor(Color.parseColor("#BDD7EE"));
                    modeIv.setImageResource(R.drawable.ic_inside_mode);
                }
                else if(id == R.id.outside_mode){//실외 모드
                    toolbar.setBackgroundColor(Color.parseColor("#FFC738"));
                    modeIv.setImageResource(R.drawable.ic_outside_mode);
                }
                else if(id == R.id.app_intro){//앱 소개
                    Intent intent = new Intent(MainActivity.this, com.example.sighome_v00.ExplainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else if(id == R.id.logout){//로그아웃
                    mFirebaseAuth.signOut();
                    Intent intent = new Intent(MainActivity.this, com.example.sighome_v00.LoginActivity.class);
                    startActivity(intent);
                    finish(); //현재 액티비티 파괴
                }

                return true;
            }
        });

//        try {
//            mqttClient = new MqttClient("tcp://111.118.51.164:1883", MqttClient.generateClientId(),null);
//        } catch (MqttException e) {
//            e.printStackTrace();
//        }
//        try {
//            mqttClient.connect();
//        } catch (MqttException e) {
//            e.printStackTrace();
//        }

        turnOffBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                turnOffBtn.setBackgroundResource(R.drawable.round_square_3);
//                try {
//                    mqttClient.publish("/phone/turnoff/enter", new MqttMessage("android".getBytes()));
//                    mqttClient.publish("/phone/turnoff/bell", new MqttMessage("android".getBytes()));
//                    mqttClient.publish("/phone/turnoff/window", new MqttMessage("android".getBytes()));
//                } catch (MqttException e) {
//                    e.printStackTrace();
//                }
            }
        });

        String userId = mUser.getUid();
        mTvUserName=(TextView)findViewById(R.id.username_tv);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String userName = snapshot.child("UserAccount").child(userId).child("userName").getValue().toString();
                    mTvUserName.setText(userName);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

        //추가된 소스, ToolBar에 추가된 항목의 select 이벤트를 처리하는 함수
    public boolean onOptionsItemSelected(MenuItem item) {
        //return super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case android.R.id.home:{//메뉴 버튼
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            }

        }
        return super.onOptionsItemSelected(item);
    }
}