package com.example.orderup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {
    private EditText Email;
    private EditText Password;
    private Button Login_button;
    private Button Register;
    private static String URL_LOGIN="https://lamp.ms.wits.ac.za/home/s2039033/ProjectLori/login.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Connecting all the above declared variables to the ones on the layout
        Email = (EditText)findViewById(R.id.txt_email);
        Password=(EditText)findViewById(R.id.etPassword);
        Login_button=(Button)findViewById(R.id.btn_Login);
        Register=(Button)findViewById(R.id.btn_register);


        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegistrationPage();
            }
        });

        Login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = Email.getText().toString().trim();
                final String password = Password.getText().toString().trim();

                if (!email.isEmpty() || !password.isEmpty()){
                    Login(email,password);
                }else{
                    Email.setError("Please Enter A Valid Email");
                    Password.setError("Please Enter A Valid Password");
                }
            }
        } );
    }

    private void Login(final String email, final String password) {
        Login_button.setVisibility(View.GONE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success= jsonObject.getString("success");
                            JSONArray jsonArray=jsonObject.getJSONArray("login");
                            if(success.equals("1")){
                                for(int i=0;i<jsonArray.length();i++){
                                    JSONObject object=jsonArray.getJSONObject(i);
                                    String username=object.getString("USER_USERNAME").trim();
                                    String email=object.getString("USER_EMAIL").trim();
                                    String usertype=object.getString("USER_TYPE").trim();
                                    if (usertype.equals("Customer")){
                                        Intent intent = new Intent(LoginActivity.this,CustomerHome.class);
                                        intent.putExtra("username",username);
                                        intent.putExtra("user_email",email);
                                        startActivity(intent);
                                    }
                                    else if(usertype.equals("Staff")){
                                        Intent intent = new Intent(LoginActivity.this,StaffHome.class);
                                        intent.putExtra("username",username);
                                        intent.putExtra("user_email",email);
                                        startActivity(intent);
                                    }

                                    Toast.makeText(LoginActivity.this,
                                            "Successful Login. \nYour Name : "
                                                    +username+"\nYour Email :"+
                                                    email,Toast.LENGTH_SHORT).show();
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Login_button.setVisibility(View.VISIBLE);
                            Toast.makeText(LoginActivity.this,"Error "+e.toString(),Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Login_button.setVisibility(View.VISIBLE);
                        Toast.makeText(LoginActivity.this,"Error "+error.toString(),Toast.LENGTH_SHORT).show();

                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params=new HashMap<>();
                params.put("USER_EMAIL",email);
                params.put("USER_PASSWORD",password);
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void openRegistrationPage(){
        Intent intent = new Intent(this,RegistrationPage.class);
        startActivity(intent);
    }
}
