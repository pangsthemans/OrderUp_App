package com.example.orderup.ui.gallery;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderup.R;
import com.example.orderup.StaffHome;

import java.util.ArrayList;

public class StaffAdapter extends RecyclerView.Adapter<StaffAdapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    private ArrayList<String> OrderNumbers;
    private ArrayList<String> OrderCreators;
    private Context mcontext;
    private String selectUpdate;

    public StaffAdapter(Context context, ArrayList<String> ON, ArrayList<String> OrderC){
        this.layoutInflater = LayoutInflater.from(context);
        OrderNumbers = ON;
        OrderCreators = OrderC;
        mcontext = context;
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
            //implement onClick to update orders
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openDialog();
                }
            });

            OrderNumber = itemView.findViewById(R.id.OrderNumber);
            OrderCreator = itemView.findViewById(R.id.OrderCreator);
            imageView = itemView.findViewById(R.id.restaurant);
        }
    }

    public void openDialog(){
        final String [] updateoptions = {"Pending","Ready", "Collected"};


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
            public void onClick(DialogInterface dialog, int which) {
                //Send network request here to update the status of the order
            }
        });
        AlertDialog al = d.create();
        al.show();
    }
}
