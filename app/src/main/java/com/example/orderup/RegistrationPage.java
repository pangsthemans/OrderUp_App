package com.example.orderup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
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
    private RadioButton StaffRadio;
    private RadioButton CustRadio;
    private Button ConfirmRegis;
    private EditText Username;
    private EditText Password;
//    private ProgressBar loading;
    private EditText reenterPassword;
    private EditText emailAd;
    DatabaseHandler DatabaseHandler;
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
        StaffRadio = findViewById(R.id.radioStaff);
        CustRadio = findViewById(R.id.radioCustomer);
        DatabaseHandler = new DatabaseHandler(this);

        //Code to make sure that only Customer or Staff is selected
        StaffRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(StaffRadio.isChecked()){
                    CustRadio.setChecked(false);
                }
            }
        });

        CustRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CustRadio.isChecked()){
                    StaffRadio.setChecked(false);
                }
            }
        });

        ConfirmRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!reenterPassword.getText().toString().trim().equals(Password.getText().toString().trim())){
                    Toast.makeText(RegistrationPage.this,"Please make sure passwords match",Toast.LENGTH_SHORT).show();
                }
                else{
                    //Get the data for the text fields and validate ID
                    String name = Username.getText().toString();
                    String password = Password.getText().toString();
                    String email = emailAd.getText().toString();
                    int  id = CustomerOrStaff();
                    if(id != -1){
                        AddLocalData(name, password, email, id);
                        Regist();
                        if(id == 0){
                            openStaffHome();
                        }
                        else{
                            openCustomerHome();
                        }
                    }
                    else{
                        Toast.makeText(RegistrationPage.this, "Please select Customer or Staff", Toast.LENGTH_SHORT).show();
                    }
                }
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
    }// end of onCreate method

    //Below are methods we use to get data and place it in the databases
    public void AddLocalData(String name, String email, String Password, int id){
        boolean insertData = DatabaseHandler.addData(name, email, Password, id);

        if(insertData){
            Toast.makeText(this, "Successfully Registered", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }


    //method to open customer home page activity
    public void openCustomerHome(){
        Intent intent = new Intent(this,CustomerHome.class);
        startActivity(intent);
    }

    //method to add users to the MySQL databse on lamp server
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
                params.put("CUS_USERNAME",username);
                params.put("CUS_EMAIL",email);
                params.put("CUS_PASSWORD",password);
                return params;

            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    //Open staff home page activity
    public void openStaffHome(){
        Intent intent = new Intent(this,StaffHome.class);
        startActivity(intent);
    }

    //Function to check which button selected by User
    public int CustomerOrStaff(){
        if(StaffRadio.isChecked() &&  !CustRadio.isChecked()){
            return 0;
        }
        if(CustRadio.isChecked() && !StaffRadio.isChecked()){
            return 1;
        }
        else{
            return -1;
        }
    }

}
