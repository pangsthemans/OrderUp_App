package com.example.orderup;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {
    //Using name to login for now but i mean we can always change that
    private EditText Name;
    private EditText Password;
    private Button Login;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Connecting all the above declaed variables to the ones on the layout
        Name = (EditText)findViewById(R.id.etName);
        Password=(EditText)findViewById(R.id.etPassword);
        Login=(Button)findViewById(R.id.btn_Login);
    }

    //this function will essentially check if the password matches the one in the database , still need to salt it
    private void validatePassword(String userName, String userPassword){

    }


}
