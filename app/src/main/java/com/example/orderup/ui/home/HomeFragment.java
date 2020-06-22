package com.example.orderup.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.orderup.R;
import com.example.orderup.ui.gallery.CustomerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    ArrayList<String> OrderNumber;
    ArrayList<String> OrderName;
    CustomerAdapter adapter;
    Bundle info;
    RecyclerView recyclerView;
    String url="https://lamp.ms.wits.ac.za/home/s2039033/ProjectLori/getYourOrders.php";


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        info = this.getActivity().getIntent().getExtras();
        String username= info.getString("username");
        recyclerView=root.findViewById(R.id.recycle_cust);
        Log.d("TEST",username);
        OrderNumber= new ArrayList<>();
        OrderName=new ArrayList<>();
        addorders(username);

        return root;
    }
    public void addorders(final String username){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("TEST","INSIDE METHOD");
                            processJSON(response);
                        }

                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params= new HashMap<>();
                params.put("ORDER_OWNER",username);
                return params;
            }
        };


        RequestQueue requestQueue= Volley.newRequestQueue(this.getActivity());
        requestQueue.add(stringRequest);
    }

    public void processJSON(String json) throws JSONException {
        JSONArray ja = new JSONArray(json);

        for(int i=0;i<ja.length();i++){
            JSONObject jo=ja.getJSONObject(i);
            String name=jo.getString("ORDER_OWNER");
            String ordNum=jo.getString("ORDER_ID");
            OrderNumber.add("Order Number: #"+ordNum);
            OrderName.add(name);
            Log.d("TEST",name);
            //Need to add The creation time and Status to new arraylists here
        }
        //Over here should be an error, need to add new Arraylists to the constructor first creation time then status
        adapter = new CustomerAdapter(getActivity(),OrderNumber,OrderName);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }


}
