package com.example.project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class FavoriteFragment extends Fragment {
    RecyclerViewItem [] items;
    ArrayAdapter<AuxData> adapter;

    public FavoriteFragment(RecyclerViewItem [] items) {
        // Required empty public constructor
        this.items = items;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        // Inflate the layout for this fragment
        ArrayList<AuxData> list = new ArrayList<>();
        for(int i = 0; i < items.length; i++){
            list.addAll(Arrays.asList(items[i].getAuxdata()));
        }
        adapter = new ArrayAdapter<>(getContext(), R.layout.favorite_item_list, R.id.favorite_textView, list);
        ListView listView = view.findViewById(R.id.favorite_listView);
        listView.setAdapter(adapter);
        return view;
    }
}