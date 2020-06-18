package com.example.orderup.ui.gallery;

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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.orderup.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GalleryFragmentStaff extends Fragment {
    RecyclerView recyclerView;
    Adapter adapter;
    ArrayList<String> OrderNum;
    ArrayList<String> OrderCreators;
    String url="https://lamp.ms.wits.ac.za/home/s2039033/ProjectLori/getYourOrders.php";
    private GalleryViewModel galleryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_staff_gallery, container, false);
        recyclerView = root.findViewById(R.id.recycle);
        OrderNum = new ArrayList<>();
        OrderCreators = new ArrayList<>();
        //This method adds orders from the orders database to the orders table
        addorders();

        return root;
    }

    public void addorders(){

    StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
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
    });
    RequestQueue requestQueue= Volley.newRequestQueue(this.getActivity());
            requestQueue.add(stringRequest);
    }

    public void processJSON(String json) throws JSONException {
        JSONArray ja = new JSONArray(json);

        for(int i=0;i<ja.length();i++){
            JSONObject jo=ja.getJSONObject(i);
            String creator=jo.getString("ORDER_CREATOR");
            String ordNum=jo.getString("ORDER_ID");
            OrderNum.add("Order Number: #"+ordNum);
            OrderCreators.add(creator);

        }
        adapter = new Adapter(getActivity(),OrderNum,OrderCreators);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }
}
