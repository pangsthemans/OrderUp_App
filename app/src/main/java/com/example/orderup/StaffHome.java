package com.example.orderup;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class StaffHome extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private MenuItem logout;
    public int numThumbsUp=0;
    public int numThumbsdown=0;
    private static String url="https://lamp.ms.wits.ac.za/home/s2039033/ProjectLori/getStaffRating.php";
    public ProgressBar progressBar;
    public TextView Rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_staff_home);
        Spinner sp =(Spinner) findViewById(R.id.spinner);
        logout=findViewById(R.id.action_logout);

        setContentView(R.layout.activity_staff_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);


        updateNavHeader();
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        updateNavHeader();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.staff_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_logout:
                Toast.makeText(this,"Goodbye",Toast.LENGTH_SHORT).show();
                finish();
                return true;
            case R.id.action_settings:
                Toast.makeText(this,"This is the settings",Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void updateNavHeader(){
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = headerView.findViewById(R.id.nav_username);
        TextView navEmail = headerView.findViewById(R.id.nav_email);

        Bundle info = getIntent().getExtras();
        String name = info.getString("username");
        String email = info.getString("user_email");
        getRating(name);
        navUsername.setText(name);
        navEmail.setText(email);

    }
    public void getRating(final String staffname){
        NavigationView navigationView=findViewById(R.id.nav_view);
        View headerView=navigationView.getHeaderView(0);
        progressBar=headerView.findViewById(R.id.progressBar);
        Rating=headerView.findViewById(R.id.Rating);
        StringRequest stringRequest= new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray ja = new JSONArray(response);
                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject jo = ja.getJSONObject(i);
                        String rating = jo.getString("ORDER_RATING");
                        String count = jo.getString("COUNT");
                        if (rating.equals("BAD")) {
                            numThumbsdown = Integer.parseInt(count);

                        } else if (rating.equals("GOOD")) {
                            numThumbsUp = Integer.parseInt(count);
                        }
                    }
                    if(numThumbsdown!=0 && numThumbsUp!=0){
                        double total=numThumbsdown+numThumbsUp;
                        double progress=Math.round((numThumbsUp/total)*100);
                        progressBar.setProgress((int)progress);
                    }else{
                        progressBar.setProgress(0);
                    }
                    Rating.setText("Good: "+ numThumbsUp+"\nBad: "+numThumbsdown);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(StaffHome.this,"Error setting Rating",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params= new HashMap<>();
                params.put("ORDER_CREATOR",staffname);
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed(){
        moveTaskToBack(true);
    }
}
