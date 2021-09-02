package com.eugene.testcft.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eugene.testcft.R;
import com.eugene.testcft.adapter.CurrencyAdapter;
import com.eugene.testcft.model.BanksCurrency;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        RecyclerView recyclerView = v.findViewById(R.id.recycler_view_list);
        List<BanksCurrency> list = new ArrayList<>();
        list.add(new BanksCurrency());
        list.add(new BanksCurrency());
        list.add(new BanksCurrency());

        initRecyclerView(recyclerView, list);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void initRecyclerView(RecyclerView recyclerView, List<BanksCurrency> data) {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        CurrencyAdapter adapter = new CurrencyAdapter(data);
        recyclerView.setAdapter(adapter);
    }

}