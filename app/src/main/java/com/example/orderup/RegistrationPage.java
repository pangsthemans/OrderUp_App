package com.example.orderup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

public class RegistrationPage extends AppCompatActivity {

    private Button Staff;
    private Button Customer;
    private Button ConfirmRegis;
    private EditText Username;
    private EditText Password;
//    private ProgressBar loading;
    private EditText reenterPassword;
    private EditText emailAd;
//    private EditText name;
    private static String URL_REGIST="https://lamp.ms.wits.ac.za/home/s2039033/register.php";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);
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
                Regist();
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
//        loading.setVisibility(View.VISIBLE);
        ConfirmRegis.setVisibility(View.GONE);

        final String username = this.Username.getText().toString().trim();
        final String password = this.Password.getText().toString().trim();
        final String email = this.emailAd.getText().toString().trim();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject=new JSONObject(response);
                            String success= jsonObject.getString("success");

                            if (success.equals("1")){
                                Toast.makeText(RegistrationPage.this,"Register Success!",Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(RegistrationPage.this,"Register Error!"+e.toString(),Toast.LENGTH_SHORT).show();
//                            loading.setVisibility(View.GONE);
                            ConfirmRegis.setVisibility(View.VISIBLE);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegistrationPage.this,"Register Error!"+error.toString(),Toast.LENGTH_SHORT).show();
//                loading.setVisibility(View.GONE);
                ConfirmRegis.setVisibility(View.VISIBLE);
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params= new HashMap<>();
                params.put("username",username);
                params.put("email",email);
                params.put("password",password);
                return super.getParams();

            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void openStaffHome(){
        Intent intent = new Intent(this,StaffHome.class);
        startActivity(intent);
    }
}
