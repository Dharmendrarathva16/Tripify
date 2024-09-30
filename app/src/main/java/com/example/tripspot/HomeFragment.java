package com.example.tripspot;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.FragmentTransaction;

import com.example.tripspot.BookingFragment;
import com.example.tripspot.DataClass;
import com.example.tripspot.MyAdapter;
import com.example.tripspot.R;
import com.example.tripspot.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment  extends Fragment {

    RecyclerView recyclerView;
    List<DataClass> dataList;
    MyAdapter adapter;
    DataClass androidData;
    SearchView searchView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home2, container, false);

        // Initialize RecyclerView and SearchView
        recyclerView = view.findViewById(R.id.recyclerView);
        searchView = view.findViewById(R.id.search);

        // Clear focus for SearchView
        searchView.clearFocus();

        // Set the query listener for SearchView
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchList(newText);
                return true;
            }
        });

        // Set up RecyclerView with GridLayoutManager
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);

        // Initialize data list
        dataList = new ArrayList<>();

        // Add data to the list
        androidData = new DataClass("Camera", R.string.camera, "Java", R.drawable.pkggoa);
        dataList.add(androidData);
        androidData = new DataClass("RecyclerView", R.string.recyclerview, "Kotlin", R.drawable.pkggoa);
        dataList.add(androidData);
        androidData = new DataClass("Date Picker", R.string.date, "Java", R.drawable.pkggoa);
        dataList.add(androidData);
        androidData = new DataClass("EditText", R.string.edit, "Kotlin", R.drawable.pkggoa);
        dataList.add(androidData);
        androidData = new DataClass("Rating Bar", R.string.rating, "Java", R.drawable.pkggoa);
        dataList.add(androidData);

        // Set up adapter for RecyclerView
        adapter = new MyAdapter(getActivity(), dataList);
        recyclerView.setAdapter(adapter);

        // Handle click event to open BookingFragment
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // Replace with BookingFragment
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new BookingFragment()); // Replace with your BookingFragment
                transaction.addToBackStack(null);
                transaction.commit();
            }

            @Override
            public void onLongItemClick(View view, int position) {
                // You can add additional logic for long click here if needed
            }
        }));

        return view;
    }

    // Method to filter the list based on the search query
    private void searchList(String text) {
        List<DataClass> dataSearchList = new ArrayList<>();
        for (DataClass data : dataList) {
            if (data.getDataTitle().toLowerCase().contains(text.toLowerCase())) {
                dataSearchList.add(data);
            }
        }
        if (dataSearchList.isEmpty()) {
            Toast.makeText(getActivity(), "Not Found", Toast.LENGTH_SHORT).show();
        } else {
            adapter.setSearchList(dataSearchList);
        }
    }
}
