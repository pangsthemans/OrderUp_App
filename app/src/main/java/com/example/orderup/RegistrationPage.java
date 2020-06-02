package com.example.orderup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegistrationPage extends AppCompatActivity {

    private Button Staff;
    private Button Customer;
    private EditText Username;
    private EditText Password;
    private EditText reenterPassword;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);

        Password=findViewById(R.id.txt_password_regis);
        reenterPassword=findViewById(R.id.txt_reEnterPassword);
        Username=findViewById(R.id.txt_username_regis);

        Customer=(Button)findViewById(R.id.btn_CustomerHome);
        Staff = (Button)findViewById(R.id.StaffBtn);

        Customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCustomerHome();
            }
        });

        Staff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStaffHome();
            }
        });
    }

    public void openCustomerHome(){
        Intent intent = new Intent(this,CustomerHome.class);
        startActivity(intent);
    }

    public void openStaffHome(){
        Intent intent = new Intent(this,StaffHome.class);
        startActivity(intent);
    }
}
