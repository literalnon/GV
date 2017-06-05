package com.example.donald.canvasesss;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.lang.reflect.GenericArrayType;
import java.util.Vector;

/**
 * Created by Donald on 18.05.2017.
 */
public class GraphsAdapter extends BaseAdapter{

        LayoutInflater inflater;
        Activity context;
        Vector<Graph> graphs;

        public GraphsAdapter(Activity context, Vector<Graph> graphs){
            this.context = context;
            this.graphs = graphs;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return graphs.size();
        }

        @Override
        public Object getItem(int position) {
            return graphs.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, final View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null){
                view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            }

            TextView textView = ((TextView) view.findViewById(android.R.id.text1));
            textView.setText(position + 1 + "");

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity.graph = graphs.elementAt(position);
                    MainActivity.drawView.invalidate();
                    MainActivity.graphsList.setVisibility(View.GONE);
                }
            });

            return view;
        }

}
