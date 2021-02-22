package com.example.shoppinglist;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ResourceBundle;

public class Fragment1 extends Fragment {

    private static final String ARG_FRAGNM = "fragment_name";
    private String fragmentName;
    private ItmAdapter adapter;
    private RecyclerView itemRecyclerView;

    public Fragment1() {
        // Required empty public constructor
    }

    public static Fragment1 newInstance(String frgNm) {
        Fragment1 fragment1 = new Fragment1();
        Bundle args = new Bundle();
        args.putString(ARG_FRAGNM, frgNm);
        fragment1.setArguments(args);
        return fragment1;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            fragmentName = getArguments().getString(ARG_FRAGNM);;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout, container, false);
        itemRecyclerView = view.findViewById(R.id.itemRecyclerView);
        Context context = getContext();
        itemRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        DividerItemDecoration divider = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        itemRecyclerView.addItemDecoration(divider);
        itemRecyclerView.setAdapter(adapter);
        return view;
    }

    public void setItmAdapter(ItmAdapter adapter){
        this.adapter = adapter;
    }
}