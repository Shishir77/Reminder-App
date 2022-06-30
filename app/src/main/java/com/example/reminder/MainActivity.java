package com.example.reminder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private TextView register , forgotPassword ;
    private EditText editTextEmail , editTextPassword ;
    private Button loginButton ;
    private FirebaseAuth mAuth ;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        register = (TextView) findViewById(R.id.register) ;
        register.setOnClickListener(this) ;
        loginButton = (Button)findViewById(R.id.loginButton);
        loginButton.setOnClickListener(this);

        editTextEmail= (EditText) findViewById(R.id.s_email);
        editTextPassword = (EditText)findViewById(R.id.s_password);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();

        forgotPassword = (TextView)findViewById(R.id.forgotPassword);
        forgotPassword.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){



            case R.id.register:
                Intent registration = new Intent() ;
                registration.setClass(this,RegisterUser.class) ;
                registration.setClass(this,RegisterUser.class) ;
                startActivity(registration);
                break;

            case R.id.loginButton:
                userLogin();

                break;
            case R.id.forgotPassword:
                startActivity(new Intent(this, ForgotPassword.class));
                break;





        }

    }

    private void userLogin()
    {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(email.isEmpty())
        {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return ;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            editTextEmail.setError("Please enter a valid email");
            editTextEmail.requestFocus();
            return ;
        }

        if(password.isEmpty())
        {
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return ;
        }

        if(password.length()<6)
        {
            editTextPassword.setError("Min password length is 6 characters");
            editTextPassword.requestFocus();
            return ;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()) {

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser() ;
                     if (user.isEmailVerified())
                     {
                         startActivity(new Intent(MainActivity.this,ProfileActivity.class));
                         //redirect to user profile
                         progressBar.setVisibility(View.GONE);
                     }
                     else {
                         user.sendEmailVerification();
                         Toast.makeText(MainActivity.this,"Check you email to verify your account",Toast.LENGTH_LONG).show();
                         progressBar.setVisibility(View.GONE);
                     }




                }
                else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this,"Failed to login , please check your creentials ",Toast.LENGTH_LONG).show();
                }

            }
        });

    }
}