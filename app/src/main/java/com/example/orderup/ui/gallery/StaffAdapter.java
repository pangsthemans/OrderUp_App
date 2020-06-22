package com.example.orderup.ui.gallery;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.example.orderup.StaffHome;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StaffAdapter extends RecyclerView.Adapter<StaffAdapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    private ArrayList<String> OrderNumbers;
    private ArrayList<String> OrderCreators;
    private ArrayList<String> OrderTime;
    private ArrayList<String> OrderStats;
    private Context mcontext;
    private String selectUpdate;
    public String url="https://lamp.ms.wits.ac.za/home/s2039033/ProjectLori/changeorderStat.php";

    public StaffAdapter(Context context, ArrayList<String> ON, ArrayList<String> OrderC, ArrayList<String> OrderCreateTime, ArrayList<String> OrderStatus){
        this.layoutInflater = LayoutInflater.from(context);
        OrderNumbers = ON;
        OrderCreators = OrderC;
        mcontext = context;
        OrderTime = OrderCreateTime;
        OrderStats = OrderStatus;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.order_item,parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        //Bind the text views with the data received

        String ONumber = OrderNumbers.get(position);
        holder.OrderNumber.setText(ONumber);

        //Similarly for images and Order creator
        String OCreator = OrderCreators.get(position);
        holder.OrderCreator.setText((OCreator));

        //Bind the Order Creation time
        String OrderT = OrderTime.get(position);
        holder.CreationTime.setText(OrderT);

        //Bind the order status
        String OrderStatus = OrderStats.get(position);
        holder.OrderStatus.setText(OrderStatus);
    }

    @Override
    public int getItemCount() {
        return OrderNumbers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView OrderNumber, OrderCreator, CreationTime, OrderStatus;
        ImageView imageView;
//        private Context context;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //implement onClick to update orders
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int mypos=getAdapterPosition();
                    String myid= OrderNumbers.get(mypos);
                    String num=myid.substring(myid.lastIndexOf("#")+1);
                    openDialog(num);
                }
            });
            OrderNumber = itemView.findViewById(R.id.OrderNumber);
            OrderCreator = itemView.findViewById(R.id.OrderCreator);
            imageView = itemView.findViewById(R.id.restaurant);
            CreationTime = itemView.findViewById(R.id.CreateTime);
            OrderStatus = itemView.findViewById(R.id.OrderStatus);
        }
    }

    public void openDialog(final String orderid){
        final String [] updateoptions = {"PENDING","READY", "COLLECTED"};


        AlertDialog.Builder d = new AlertDialog.Builder(mcontext);
        d.setTitle("Update Order Status");
        d.setSingleChoiceItems(updateoptions, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectUpdate = updateoptions[which];
            }
        });
        d.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, final int which) {
                //Send network request here to update the status of the order

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
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
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params= new HashMap<>();
                        params.put("ORDER_ID",orderid);
                        params.put("STATUS_CHANGE",selectUpdate);
                        return params;
                    }
                };


                RequestQueue requestQueue= Volley.newRequestQueue(mcontext);
                requestQueue.add(stringRequest);
            }

        });
        AlertDialog al = d.create();
        al.show();
    }

    public void processJSON(String json) throws JSONException {
            JSONObject jsonObject = new JSONObject(json);
            String success=jsonObject.getString("success");
            if(success.equals("1")){
                Toast.makeText(mcontext,"Successful Update to Status",Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(mcontext,"Some kinda error",Toast.LENGTH_SHORT).show();
            }
    }
}
