package com.eugene.testcft.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.eugene.testcft.model.JsonObjectOfList;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MainFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<JsonObjectOfList> listParcelableObj;
    private final String KEY_FOR_PARCE = "KEY_FOR_PARCE";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        recyclerView = view.findViewById(R.id.recycler_view_list);

        if (savedInstanceState != null) {
            listParcelableObj = savedInstanceState.getParcelableArrayList(KEY_FOR_PARCE);
            initRecyclerView(recyclerView, listParcelableObj);
        } else {
            loadJsonFromUrl();
        }
        updateValutesByTime();
    }

    //Отрисовка списка
    private void initRecyclerView(RecyclerView recyclerView, List<JsonObjectOfList> data) {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        CurrencyAdapter adapter = new CurrencyAdapter(data);
        recyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }

    //Загружаем Json и при успешном запросе передаем лист объектов и recycler view для отрисовки
    private void loadJsonFromUrl() {
        String urlText = "https://www.cbr-xml-daily.ru/daily_json.js";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlText,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            JSONObject jsonValute = object.getJSONObject("Valute");

                            Iterator<String> jsonValuteKeys = jsonValute.keys();
                            listParcelableObj = new ArrayList<>();
                            DecimalFormat decFormat = new DecimalFormat("#.##");

                            while (jsonValuteKeys.hasNext()) {
                                JSONObject jsonObject = jsonValute.getJSONObject(jsonValuteKeys.next());
                                listParcelableObj.add(new JsonObjectOfList(jsonObject.getString("Name"),
                                                                    jsonObject.getString("CharCode"),
                                                                    decFormat.format(Double.parseDouble(jsonObject.getString("Value")))));
                            }
                            initRecyclerView(recyclerView, listParcelableObj);

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

    //Создание меню
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    //Обработка кнопки для обновления данных
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.update_list) {
            loadJsonFromUrl();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Сохранение данных при повороте экрана
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(KEY_FOR_PARCE, (ArrayList<? extends Parcelable>) listParcelableObj);
    }

    //Обновление данных каждые 10 минут
    private void updateValutesByTime() {
        ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(1);
        exec.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                loadJsonFromUrl();
            }
        }, 0, 10, TimeUnit.MINUTES);
    }
}
