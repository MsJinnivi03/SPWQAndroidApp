package com.spwqapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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


public class SignupActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText email, password,repassword,fullname,age;
    private Button btnRegister;
    private TextView textLogin;
    boolean passVis,repassVis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();
        fullname = findViewById(R.id.Fullname);
        age = findViewById(R.id.Age);
        email = findViewById(R.id.register_email);
        password = findViewById(R.id.register_password);
        repassword = findViewById(R.id.register_repassword);
        btnRegister = findViewById(R.id.register);
        textLogin = findViewById(R.id.text_login);

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
        repassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int Right=2;
                if (motionEvent.getAction()==MotionEvent.ACTION_UP){
                    if(motionEvent.getRawX()>=repassword.getRight()-repassword.getCompoundDrawables()[Right].getBounds().width()){
                        int selection=password.getSelectionEnd();
                        if (repassVis) {
                            repassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_eye, 0);
                            repassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            repassVis=false;
                        }else{
                            repassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_eye2, 0);
                            repassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            repassVis=true;
                        }
                        repassword.setSelection(selection);
                        return true;
                    }

                }

                return false;
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Signup();
            }
        });

        textLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            }
        });


    }



    private void Signup()
    {
        String name = fullname.getText().toString();
        String age1 = age.getText().toString();
        String user = email.getText().toString().trim();
        String pass = password.getText().toString().trim();
        String repass = repassword.getText().toString();
        if (name.isEmpty())
        {
            fullname.setError("Indicate your name");
        }
        if (age1.isEmpty())
        {
            age.setError("Indicate your Age");
        }
        if (user.isEmpty())
        {
            email.setError("Email can be Empty");
        }
        if (pass.isEmpty() ||pass.length()<6)
        {
            password.setError("Enter proper Password ");
        }
        if (!pass.equals(repass)||repass.isEmpty())
        {
            repassword.setError("Password not match");
        }
        else{
            mAuth.createUserWithEmailAndPassword(user,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful())
                    {
                        Toast.makeText(SignupActivity.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                        email.setText("");
                        password.setText("");
                        fullname.setText("");
                        age.setText("");
                        repassword.setText("");
                    }
                    else
                    {
                        Toast.makeText(SignupActivity.this, "Registration Failed"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
