package com.eugene.testcft.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eugene.testcft.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.List;

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.ViewHolder> {
    final private List<JSONObject> data;

    public CurrencyAdapter(List<JSONObject> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public CurrencyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrencyAdapter.ViewHolder holder, int position) {
        try {
            JSONObject jsObject = data.get(position);
            DecimalFormat decFormat = new DecimalFormat("#.##");

            holder.getName().setText(jsObject.getString("Name"));
            holder.charCode.setText(jsObject.getString("CharCode"));
            holder.getValue().setText(decFormat.format(Double.parseDouble(jsObject.getString("Value"))));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final TextView charCode;
        private final TextView value;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_name);
            charCode = itemView.findViewById(R.id.item_charCode);
            value = itemView.findViewById(R.id.item_value);
        }

        public TextView getName() {
            return name;
        }

        public TextView getValue() {
            return value;
        }
    }
}
