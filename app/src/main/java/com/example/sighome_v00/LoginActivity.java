package com.example.sighome_v00;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;     //파이어베이스 인증
    private DatabaseReference mDatabaseRef; //실시간 데이터베이스
    private EditText mEtEmail, mEtPwd;      //로그인 입력필드

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFirebaseAuth=FirebaseAuth.getInstance();
        mDatabaseRef= FirebaseDatabase.getInstance().getReference("SIGHOME");

        mEtEmail = findViewById(R.id.email_et);
        mEtPwd = findViewById(R.id.password_et);

        TextView login_btn = findViewById(R.id.login_btn);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 로그인 요청
                String strEmail = mEtEmail.getText().toString();
                String strPwd = mEtPwd.getText().toString();

                mFirebaseAuth.signInWithEmailAndPassword(strEmail,strPwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //로그인 성공
                            Toast.makeText(LoginActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, com.example.sighome_v00.MainActivity.class);
                            startActivity(intent);
                            finish(); //현재 액티비티 파괴
                        }else{
                            Toast.makeText(LoginActivity.this, "로그인 실패", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        TextView gotoRegister = (TextView) findViewById(R.id.goto_register1_btn);
        gotoRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //회원가입 화면으로 이동
                Intent intent = new Intent(LoginActivity.this, com.example.sighome_v00.Register1Activity.class);
                startActivity(intent);
            }
        });
    }
}