package com.example.orderup.ui.gallery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderup.R;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    private ArrayList<String> OrderNumbers;
    private ArrayList<String> OrderCreators;

    Adapter(Context context, ArrayList<String> ON, ArrayList<String> OrderC){
        this.layoutInflater = LayoutInflater.from(context);
        OrderNumbers = ON;
        OrderCreators = OrderC;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.order_item,parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        //Bidn the textviews with the data received

        String ONumber = OrderNumbers.get(position);
        holder.OrderNumber.setText(ONumber);

        //Similarly for images and Order creator
        String OCreator = OrderCreators.get(position);
        holder.OrderCreator.setText((OCreator));

    }

    @Override
    public int getItemCount() {
        return OrderNumbers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView OrderNumber, OrderCreator;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            OrderNumber = itemView.findViewById(R.id.OrderNumber);
            OrderCreator = itemView.findViewById(R.id.OrderCreator);
            imageView = itemView.findViewById(R.id.restaurant);
        }
    }
}
