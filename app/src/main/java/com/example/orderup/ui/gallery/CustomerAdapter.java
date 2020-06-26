package com.example.orderup.ui.gallery;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.orderup.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    private ArrayList<String> OrderNumbers;
    private ArrayList<String> OrderCreators;
    private ArrayList<String> OrderTime;
    private ArrayList<String> OrderStats;
    private ArrayList<String> OrderOwners;
    private ArrayList<String> Restaurants;
    private Context mcontext;
    private String selectUpdate;
    public String url="https://lamp.ms.wits.ac.za/home/s2039033/ProjectLori/changeOrderRating.php";


    public CustomerAdapter(Context context, ArrayList<String> ON, ArrayList<String> OrderC, ArrayList<String> OrderCreateTime, ArrayList<String> OrderStatus,ArrayList<String> OrderOwner, ArrayList<String> Restaurant){
        this.layoutInflater = LayoutInflater.from(context);
        OrderNumbers = ON;
        OrderOwners=OrderOwner;
        OrderCreators = OrderC;
        mcontext=context;
        OrderTime = OrderCreateTime;
        OrderStats = OrderStatus;
        Restaurants = Restaurant;
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

        String oOwner=OrderOwners.get(position);
        holder.OrderOwner.setText(oOwner);

        //Bind the order status
        String OrderStatus = OrderStats.get(position);
        holder.OrderStatus.setText(OrderStatus);


        //Bind the restaurant image
        String restaurant = Restaurants.get(position);
        if(restaurant.equals("KFC")){
            holder.RestaurantImage.setImageResource(R.drawable.kfc);
        }
        else if(restaurant.equals("Steers")){
            holder.RestaurantImage.setImageResource(R.drawable.steers);
        }
        else if(restaurant.equals("RoccoMamas")){
            holder.RestaurantImage.setImageResource(R.drawable.rocomama);
        }
        else if(restaurant.equals("Romans Pizza")){
            holder.RestaurantImage.setImageResource(R.drawable.romans);
        }
        else if(restaurant.equals("Macdonalds")){
            holder.RestaurantImage.setImageResource(R.drawable.mcd);
        }
        else{
            holder.UnknownRest.setText(restaurant);
        }

    }

    @Override
    public int getItemCount() {
        return OrderNumbers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView OrderNumber, OrderCreator, CreationTime, OrderStatus,OrderOwner, UnknownRest;
        ImageView RestaurantImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int mypos=getAdapterPosition();
                    String myid=OrderNumbers.get(mypos);
                    String number=myid.substring(myid.lastIndexOf("#")+1);
                    openDialog(number);
                }
            });

            OrderNumber = itemView.findViewById(R.id.OrderNumber);
            OrderCreator = itemView.findViewById(R.id.OrderCreator);
            RestaurantImage = itemView.findViewById(R.id.restaurant);
            CreationTime = itemView.findViewById(R.id.CreateTime);
            OrderStatus = itemView.findViewById(R.id.OrderStatus);
            OrderOwner= itemView.findViewById(R.id.OrderOwner);
            UnknownRest = itemView.findViewById(R.id.UnkownRest);
        }


        public void openDialog(final String orderid){
            final String [] updateoptions = {"GOOD", "BAD"};


            AlertDialog.Builder d = new AlertDialog.Builder(mcontext);
            d.setTitle("Give Your Order A Rating!");
            d.setSingleChoiceItems(updateoptions, 0, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    selectUpdate = updateoptions[which];
                }
            });
            d.setPositiveButton("Rate", new DialogInterface.OnClickListener() {
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
                            params.put("RATING_CHANGE",selectUpdate);
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
            String message=jsonObject.getString("message");
            if(success.equals("1")){
                Toast.makeText(mcontext,"Thank You for Your Feedback",Toast.LENGTH_SHORT).show();
            }
            else if(success.equals("0")){
                Toast.makeText(mcontext,message,Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(mcontext,"Some kinda error",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
