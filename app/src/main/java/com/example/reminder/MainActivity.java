package com.example.reminder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private TextView register ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        register = (TextView) findViewById(R.id.register) ;
        register.setOnClickListener(this) ;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.register:
                Intent registration = new Intent() ;
                registration.setClass(this,RegisterUser.class) ;
                registration.setClass(this,RegisterUser.class) ;
                startActivity(registration);

        }

    }
}