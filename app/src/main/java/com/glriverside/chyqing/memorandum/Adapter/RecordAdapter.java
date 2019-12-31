package com.glriverside.chyqing.memorandum.Adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.glriverside.chyqing.memorandum.R;
import com.glriverside.chyqing.memorandum.Values.RecordValues;

import java.util.List;

public class RecordAdapter extends ArrayAdapter<RecordValues> {
    private int resourceId;

    public RecordAdapter(Context context, int textViewResourceId, List<RecordValues> objects){
        super(context,textViewResourceId,objects);
        resourceId = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        RecordValues recordValues = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        TextView textView = view.findViewById(R.id.record_title);
        return view;
    }
}
