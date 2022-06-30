package com.example.reminder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    private FirebaseUser user ;
    private DatabaseReference reference ;

    private String userID ;


    private Button logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        logout = (Button)findViewById(R.id.logout);
        {
            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FirebaseAuth.getInstance().signOut();
                    startActivity ( new Intent( ProfileActivity.this,MainActivity.class)) ;
                }
            });

            user = FirebaseAuth.getInstance().getCurrentUser();
            reference = FirebaseDatabase.getInstance().getReference("Users");
            userID= user.getUid();

            final TextView greetingTextView = (TextView)findViewById(R.id.greeting);
            final TextView fullNameTextView = (TextView)findViewById(R.id.fullName);
            final TextView emailTextView = (TextView)findViewById(R.id.emailAddress);
            final TextView dobTextView = (TextView)findViewById(R.id.dob);
            final TextView phoneTextView = (TextView)findViewById(R.id.phoneNumber);

            reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    user userProfile = snapshot.getValue(user.class);

                    if(userProfile!=null)
                    {
                        String firstName = userProfile.firstName;
                        String lastName = userProfile.lastName;
                        String email= userProfile.email;
                        String dob = userProfile.dob;
                        String phoneNumber = userProfile.phoneNumber;

                        greetingTextView.setText("Welcome,"+ "!");
                       fullNameTextView.setText(firstName +" "+ lastName);
                        emailTextView.setText(email);
                        dobTextView.setText(dob);
                        phoneTextView.setText(phoneNumber);


                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(ProfileActivity.this,"something wrong happened",Toast.LENGTH_LONG).show();
                }
            });




        }
    }
}