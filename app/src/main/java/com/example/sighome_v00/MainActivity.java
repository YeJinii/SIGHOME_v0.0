package com.example.sighome_v00;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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


public class MainActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private DatabaseReference mRef;
    private FirebaseAuth mFirebaseAuth; //파이어베이스 인증
    private FirebaseUser mUser;
    private TextView mTvUserName, mqttSendBtn;

    private MqttClient mqttClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mFirebaseAuth=FirebaseAuth.getInstance();
        mUser=mFirebaseAuth.getCurrentUser();

        mqttSendBtn=findViewById(R.id.mqttSend_btn);

        try {
            mqttClient = new MqttClient("tcp://192.168.0.6:1883", MqttClient.generateClientId(),null);
        } catch (MqttException e) {
            e.printStackTrace();
        }
        try {
            mqttClient.connect();
        } catch (MqttException e) {
            e.printStackTrace();
        }

        try {
            Toast.makeText(MainActivity.this, "왜 안가노", Toast.LENGTH_SHORT).show();
            mqttClient.publish("/phone/turnoff/enter", new MqttMessage("android".getBytes()));
            mqttClient.publish("/phone/turnoff/bell", new MqttMessage("android".getBytes()));
            mqttClient.publish("/phone/turnoff/window", new MqttMessage("android".getBytes()));
        } catch (MqttException e) {
            e.printStackTrace();
        }
        /*
        mqttSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mqttClient.publish("/phone/turnoff/enter", new MqttMessage("android".getBytes()));
                    mqttClient.publish("/phone/turnoff/bell", new MqttMessage("android".getBytes()));
                    mqttClient.publish("/phone/turnoff/window", new MqttMessage("android".getBytes()));
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            }
        });*/

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
}