package com.example.orderup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {
    //Using name to login for now but i mean we can always change that
    private EditText Name;
    private EditText Password;
    private Button Login;
    private Button Register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Connecting all the above declared variables to the ones on the layout
        Name = (EditText)findViewById(R.id.etName);
        Password=(EditText)findViewById(R.id.etPassword);
        Login=(Button)findViewById(R.id.btn_Login);
        Register=(Button)findViewById(R.id.btn_register);


        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegistrationPage();
            }
        });
    }
    public void openRegistrationPage(){
        Intent intent = new Intent(this,RegistrationPage.class);
        startActivity(intent);
    }



    //this function will essentially check if the password matches the one in the database , still need to salt it
    private void validatePassword(String userName, String userPassword){

    }


}
