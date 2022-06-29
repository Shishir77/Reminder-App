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
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUser extends AppCompatActivity  implements View.OnClickListener{

    private FirebaseAuth mAuth;
    private TextView banner ;
    private Button registerUser;
    private EditText editTextFName , editTextLName , editTextPassword , editTextPhoneNumber ,editTextDOB , editTextEmail ;

    private ProgressBar progressBar ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        mAuth = FirebaseAuth.getInstance();
        banner=(TextView)findViewById(R.id.reminderBanner) ;
        banner.setOnClickListener(this);

        registerUser = (Button)findViewById(R.id.registerUserButton);
        registerUser.setOnClickListener(this);

        editTextFName = (EditText)findViewById(R.id.firstName);
        editTextLName= (EditText)findViewById(R.id.lastName);
        editTextDOB= (EditText)findViewById(R.id.dob);
        editTextPassword=(EditText)findViewById(R.id.r_password);
        editTextEmail= (EditText) findViewById(R.id.email);
        editTextPhoneNumber = (EditText)findViewById((R.id.phoneNumber));

        progressBar = (ProgressBar) findViewById(R.id.r_progressBar) ;

    }

    @Override
    public void onClick(View view) {


        switch (view.getId())
        {
            case R.id.reminderBanner:
                startActivity(new Intent(this, MainActivity.class));

                break;
            case R.id.registerUserButton:
                registerUser() ;
                break;

        }


    }
    private void registerUser (){
        String FName = editTextFName.getText().toString().trim();
        String LName = editTextLName.getText().toString().trim();
        String DOB = editTextDOB.getText().toString().trim();
        String Email = editTextEmail.getText().toString().trim();
        String Password = editTextPassword.getText().toString().trim();
        String PhoneNumber = editTextPhoneNumber.getText().toString().trim();

        if (FName.isEmpty() ){
            editTextFName.setError("First name is required") ;
            editTextFName.requestFocus() ;
            return ;
        }
        if (LName.isEmpty() ){
            editTextLName.setError("Last name is required") ;
            editTextLName.requestFocus() ;
            return ;
        }
        if (DOB.isEmpty() ){
            editTextDOB.setError("DOB is required") ;
            editTextDOB.requestFocus() ;
            return ;
        }
        if (Email.isEmpty() ){
            editTextEmail.setError("Email is required") ;
            editTextEmail.requestFocus() ;
            return ;
        }

        //check email patterns

        if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches())
        {   editTextEmail.setError("Please provide a valid email") ;
            editTextEmail.requestFocus() ;
            return ;
        }
        if (Password.isEmpty() ){
            editTextPassword.setError("Password is required") ;
            editTextPassword.requestFocus() ;
            return ;
        }

        if (PhoneNumber.isEmpty() ){
            editTextPhoneNumber.setError("Phone number is required") ;
            editTextPhoneNumber.requestFocus() ;
            return ;
        }

        if(editTextPassword.length()<6) //firebase default requires 6 length at least
        {
            editTextPassword.setError("Min password length should be at least 6 character");
            editTextPassword.requestFocus() ;
            return ;

        }

        progressBar.setVisibility(View.VISIBLE); // setting progress bar visible after clicking register button
        mAuth.createUserWithEmailAndPassword(Email,Password) //firebase creates a user obj
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){    user user = new user(FName,LName,DOB,Email,PhoneNumber);
                        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                             if (task.isSuccessful())
                             {
                                 Toast.makeText(RegisterUser.this , "User has been registered", Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);  }
                             else {  Toast.makeText(RegisterUser.this , "User not istered", Toast.LENGTH_LONG).show();
                                 progressBar.setVisibility(View.GONE); }                                   }
                                                      });

                    }else {  Toast.makeText(RegisterUser.this , "User not registered", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE); } }});


    }
}