package com.example.orderup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegistrationPage extends AppCompatActivity {

    private Button Staff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);

        Staff = (Button)findViewById(R.id.StaffBtn);

        Staff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStaffHome();
            }
        });
    }

    public void openStaffHome(){
        Intent intent = new Intent(this,StaffHome.class);
        startActivity(intent);
    }
}
