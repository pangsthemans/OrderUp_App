package com.example.orderup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;

public class RegistrationPage extends AppCompatActivity {

    private Button Staff;
    private Button Customer;
    private Button ConfirmRegis;
    private EditText Username;
    private EditText Password;
    private ProgressBar loading;
    private EditText reenterPassword;
    private EditText emailAd;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);
        //for some reason the line below wont allow me to declare this damn button. not sure why
        ConfirmRegis= findViewById(R.id.btn_confirmRegis);
        Password=findViewById(R.id.txt_password_regis);
        emailAd=findViewById(R.id.txt_email);
        reenterPassword=findViewById(R.id.txt_reEnterPassword);
        Username=findViewById(R.id.txt_username_regis);

        Customer= findViewById(R.id.btn_CustomerHome);
        Staff = findViewById(R.id.StaffBtn);

        ConfirmRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

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

    public void Regist(){
        loading.setVisibility(View.VISIBLE);
        ConfirmRegis.setVisibility(View.GONE);

        final String name = this.Username.getText().toString().trim();
        final String password = this.Password.getText().toString().trim();
        final String email = this.emailAd.getText().toString().trim();

//        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        })
//        {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                return super.getParams();
//            }
//        };
    }

    public void openStaffHome(){
        Intent intent = new Intent(this,StaffHome.class);
        startActivity(intent);
    }
}
