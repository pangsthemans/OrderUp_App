package com.example.orderup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationPage extends AppCompatActivity {


    private RadioButton StaffRadio;
    private RadioButton CustRadio;
    private Button ConfirmRegis;
    private EditText Username;
    private EditText Password;
    private EditText reenterPassword;
    private EditText emailAd;
    private static String URL_REGIST="https://lamp.ms.wits.ac.za/home/s2039033/ProjectLori/register.php";
//    private ProgressBar loading;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);
        ConfirmRegis= findViewById(R.id.btn_confirmRegis);
        Password=findViewById(R.id.txt_password_regis);
        emailAd=findViewById(R.id.txt_email);
        reenterPassword=findViewById(R.id.txt_reEnterPassword);
        Username=findViewById(R.id.txt_username_regis);

        StaffRadio = findViewById(R.id.radioStaff);
        CustRadio = findViewById(R.id.radioCustomer);

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
        //Multiple methods and if statements to make sure their password is secure
        ConfirmRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!reenterPassword.getText().toString().trim().equals(Password.getText().toString().trim())) {
                    Password.setError("Please make sure passwords match");
                    reenterPassword.setError("Please make sure passwords match");
                }
                else if(Password.getText().toString().trim().isEmpty()){
                    Password.setError("Please Enter a Password");
                }
                else if(emailAd.getText().toString().trim().isEmpty()){
                    emailAd.setError("Please Enter a Email");
                }
                else if(Username.getText().toString().trim().toLowerCase().equals(Password.getText().toString().trim().toLowerCase())){
                    Username.setError("Password and Username Cannot be Identical");
                    Password.setError("Password and Username Cannot be Identical");
                }
                else if(Username.getText().toString().trim().isEmpty()){
                    Username.setError("Please Enter a Username");
                }
                else if(!Password_Validation(Password.getText().toString().trim())){
                    Password.setError("Password must contain a SPECIAL character,LETTER, DIGIT and have Eight(8) characters");
                }
                else{
                    //validates that an id was chosen
                    int  id = CustomerOrStaff();
                    //adds the user to the data base
                    if(id != -1){
                        Regist();
                    }
                    else{
                        Toast.makeText(RegistrationPage.this, "Please select Customer or Staff", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }// end of onCreate method




    //method to open customer home page activity used for development purposes so we don't have to sign in every time we wanna test something
    public void openCustomerHome(String name, String email){
        Intent intent = new Intent(this,CustomerHome.class);
        intent.putExtra("username", name);
        intent.putExtra("user_email", email);
        startActivity(intent);
        finish();
    }

    //method to add users to the MySQL database on lamp server
    public void Regist(){
//        loading.setVisibility(View.VISIBLE);
        ConfirmRegis.setVisibility(View.GONE);

        final String username = this.Username.getText().toString().trim();
        final String password = this.Password.getText().toString().trim();
        final String email = this.emailAd.getText().toString().trim();
        final int usertype = CustomerOrStaff();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject=new JSONObject(response);
                            String success= jsonObject.getString("success");
                            String message=jsonObject.getString("message");
                            if (success.equals("1")){
                                Toast.makeText(RegistrationPage.this,"Register Success!",Toast.LENGTH_SHORT).show();
                                String name = Username.getText().toString().trim();
                                String password = Password.getText().toString().trim();
                                String email = emailAd.getText().toString().trim();
                                int  id = CustomerOrStaff();
                                if(id == 0){
                                    openStaffHome(name, email);
                                }
                                else{
                                    openCustomerHome(name, email);
                                }
                            }
                            else if(success.equals("0")){
                                Toast.makeText(RegistrationPage.this,message,Toast.LENGTH_SHORT).show();
                                ConfirmRegis.setVisibility(View.VISIBLE);
                            }
                            else if(success.equals("0") && message.equals("User Already Exists")){
                                Toast.makeText(RegistrationPage.this,"Registration Failure!\n User Already Exists",Toast.LENGTH_SHORT).show();
                                ConfirmRegis.setVisibility(View.VISIBLE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(RegistrationPage.this,"Register Error!"+e.toString(),Toast.LENGTH_SHORT).show();
//                            loading.setVisibility(View.GONE);
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
                params.put("USER_USERNAME",username);
                params.put("USER_EMAIL",email);
                params.put("USER_PASSWORD",password);
                params.put("USER_TYPE", Integer.toString(usertype));
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    //Open staff home page activity
    public void openStaffHome(String name, String email){
        Intent intent = new Intent(this,StaffHome.class);
        intent.putExtra("username", name);
        intent.putExtra("user_email", email);
        startActivity(intent);
        finish();
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
    //this method validates the strength of the users password
    public static boolean Password_Validation(String password)
    {

        if(password.length()>=8)
        {
            Pattern letter = Pattern.compile("[a-zA-z]");
            Pattern digit = Pattern.compile("[0-9]");
            Pattern special = Pattern.compile ("[!@#$%&*()_+=|<>?{}\\[\\]~-]");
            //Pattern eight = Pattern.compile (".{8}");
            Matcher hasLetter = letter.matcher(password);
            Matcher hasDigit = digit.matcher(password);
            Matcher hasSpecial = special.matcher(password);

            return hasLetter.find() && hasDigit.find() && hasSpecial.find();

        }
        else
            return false;

    }

}
