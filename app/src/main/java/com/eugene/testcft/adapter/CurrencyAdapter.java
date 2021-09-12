package com.eugene.testcft.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eugene.testcft.R;
import com.eugene.testcft.model.JsonObjectOfList;

import java.util.List;

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.ViewHolder> {
    final private List<JsonObjectOfList> data;

    public CurrencyAdapter(List<JsonObjectOfList> data) {
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
            JsonObjectOfList jsObject = data.get(position);
            holder.getName().setText(jsObject.getNameJSO());
            holder.getCharCode().setText(jsObject.getCharCodeJSO());
            holder.getValue().setText(jsObject.getValueJSO());
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

        public TextView getCharCode() {
            return charCode;
        }
    }
}
