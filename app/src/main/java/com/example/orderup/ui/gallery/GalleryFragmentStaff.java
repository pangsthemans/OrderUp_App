package com.example.orderup.ui.gallery;

import android.os.Bundle;
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

import com.example.orderup.R;

import java.util.ArrayList;

public class GalleryFragmentStaff extends Fragment {
    RecyclerView recyclerView;
    Adapter adapter;
    ArrayList<String> OrderNum;
    ArrayList<String> OrderCreators;

    private GalleryViewModel galleryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_staff_gallery, container, false);

        OrderNum = new ArrayList<>();
        OrderNum.add("Order Number: 1");
        OrderNum.add("Order Number: 2");
        OrderNum.add("Order Number: 3");
        OrderNum.add("Order Number: 4");

        OrderCreators = new ArrayList<>();
        OrderCreators.add("Langiwe");
        OrderCreators.add("Sipho");
        OrderCreators.add("Ghulame");
        OrderCreators.add("Faisal");

        recyclerView = root.findViewById(R.id.recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new Adapter(getActivity(),OrderNum,OrderCreators);
        recyclerView.setAdapter(adapter);

        return root;
    }
}
