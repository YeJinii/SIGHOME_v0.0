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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register1Activity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;     //파이어베이스 인증
    private FirebaseUser mUser;
    private DatabaseReference mDatabaseRef; //실시간 데이터베이스
    private EditText mEtEmail, mEtPwd, mEtReRwd;      //회원가입 입력필드
    private TextView mBtnRegister;          //회원가입 버튼

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1);

        mFirebaseAuth=FirebaseAuth.getInstance();
        mUser=mFirebaseAuth.getCurrentUser();
        mDatabaseRef= FirebaseDatabase.getInstance().getReference("SIGHOME");

        mEtEmail = findViewById(R.id.regist_email_et);
        mEtPwd = findViewById(R.id.regist_password_et);
        mEtReRwd = findViewById(R.id.regist_repassword_et);

        mBtnRegister = findViewById(R.id.send_email_bt);

        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //회원가입 처리 시작
                String strEmail = mEtEmail.getText().toString();
                String strPwd = mEtPwd.getText().toString();
                String strReRwd = mEtReRwd.getText().toString();

                int status = NetworkStatus.getConnectivityStatus(getApplicationContext());

                if(strEmail==null||strEmail.isEmpty()||strPwd==null||strPwd.isEmpty()||strReRwd==null||strReRwd.isEmpty()){//공백란이 있음
                    Toast.makeText(Register1Activity.this, "로그인 이메일/비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                }else if(strPwd!=strReRwd){
                    Toast.makeText(Register1Activity.this, "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                }else if(status==NetworkStatus.TYPE_NOT_CONNECTED){
                    Toast.makeText(Register1Activity.this, "네트워크 상태를 확인해주세요", Toast.LENGTH_SHORT).show();
                }else{
                    //Firebase Auth 진행
                    mFirebaseAuth.createUserWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(Register1Activity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {//가입 성공시
                                FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
                                UserAccount account = new UserAccount();

                                account.setIdToken(firebaseUser.getUid());
                                account.setEmailId(firebaseUser.getEmail());
                                account.setPassword(strPwd);

                                //setValue : database에 insert (삽입) 행위
                                mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).setValue(account);

                                mUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){//이메일 인증 성공 시
                                            Toast.makeText(Register1Activity.this, "인증 이메일이 발송되었습니다", Toast.LENGTH_SHORT).show();

                                        }else{
                                            if(status==NetworkStatus.TYPE_NOT_CONNECTED){
                                                Toast.makeText(Register1Activity.this, "네트워크 상태를 확인해주세요", Toast.LENGTH_SHORT).show();
                                            }
                                            else{
                                                Toast.makeText(Register1Activity.this, "이메일 발송 실패", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                });


                                Intent intent = new Intent(Register1Activity.this, MainActivity.class);
                                startActivity(intent);

                            }
                        }
                    });
                }
            }
        });



    }
}