package com.benmohammad.reelz.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.benmohammad.reelz.R;

import com.benmohammad.reelz.model.Snippet;

import java.util.ArrayList;
import java.util.List;

public class SnippetAdapter extends BaseAdapter implements ListAdapter {

    private List<Snippet> list = new ArrayList<>();
    private Context context;
    private Communicator comm;

    public SnippetAdapter(ArrayList<Snippet> arr, Context context, Communicator comm) {
        this.list = arr;
        this.context = context;
        this.comm = comm;

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Snippet getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        if(view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.snippet_item, null);
        }

        final TextView content = view.findViewById(R.id.title);
        content.setText(list.get(i).getTitle());

        content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comm.customSetResult(list.get(i).getContent());
            }
        });



        return view;
    }
}
