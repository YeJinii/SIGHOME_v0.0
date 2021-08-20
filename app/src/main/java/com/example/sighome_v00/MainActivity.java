package com.example.sighome_v00;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
    private TextView mTvUserName, mqttSendBtn;

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

        mqttSendBtn=findViewById(R.id.mqttSend_btn);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        getSupportActionBar().setTitle("SIG HOME");


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

                }
                else if(id == R.id.outside_mode){//실외 모드

                }
                else if(id == R.id.app_intro){//앱 소개

                }
                else if(id == R.id.logout){//로그아웃

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

        mqttSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mqttSendBtn.setBackgroundResource(R.drawable.round_square);
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

    //추가된 소스, ToolBar에 menu.xml을 인플레이트함
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    //추가된 소스, ToolBar에 추가된 항목의 select 이벤트를 처리하는 함수
    public boolean onOptionsItemSelected(MenuItem item) {
        //return super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case android.R.id.home:{//메뉴 버튼
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            }
            case R.id.logout_btn: {
                // User chose the "Settings" item, show the app settings UI...
                Toast.makeText(getApplicationContext(), "로그아웃 버튼이 클릭됨", Toast.LENGTH_LONG).show();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}