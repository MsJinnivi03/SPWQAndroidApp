package com.spwqapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private long backPressedTime;
    private Toast backToast;
    private FirebaseAuth mAuth;
    private EditText email, password;
    private Button btnLogin;
    private TextView textRegister;
    boolean passVis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);
        btnLogin = findViewById(R.id.login);
        textRegister = findViewById(R.id.text_register);

        password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int Right=2;
                if (motionEvent.getAction()==MotionEvent.ACTION_UP){
                    if(motionEvent.getRawX()>=password.getRight()-password.getCompoundDrawables()[Right].getBounds().width()){
                        int selection=password.getSelectionEnd();
                        if (passVis) {
                            password.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_eye, 0);
                            password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passVis=false;
                        }else{
                            password.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_eye2, 0);
                            password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passVis=true;
                        }
                        password.setSelection(selection);
                        return true;
                    }

                }

                return false;
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        textRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });

    }

    @Override
    public void onBackPressed() {

        if (backPressedTime + 2000 > System.currentTimeMillis()){
            super.onBackPressed();
            finish();
            return;
        }else{
                backToast = Toast.makeText(getBaseContext(), "Press back again",Toast.LENGTH_SHORT);
                backToast.show();
        }

        backPressedTime = System.currentTimeMillis();

    }




    private void login()
    {
        String user = email.getText().toString().trim();
        String pass = password.getText().toString().trim();
        if (user.isEmpty())
        {
            email.setError("Email can be Empty");
        }
        if (pass.isEmpty())
        {
            password.setError("Password can not be Empty");
        }
        else
            {
                mAuth.signInWithEmailAndPassword(user,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                            email.setText("");
                            password.setText("");

                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                            
                        }
                        else
                        {
                            Toast.makeText(LoginActivity.this, "Login Failed"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
    }

}
