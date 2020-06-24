package com.example.orderup.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GalleryFragmentStaff extends Fragment {
    RecyclerView recyclerView;
    StaffAdapter staffAdapter;
    ArrayList<String> OrderNum;
    ArrayList<String> OrderCreators;
    ArrayList<String> OrderCreationTime;
    ArrayList<String> OrderStatus;
    String url="https://lamp.ms.wits.ac.za/home/s2039033/ProjectLori/getordersnew.php";
    private GalleryViewModel galleryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {

        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_staff_gallery, container, false);
        recyclerView = root.findViewById(R.id.recycle);

        FloatingActionButton fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                staffAdapter = new StaffAdapter(getActivity(),OrderNum,OrderCreators, OrderCreationTime, OrderStatus);
//                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//                recyclerView.setAdapter(staffAdapter);
//                staffAdapter.notifyDataSetChanged();
                addorders();
                Snackbar.make(view, "Refreshed", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

//        circlebutton = root.findViewById(R.id.fab);
        OrderNum = new ArrayList<>();
        OrderCreators = new ArrayList<>();
        OrderCreationTime = new ArrayList<>();
        OrderStatus = new ArrayList<>();
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
            String ordTime = jo.getString("ORDER_TIME");
            String ordStatus = jo.getString("ORDER_STATUS");
            OrderNum.add("Order Number: #"+ordNum);
            OrderCreators.add("Created by: "+creator);
            OrderCreationTime.add("Created at: " + ordTime);
            OrderStatus.add("Status: " + ordStatus);

        }
//        recyclerView.removeAllViews();
//        staffAdapter.notifyItemRangeRemoved(0,OrderNum.size());
        //Over here should be an error, need to add new Arraylists to the constructor first creation time then status
        staffAdapter = new StaffAdapter(getActivity(),OrderNum,OrderCreators, OrderCreationTime, OrderStatus);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(staffAdapter);

    }
}
