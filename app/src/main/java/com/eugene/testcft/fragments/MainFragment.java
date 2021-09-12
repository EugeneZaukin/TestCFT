package com.eugene.testcft.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.eugene.testcft.R;
import com.eugene.testcft.adapter.CurrencyAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class MainFragment extends Fragment {
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycler_view_list);
        loadJsonFromUrl();
    }

    //Отрисовка списка
    private void initRecyclerView(RecyclerView recyclerView, List<JSONObject> data) {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        CurrencyAdapter adapter = new CurrencyAdapter(data);
        recyclerView.setAdapter(adapter);
    }

    //Загружаем Json и при успешном запросе передаем лист обхектов и recycler view для отрисовки
    private void loadJsonFromUrl() {
        String urlText = "https://www.cbr-xml-daily.ru/daily_json.js";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlText,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            JSONObject jsonValute = object.getJSONObject("Valute");

                            List<JSONObject> listJsonValutes = new ArrayList<>();
                            Iterator<String> jsonValuteKeys = jsonValute.keys();

                            while (jsonValuteKeys.hasNext()) {
                                listJsonValutes.add(jsonValute.getJSONObject(jsonValuteKeys.next()));
                            }

                            initRecyclerView(recyclerView, listJsonValutes);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity().getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }
}
